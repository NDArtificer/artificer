package com.artificer.pedidos.domain.service;

import com.artificer.pedidos.api.model.input.PedidoInput;
import com.artificer.pedidos.api.model.output.ItemPedidoOutput;
import com.artificer.pedidos.domain.exception.NegocioException;
import com.artificer.pedidos.domain.http.clientes.ClientesApiService;
import com.artificer.pedidos.domain.model.Cliente;
import com.artificer.pedidos.domain.model.Pedido;
import com.artificer.pedidos.domain.model.Produto;
import com.artificer.pedidos.domain.repository.PedidoRepository;
import com.artificer.pedidos.mapper.PedidoMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

import static com.artificer.pedidos.domain.model.Pedido.StatusPedido.RASCUNHO;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClientesApiService clientesApiService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        String email = pedido.getCliente().getEmail();
        Mono<Cliente> clienteMono = clientesApiService.buscarClientePorEmail(email);
        var cliente = clienteMono.block();
        validarItems(pedido);
        pedido.setCliente(cliente);
        pedido.setTaxaFrete(new BigDecimal("20.00"));
        pedido.calcularValorTotalPedido();
        return pedidoRepository.save(pedido);
    }

    private void validarItems(Pedido pedido) {
        pedido.getItens().forEach(i -> {
            Produto produto = produtoService.buscarOuFalhar(i.getProduto().getCodigoProduto());
            i.setProduto(produto);
            i.setPedido(pedido);
            i.calcularValorTotalItem();
        });
    }

    public Pedido buscarOuFalhar(UUID codigoPedido) {
        return pedidoRepository.findByCodigoPedido(codigoPedido).orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));
    }

    @Transactional
    public Pedido emitirPedido(UUID codigoPedido) {
        var pedido = buscarOuFalhar(codigoPedido);
        pedido.emitir();
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido cancelarPedido(UUID codigoPedido) {
        var pedido = buscarOuFalhar(codigoPedido);
        pedido.cancelar();
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido confirmarPedido(UUID codigoPedido) {
        var pedido = buscarOuFalhar(codigoPedido);
        pedido.confirmar();
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido registrarEntrega(UUID codigoPedido) {
        var pedido = buscarOuFalhar(codigoPedido);
        pedido.entregar();
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido atualizarPedido(UUID codigoPedido, @Valid PedidoInput pedidoInput) {
        var pedidoExistente = buscarOuFalhar(codigoPedido);
        String emailPedidoExistente = pedidoExistente.getCliente().getEmail();
        String emailPedidoInput = pedidoInput.getCliente().getEmail();
        if(emailPedidoExistente.equals(emailPedidoInput)){
            if(pedidoExistente.getStatus().equals(RASCUNHO)){
                var pedidoAtualizado = pedidoMapper.toEntity(pedidoInput);
                pedidoAtualizado.setId(pedidoExistente.getId());
                pedidoAtualizado.setCodigoPedido(codigoPedido);
                return criarPedido(pedidoAtualizado);
            } else {
                throw new NegocioException("Pedido não pode ser atualizado, pois já foi emitido!");
            }
        } else{
            throw new NegocioException("Email informando não é o mesmo do cliente que criou o pedido!");
        }
    }

    @Transactional
    public void removerItemDoPedido(UUID codigoPedido, Long itemCodigo) {
        var pedido = buscarOuFalhar(codigoPedido);
        if(pedido.getStatus().equals(RASCUNHO)){
            var itemPedido = pedido.getItens().stream()
                    .filter(i -> i.getId().equals(itemCodigo))
                    .findFirst()
                    .orElseThrow(() -> new NegocioException("Item do pedido não encontrado!"));
            pedido.removerItem(itemPedido);
            pedido.calcularValorTotalPedido();
            pedidoRepository.saveAndFlush(pedido);
        }else{
            throw new NegocioException("Itens só podem ser removidos de pedidos em rascunho!");
        }
    }

    public ItemPedidoOutput getItemPedidoOutput(UUID codigoPedido, Long itemCodigo) {
        var pedido = buscarOuFalhar(codigoPedido);
        var pedidoOutput = pedidoMapper.toModel(pedido);
        return pedidoOutput.getItens().stream()
                .filter(i -> i.getId().equals(itemCodigo))
                .findFirst()
                .orElseThrow(() -> new NegocioException("Item do pedido não encontrado!"));
    }
}

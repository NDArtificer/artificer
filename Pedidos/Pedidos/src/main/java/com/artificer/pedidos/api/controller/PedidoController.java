package com.artificer.pedidos.api.controller;


import com.artificer.pedidos.api.model.input.PedidoInput;
import com.artificer.pedidos.api.model.output.ItemPedidoOutput;
import com.artificer.pedidos.api.model.output.PedidoOutput;
import com.artificer.pedidos.domain.model.Pedido;
import com.artificer.pedidos.domain.repository.PedidoRepository;
import com.artificer.pedidos.domain.service.PedidoService;
import com.artificer.pedidos.mapper.PedidoMapper;
import com.artificer.pedidos.page.CustomPage;
import com.artificer.pedidos.page.PageMetaData;
import com.artificer.pedidos.security.CanEditPedidos;
import com.artificer.pedidos.security.CanReadPedidos;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @CanReadPedidos
    public ResponseEntity<CustomPage<PedidoOutput>> listarPedidos(@PageableDefault Pageable pageable) {
        Page<Pedido> all = pedidoRepository.findAll(pageable);
        List<PedidoOutput> pedidoOutputList = pedidoMapper.toModel(all.getContent());
        CustomPage<PedidoOutput> customPage = new CustomPage<>(pedidoOutputList, PageMetaData.brandNewPage(all));
        return ResponseEntity.ok(customPage);
    }

    @GetMapping("/{codigoPedido}")
    @CanReadPedidos
    public ResponseEntity<PedidoOutput> buscarPedidoPorCodigo(@PathVariable UUID codigoPedido) {
        var pedido = pedidoService.buscarOuFalhar(codigoPedido);
        var pedidoOutput = pedidoMapper.toModel(pedido);
        return ResponseEntity.ok(pedidoOutput);
    }

    @CanEditPedidos
    @PostMapping
    public ResponseEntity<PedidoOutput> criarPedido(@Valid @RequestBody PedidoInput pedidoInput) {
        var novoPedido = pedidoMapper.toEntity(pedidoInput);
        var pedidoSalvo = pedidoService.criarPedido(novoPedido);
        var pedidoOutput = pedidoMapper.toModel(pedidoSalvo);
        return ResponseEntity.status(CREATED).body(pedidoOutput);
    }

    @CanEditPedidos
    @PutMapping("/{codigoPedido}")
    public ResponseEntity<PedidoOutput> atualizarPedido(@PathVariable UUID codigoPedido, @RequestBody @Valid PedidoInput pedidoInput) {
        var pedidoSalvo = pedidoService.atualizarPedido(codigoPedido, pedidoInput);
        var pedidoOutput = pedidoMapper.toModel(pedidoSalvo);
        return ResponseEntity.ok(pedidoOutput);
    }

    @CanEditPedidos
    @PutMapping("/{codigoPedido}/emissao")
    public ResponseEntity<PedidoOutput> emitirPedido(@PathVariable UUID codigoPedido) {
        Pedido pedido = pedidoService.emitirPedido(codigoPedido);
        return ResponseEntity.ok().body(pedidoMapper.toModel(pedido));
    }

    @CanEditPedidos
    @PutMapping("/{codigoPedido}/entrega")
    public ResponseEntity<PedidoOutput> registrarEntrega(@PathVariable UUID codigoPedido) {
        Pedido pedido = pedidoService.registrarEntrega(codigoPedido);
        return ResponseEntity.ok().body(pedidoMapper.toModel(pedido));
    }

    @CanEditPedidos
    @PutMapping("/{codigoPedido}/confirmacao")
    public ResponseEntity<PedidoOutput> confirmarPedido(@PathVariable UUID codigoPedido) {
        Pedido pedido = pedidoService.confirmarPedido(codigoPedido);
        return ResponseEntity.ok().body(pedidoMapper.toModel(pedido));
    }

    @CanEditPedidos
    @PutMapping("/{codigoPedido}/cancelamento")
    public ResponseEntity<PedidoOutput> cancelarPedido(@PathVariable UUID codigoPedido) {
        Pedido pedido = pedidoService.cancelarPedido(codigoPedido);
        return ResponseEntity.ok().body(pedidoMapper.toModel(pedido));
    }

    @CanReadPedidos
    @GetMapping("/{codigoPedido}/itens")
    public ResponseEntity<CustomPage<ItemPedidoOutput>> listarPedidos(@PathVariable UUID codigoPedido, @PageableDefault Pageable pageable) {
        var pedido = pedidoService.buscarOuFalhar(codigoPedido);
        var pedidoOutput = pedidoMapper.toModel(pedido);
        List<ItemPedidoOutput> itens = pedidoOutput.getItens();
        Page<ItemPedidoOutput> page =  PageMetaData.listToPage(pageable, itens);
        CustomPage<ItemPedidoOutput> customPage = new CustomPage<>(page.getContent(), PageMetaData.brandNewPage(page));
        return ResponseEntity.ok(customPage);
    }

    @CanReadPedidos
    @GetMapping("/{codigoPedido}/itens/{itemCodigo}")
    public ResponseEntity<ItemPedidoOutput> buscarItemPedido(@PathVariable UUID codigoPedido, @PathVariable Long itemCodigo) {
        var itemPedidoOutput = pedidoService.getItemPedidoOutput(codigoPedido, itemCodigo);
        return ResponseEntity.ok(itemPedidoOutput);
    }

    @CanEditPedidos
    @DeleteMapping("/{codigoPedido}/itens/{itemCodigo}")
    public ResponseEntity<Void> listarPedidos(@PathVariable UUID codigoPedido, @PathVariable Long itemCodigo) {
        pedidoService.removerItemDoPedido(codigoPedido, itemCodigo);
        return ResponseEntity.noContent().build();
    }

}

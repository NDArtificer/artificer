package com.artificer.pedidos.domain.model;

import com.artificer.pedidos.domain.exception.NegocioException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
public class Pedido {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    private UUID codigoPedido;

    @Column
    private BigDecimal subtotal;

    @Column
    private BigDecimal taxaFrete;

    @Column
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.RASCUNHO;

    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "pedido_cliente_id")),
            @AttributeOverride(name = "nome", column = @Column(name = "pedido_cliente_nome")),
            @AttributeOverride(name = "email", column = @Column(name = "pedido_cliente_email")),
    })
    @Embedded
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    private void setStatus(StatusPedido novoStatus) {
        if (getStatus().naoPodeAleterarPara(novoStatus)) {
            throw new NegocioException(String.format("O status do pedido %s não pode ser alterado de %s para %s!",
                    getCodigoPedido(), getStatus().getDescricao(), novoStatus.getDescricao()));
        }
        this.status = novoStatus;
    }

    public void emitir() {
        setStatus(StatusPedido.CRIADO);
    }

    public void confirmar() {
        setStatus(StatusPedido.CONFIRMADO);
    }

    public void entregar() {
        setStatus(StatusPedido.ENTREGUE);
    }

    public void cancelar() {
        setStatus(StatusPedido.CANCELADO);
    }

    public void calcularValorTotalPedido() {
        this.subtotal = itens.stream()
                .map(ItemPedido::getValorTotalItem)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    @PrePersist
    private void gerarCodigo() {
        setCodigoPedido(UUID.randomUUID());
    }

    public void removerItem(ItemPedido itemPedido) {
        itens.remove(itemPedido);
        itemPedido.setPedido(null);
    }


    @AllArgsConstructor
    @Getter
    public enum StatusPedido {
        RASCUNHO("Rascunho"),
        CRIADO("Criado", RASCUNHO),
        CONFIRMADO("Confirmado", CRIADO),
        ENTREGUE("Entregue", CONFIRMADO),
        CANCELADO("Cancelado", CRIADO);

        private final String descricao;
        private final List<StatusPedido> statusAnteriores;

        StatusPedido(String descricao, StatusPedido... statusAnteriores) {
            this.descricao = descricao;
            this.statusAnteriores = Arrays.asList(statusAnteriores);
        }

        public Boolean naoPodeAleterarPara(StatusPedido novoStatusPedido) {
            return !novoStatusPedido.statusAnteriores.contains(this);
        }

        public Boolean podeAleterarPara(StatusPedido novoStatusPedido) {
            return !naoPodeAleterarPara(novoStatusPedido);
        }

    }

}

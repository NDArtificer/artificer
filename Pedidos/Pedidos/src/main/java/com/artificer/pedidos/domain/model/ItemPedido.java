package com.artificer.pedidos.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private BigDecimal valorTotalItem;

    @Column
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Pedido pedido;

    public void calcularValorTotalItem() {
        if (produto != null && quantidade != null) {
            this.valorTotalItem = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
        } else {
            this.valorTotalItem = BigDecimal.ZERO;
        }
    }

}

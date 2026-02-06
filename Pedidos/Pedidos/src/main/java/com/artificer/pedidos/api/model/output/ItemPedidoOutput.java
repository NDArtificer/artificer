package com.artificer.pedidos.api.model.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoOutput {
    private Long id;
    private ProdutoOutput produto;
    private Integer quantidade;
    private BigDecimal valorTotalItem;
}

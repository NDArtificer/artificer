package com.artificer.pedidos.api.model.output;

import com.artificer.pedidos.domain.model.Cliente;
import com.artificer.pedidos.domain.model.ItemPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PedidoOutput {

    private UUID codigoPedido;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private ClienteOutput cliente;
    private List<ItemPedidoOutput> itens;

}

package com.artificer.pedidos.api.model.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoInput {

    @NotNull
    @Valid
    private ClienteInput cliente;

    @NotNull
    @Valid
    @Size(min = 1)
    private List<ItemPedidoInput> itens;

}

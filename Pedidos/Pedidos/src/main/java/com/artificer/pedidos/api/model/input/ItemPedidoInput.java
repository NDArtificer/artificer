package com.artificer.pedidos.api.model.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {

    @NotNull
    @Valid
    private ProdutoSumaryInput produto;

    @Min(1)
    @NotNull
    private Integer quantidade;

}

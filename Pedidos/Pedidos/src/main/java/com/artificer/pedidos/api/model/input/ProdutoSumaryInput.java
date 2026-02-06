package com.artificer.pedidos.api.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoSumaryInput {

    @NotBlank
    private String codigoProduto;

}

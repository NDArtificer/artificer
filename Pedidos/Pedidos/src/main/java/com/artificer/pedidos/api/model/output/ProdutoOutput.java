package com.artificer.pedidos.api.model.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoOutput {

    private String codigoProduto;
    private String nome;
    private String descricao;
    private BigDecimal preco;

}

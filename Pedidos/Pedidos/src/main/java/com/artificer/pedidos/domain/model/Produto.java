package com.artificer.pedidos.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private UUID codigoProduto;
    @Column
    private String nome;
    @Column
    private String descricao;
    @Column
    private BigDecimal preco;

    @PrePersist
    public void gerarProdutoId() {
        this.codigoProduto = UUID.randomUUID();
    }

}

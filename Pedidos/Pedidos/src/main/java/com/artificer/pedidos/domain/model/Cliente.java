package com.artificer.pedidos.domain.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Cliente {

    private Long id;
    private String nome;
    private String email;

}

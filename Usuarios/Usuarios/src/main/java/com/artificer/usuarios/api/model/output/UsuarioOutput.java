package com.artificer.usuarios.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioOutput {

    private String id;
    private String nome;
    private String email;
    private String tipo;
}

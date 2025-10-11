package com.artificer.usuarios.api.model.intput;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioCredentials {

    private String id;
    private String nome;
    private String email;
    private String senha;
    private String tipo;
}

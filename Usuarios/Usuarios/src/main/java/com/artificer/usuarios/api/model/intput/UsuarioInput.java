package com.artificer.usuarios.api.model.intput;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {

    @NotBlank
    private String nome;
    @Email
    private String email;
    @NotBlank
    private String senha;
    @NotBlank
    private String tipo;
}

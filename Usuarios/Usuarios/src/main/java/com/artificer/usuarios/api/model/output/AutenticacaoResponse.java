package com.artificer.usuarios.api.model.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AutenticacaoResponse {
    private boolean autenticado;
    private String userId;
    private String userName;
    private List<String> authorities;
}

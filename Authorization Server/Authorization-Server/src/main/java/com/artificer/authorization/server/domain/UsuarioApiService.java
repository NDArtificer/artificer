package com.artificer.authorization.server.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UsuarioApiService {

    @Autowired
    private WebClient webClient;

    public Usuario buscarUsuario(String email) {
        return webClient.get()
                .uri("/usuarios/email?email={email}", email)
                .retrieve()
                .bodyToMono(Usuario.class)
                .block();
    }

}

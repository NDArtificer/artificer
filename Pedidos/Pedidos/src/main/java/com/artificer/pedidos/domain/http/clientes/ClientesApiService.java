package com.artificer.pedidos.domain.http.clientes;

import com.artificer.pedidos.domain.exception.ClienteNaoEncontradoException;
import com.artificer.pedidos.domain.exception.NegocioException;
import com.artificer.pedidos.domain.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ClientesApiService {

    @Autowired
    private WebClient webClient;

    public Mono<Cliente> buscarClientePorEmail(String email) {
        return webClient.get()
                .uri("http://localhost:8082/clientes/email/{email}", email)
                .retrieve()
                .onStatus(NOT_FOUND::equals,
                        response -> Mono.error(new ClienteNaoEncontradoException("Cliente %s não foi encontrado!".formatted(email))))
                .bodyToMono(Cliente.class);
    }
}

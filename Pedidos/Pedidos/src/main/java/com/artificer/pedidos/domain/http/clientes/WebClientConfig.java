package com.artificer.pedidos.domain.http.clientes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient webClient(ClientRegistrationRepository clients,
                        OAuth2AuthorizedClientRepository authClients) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(clients, authClients);
        oauth.setDefaultClientRegistrationId("pedidos-client");

        return WebClient.builder()
                .apply(oauth.oauth2Configuration())
                .build();
    }


}

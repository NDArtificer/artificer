package com.artificer.authorization.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.List;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(JwtEncoder jwtEncoder) {
        String token = gerarTokenInterno(jwtEncoder);

        return WebClient.builder()
                .baseUrl("http://localhost:8081")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
                .build();
    }

    public String gerarTokenInterno(JwtEncoder jwtEncoder) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("auth-server")
                .subject("internal-call")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(60))
                .claim("scope", List.of("usuarios:auth"))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}

package com.artificer.authorization.server.security;

import com.artificer.authorization.server.keystore.MultiKeyStoreResolver;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.List;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(MultiKeyStoreResolver resolver) {
        JwtEncoder internalJwtEncoder = createInternalJwtEncoder(resolver);
        return WebClient.builder()
                .baseUrl("http://localhost:8081")
                .filter((request, next) -> {
                    String token = gerarTokenInterno(internalJwtEncoder);
                    ClientRequest newRequest = ClientRequest.from(request)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
                            .build();
                    return next.exchange(newRequest);
                }).build();
    }

    private JwtEncoder createInternalJwtEncoder(MultiKeyStoreResolver resolver) {
        RSAKey rsaKey = resolver.getByClient("internal");
        JWKSource<SecurityContext> jwkSource = (selector, ctx) -> selector.select(new JWKSet(rsaKey));
        return new NimbusJwtEncoder(jwkSource);
    }

    private String gerarTokenInterno(JwtEncoder jwtEncoder) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("auth-server")
                .subject("internal-call")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(90))
                .claim("scope", List.of("usuarios:auth"))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}

package com.artificer.authorization.server;

import com.artificer.authorization.server.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorizationServerApplicationTests {

	public static final String MAIL = "/usuarios/email?email=artificer@email.com.br";

	@Autowired
	private JwtEncoder jwtEncoder;

	private WebClient webClientComToken(String token) {
		return WebClient.builder()
				.baseUrl("http://localhost:8081") // URL do UserService
				.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
				.build();
	}

	private String gerarTokenComScope(String scope) {
		Instant now = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("auth-server")
				.subject("test-client")
				.issuedAt(now)
				.expiresAt(now.plusSeconds(300))
				.claim("scope", List.of(scope))
				.build();

		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

	@Test
	@DisplayName("[TESTE02] - Deve negar acesso ao endpoint de Credencial sem token")
	@Tag("TESTE02")
	void deveNegarAcessoSemToken() {
		WebClient clientSemToken = WebClient.builder()
				.baseUrl("http://localhost:8081")
				.build();

		assertThrows(WebClientResponseException.Unauthorized.class, () -> {
			clientSemToken.get()
					.uri(MAIL)
					.retrieve()
					.bodyToMono(Usuario.class)
					.block();
		});
	}

	@Test
	@Tag("TESTE03")
	@DisplayName("[TESTE03] - Deve permitir acesso ao endpoint de Credencial com scope valido")
	void devePermitirAcessoComScopeUsuariosAuth() {
		String token = gerarTokenComScope("usuarios:auth");

		Usuario response = webClientComToken(token)
				.get()
				.uri(MAIL)
				.retrieve()
				.bodyToMono(Usuario.class)
				.block();

		assertNotNull(response);
		assertEquals(MAIL.replace("/usuarios/email?email=", ""), response.getEmail());
	}

	@Test
	@Tag("TESTE04")
	@DisplayName("[TESTE04] - Deve negar acesso ao endpoint de Credencial com scope invalida")
	void deveNegarAcessoComScopeInvalida() {
		String token = gerarTokenComScope("usuarios:read");

		assertThrows(WebClientResponseException.Forbidden.class, () -> {
			webClientComToken(token)
					.get()
					.uri( MAIL)
					.retrieve()
					.bodyToMono(Usuario.class)
					.block();
		});
	}

}

package com.artificer.clientes.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.net.URI;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper = new ObjectMapper().setDefaultPropertyInclusion(JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.NON_NULL));

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException ex) throws IOException {
        ApiError error = new ApiError(
                URI.create(request.getRequestURI()),
                UNAUTHORIZED.value(),
                "Credenciais inválidas ou não informadas!",
                URI.create("https://clientes.com/erros/unauthorized")
        );

        response.setStatus(UNAUTHORIZED.value());
        response.setContentType("application/problem+json");
        mapper.writeValue(response.getWriter(), error);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({"instance", "status", "title", "type"})
    public record ApiError(
            URI instance,
            int status,
            String title,
            URI type
    ) {
    }

}
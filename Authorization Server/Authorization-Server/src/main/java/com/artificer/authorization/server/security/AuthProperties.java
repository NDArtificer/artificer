package com.artificer.authorization.server.security;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@ConfigurationProperties(prefix = "artificer.auth")
@Getter
@Setter
public class AuthProperties {

    @NotBlank
    private String keypass;

    @NotBlank
    private String storepass;

    @NotBlank
    private String alias;

    @NotBlank
    private String path ;
}
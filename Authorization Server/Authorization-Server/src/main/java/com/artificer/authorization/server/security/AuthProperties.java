package com.artificer.authorization.server.security;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Component
@Validated
@ConfigurationProperties(prefix = "auths")
@Getter
@Setter
public class AuthProperties {

    private Map<String, ClientKeyStore> clients;

    @Getter
    @Setter
    public static class ClientKeyStore {
        @NotBlank
        private String keypass;

        @NotBlank
        private String storepass;

        @NotBlank
        private String alias;

        @NotBlank
        private String path;


    }
}
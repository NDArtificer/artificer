package com.artificer.authorization.server.keystore;

import com.artificer.authorization.server.security.AuthProperties;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MultiKeyStoreResolver {

    private final Map<String, RSAKey> rsaKeys = new HashMap<>();

    public MultiKeyStoreResolver(AuthProperties authProperties) {
        for (Map.Entry<String, AuthProperties.ClientKeyStore> entry : authProperties.getClients().entrySet()) {
            AuthProperties.ClientKeyStore clientKeyStore = entry.getValue();
            try (InputStream in = new ClassPathResource(clientKeyStore.getPath()).getInputStream()) {
                KeyStore keyStore = KeyStore.getInstance("JKS");
                keyStore.load(in, clientKeyStore.getStorepass().toCharArray());

                RSAKey rsaKey = RSAKey.load(
                        keyStore,
                        clientKeyStore.getAlias(),
                        clientKeyStore.getKeypass().toCharArray()
                );

                rsaKeys.put(entry.getKey(), rsaKey);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao carregar keystore para o client: " + entry.getKey(), e);
            }
        }
    }

    public RSAKey getByClient(String client) {
        RSAKey rsaKey = rsaKeys.get(client.replace("-web", ""));
        if (rsaKey == null) {
            throw new IllegalArgumentException("Nenhuma chave encontrada para clientId: " + client);
        }
        return rsaKey;
    }

    public List<RSAKey> getAll() {
        return new ArrayList<>(rsaKeys.values());
    }

}

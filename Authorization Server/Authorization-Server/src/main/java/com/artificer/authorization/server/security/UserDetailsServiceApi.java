package com.artificer.authorization.server.security;

import com.artificer.authorization.server.domain.UsuarioApiService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceApi implements UserDetailsService {

    @Autowired
    private UsuarioApiService usuarioApiService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = usuarioApiService.buscarUsuario(email);

        final var simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + user.getTipo());

        return new User(
                user.getEmail(),
                user.getSenha(),
                List.of(simpleGrantedAuthority)
        );
    }
}
package com.artificer.usuarios.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyRole('ADMIN','CLIENT') or hasAuthority('SCOPE_usuarios:read')")
public @interface CanReadUsuarios {
}

package com.danielarrais.algafood.auth.domain.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;

public class AuthUser extends User {
    private String fullName;

    public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthUser(Usuario usuario) {
        super(usuario.getEmail(), usuario.getSenha(), Collections.emptyList());
        this.fullName = usuario.getNome();
    }
}

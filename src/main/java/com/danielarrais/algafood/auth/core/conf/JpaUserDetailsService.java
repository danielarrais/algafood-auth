package com.danielarrais.algafood.auth.core.conf;

import com.danielarrais.algafood.auth.domain.model.AuthUser;
import com.danielarrais.algafood.auth.domain.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public JpaUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario = usuarioRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário com e-mail informado não encontrado"));

        return new AuthUser(usuario);
    }
}

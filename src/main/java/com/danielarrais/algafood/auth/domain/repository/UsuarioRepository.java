package com.danielarrais.algafood.auth.domain.repository;

import com.danielarrais.algafood.auth.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("select u from Usuario u where u.email = :email")
    Optional<Usuario> findByEmail(String email);
}


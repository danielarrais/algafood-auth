package com.danielarrais.algafood.auth.domain.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String nome;
    private String email;
    private String senha;

    @ManyToMany
    @JoinTable(
            name = "usuario_grupo",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_id"))
    private Set<Grupo> grupos = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    private OffsetDateTime dataAtualizacao;

    public boolean isSenhaIgual(String senhaAtual) {
        return this.senha.equals(senhaAtual);
    }

    public void adicionarGrupo(Grupo formaPagamento) {
        getGrupos().add(formaPagamento);
    }

    public void removerGrupo(Grupo formaPagamento) {
        getGrupos().remove(formaPagamento);
    }
}
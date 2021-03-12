package com.danielarrais.algafood.auth.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String nome;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "grupo_permissao",
            joinColumns = @JoinColumn(name = "grupo_id"),
            inverseJoinColumns = @JoinColumn(name = "permissao_id"))
    private Set<Permissao> permissoes = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    private OffsetDateTime dataAtualizacao;
    
    public void adicionarPermissao(Permissao formaPagamento) {
        getPermissoes().add(formaPagamento);
    }

    public void removerPermissao(Permissao formaPagamento) {
        getPermissoes().remove(formaPagamento);
    }
}
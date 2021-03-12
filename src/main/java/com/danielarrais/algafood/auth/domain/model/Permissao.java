package com.danielarrais.algafood.auth.domain.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Permissao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String nome;
    private String descricao;

    @CreationTimestamp
    @Column(updatable = false)
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    private OffsetDateTime dataAtualizacao;
}
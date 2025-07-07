package com.kawsay.ia.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "perfil_psicologo")
@Data
@NoArgsConstructor
public class PerfilPsicologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false)
    private String nombreProfesional;

    private String especialidad;

    @Column(columnDefinition = "TEXT")
    private String descripcion;
}
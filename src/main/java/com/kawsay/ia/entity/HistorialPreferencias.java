package com.kawsay.ia.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "historial_preferencias")
public class HistorialPreferencias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial_preferencias")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    // Getters y setters
}


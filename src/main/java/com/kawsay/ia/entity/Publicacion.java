package com.kawsay.ia.entity;

import jakarta.persistence.*;

@Entity
@Table(name="publicacion")
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    private String titulo;
    private String contenido;
    private String imagen;

}

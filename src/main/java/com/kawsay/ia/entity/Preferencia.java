package com.kawsay.ia.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "preferencia")
@Data
public class Preferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_preferencia")
    private Long id;

    @Column(nullable = false, length = 50)
    private String tipo;         // “hobby”, “genero”, “narrativa”, “estado_animo”

    @Column(nullable = false, length = 100)
    private String valor;        // “lectura”, “ciencia ficción”, etc.

    public Preferencia() {}

    public Preferencia(Long id, String tipo, String valor) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
    }

}
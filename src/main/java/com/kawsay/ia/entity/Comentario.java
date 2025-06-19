package com.kawsay.ia.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    private Integer id;

    @Column(name = "contenido", columnDefinition = "TEXT")
    private String contenido;

    @CreationTimestamp
    @Column(name = "fecha_comentario", updatable = false)
    private LocalDateTime fechaComentario;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "publicacion_id", nullable = false)
    private Publicacion publicacion;

    @Builder
    public Comentario(String contenido, Usuario autor, Publicacion publicacion) {
        this.contenido = contenido;
        this.autor = autor;
        this.publicacion = publicacion;
    }
}
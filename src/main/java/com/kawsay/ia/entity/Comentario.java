package com.kawsay.ia.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "comentario_padre_id")
    private Comentario comentarioPadre;

    @OneToMany(mappedBy = "comentarioPadre")
    private List<Comentario> respuestas;



    @Builder
    public Comentario(String contenido, LocalDateTime fechaComentario, Usuario autor, Publicacion publicacion, Comentario comentarioPadre, List<Comentario> respuestas) {
        this.contenido = contenido;
        this.fechaComentario = fechaComentario;
        this.autor = autor;
        this.publicacion = publicacion;
        this.comentarioPadre = comentarioPadre;
        this.respuestas = respuestas;
    }

}
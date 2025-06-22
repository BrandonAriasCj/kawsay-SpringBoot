package com.kawsay.ia.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "reaccion")
public class Reaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reaccion")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @CreationTimestamp
    @Column(name = "fecha_reaccion", updatable = false)
    private LocalDateTime fechaReaccion;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "publicacion_id", referencedColumnName = "id_publicacion")
    private Publicacion publicacion;

    @ManyToOne
    @JoinColumn(name = "comentario_id")
    private Comentario comentario;


    public enum Tipo {
        LIKE, DISLIKE, LOVE, FIRE, SAD, FUNNY
    }

    @Builder
    public Reaccion(Tipo tipo, Usuario usuario, Publicacion publicacion, Comentario comentario, LocalDateTime fechaReaccion) {
        this.tipo = tipo;
        this.usuario = usuario;
        this.publicacion = publicacion;
        this.comentario = comentario;
        this.fechaReaccion = fechaReaccion;
    }





}

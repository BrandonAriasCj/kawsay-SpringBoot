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

    @Column(name = "tipo", length = 50, nullable = false)
    private String tipo;

    @CreationTimestamp
    @Column(name = "fecha_reaccion", updatable = false)
    private LocalDateTime fechaReaccion;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "publicacion_id", referencedColumnName = "id_publicacion")
    private Publicacion publicacion;

    @Builder
    public Reaccion(String tipo, Usuario usuario, Publicacion publicacion) {
        this.tipo = tipo;
        this.usuario = usuario;
        this.publicacion = publicacion;
    }





}

package com.kawsay.ia.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "publicacion")
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_publicacion")
    private Integer id;
    @Column(name = "titulo", length = 150)
    private String titulo;

    @Column(name = "contenido", columnDefinition = "TEXT")
    private String contenido;

    @CreationTimestamp
    @Column(name = "fecha_publicacion", updatable = false)
    private LocalDateTime fechaPublicacion;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "grupo_id", nullable = false)
    private Grupo grupo;

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Reaccion> reacciones;

    @Builder
    public Publicacion(Integer id, String titulo,String contenido, LocalDateTime fechaPublicacion, Usuario autor, Grupo grupo) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechaPublicacion = fechaPublicacion;
        this.autor = autor;
        this.grupo = grupo;
    }

}
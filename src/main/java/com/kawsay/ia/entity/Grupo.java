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
@Table(name = "grupo")
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "categoria", nullable = false, length = 100)
    private String categoria;


    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "creador_id", nullable = false)
    private Usuario creador;

    @ManyToOne
    @JoinColumn(name = "moderador_id", nullable = false)
    private Usuario moderador;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Publicacion> publicaciones;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private List<UsuarioGrupo> miembros;


    @Builder
    public Grupo(String nombre, String descripcion, String categoria, Usuario creador, Usuario moderador) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.creador = creador;
        this.moderador = moderador;
    }


}
package com.kawsay.ia.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;

    @Column(name = "correo_institucional", length = 100, nullable = false, unique = true)
    private String correoInstitucional;

    @Column(name = "contraseña", length = 100, nullable = false)
    private String contraseña;

    @ManyToOne
    @JoinColumn(name = "user_rol", nullable = false)
    private Rol rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<AiChatMemory> conversaciones;

    @OneToMany(mappedBy = "creador", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Grupo> gruposCreados;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Publicacion> publicaciones;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Reaccion> reacciones;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<UsuarioGrupo> gruposUnidos;


    @Builder
    public Usuario(String correoInstitucional, String contraseña, Rol rol) {
        this.correoInstitucional = correoInstitucional;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Perfil perfil;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
}
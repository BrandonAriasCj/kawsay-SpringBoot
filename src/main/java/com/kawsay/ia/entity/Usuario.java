package com.kawsay.ia.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
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

    // Getters y setters
}

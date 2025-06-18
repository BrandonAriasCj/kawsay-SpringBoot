package com.kawsay.ia.entity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


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
    private List<AiChatMemory> conversaciones;


    @Builder
    public Usuario(String correoInstitucional, String contraseña, Rol rol) {
        this.correoInstitucional = correoInstitucional;
        this.contraseña = contraseña;
        this.rol = rol;
    }


}

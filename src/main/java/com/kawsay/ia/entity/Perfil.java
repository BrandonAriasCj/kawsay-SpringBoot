package com.kawsay.ia.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "perfil")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil")
    private Long idPerfil;

    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "nombre_completo")
    private String nombreCompleto;

    private String carrera;

    private String descripcion;

    @Column(name = "url_foto_perfil")
    private String urlFotoPerfil;

    @Column(name = "perfil_completado", nullable = false)
    private boolean perfilCompletado = false;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "perfil_preferencias",
            joinColumns = @JoinColumn(name = "id_perfil"),
            inverseJoinColumns = @JoinColumn(name = "id_preferencia")
    )
    private List<Preferencia> preferencias = new ArrayList<>();
}
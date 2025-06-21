package com.kawsay.ia.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_grupo")
public class UsuarioGrupo {

    @EmbeddedId
    private UsuarioGrupoId id = new UsuarioGrupoId();

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @MapsId("grupoId")
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    @Column(name = "fecha_union")
    @CreationTimestamp
    private LocalDateTime fechaUnion;

    @Column(name = "estado", nullable = false)
    private boolean estado = true;

    public UsuarioGrupoId getId() {
        return id;
    }

    public void setId(UsuarioGrupoId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public LocalDateTime getFechaUnion() {
        return fechaUnion;
    }

    public void setFechaUnion(LocalDateTime fechaUnion) {
        this.fechaUnion = fechaUnion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}

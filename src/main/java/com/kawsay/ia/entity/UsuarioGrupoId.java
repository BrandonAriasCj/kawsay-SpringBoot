package com.kawsay.ia.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;


public class UsuarioGrupoId implements Serializable {
    private Integer usuarioId;
    private Integer grupoId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioGrupoId that = (UsuarioGrupoId) o;
        return Objects.equals(usuarioId, that.usuarioId) && Objects.equals(grupoId, that.grupoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, grupoId);
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }
    public UsuarioGrupoId() {
        // Constructor por defecto requerido por JPA
    }

    public UsuarioGrupoId(Integer usuarioId, Integer grupoId) {
        this.usuarioId = usuarioId;
        this.grupoId = grupoId;
    }

}
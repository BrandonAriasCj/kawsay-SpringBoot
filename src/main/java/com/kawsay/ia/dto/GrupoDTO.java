package com.kawsay.ia.dto;

import java.time.LocalDateTime;

public class GrupoDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private Integer creadorId;

    private Integer moderadorId;
    private LocalDateTime fechaCreacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getCreadorId() {
        return creadorId;
    }

    public void setCreadorId(Integer creadorId) {
        this.creadorId = creadorId;
    }

    public Integer getModeradorId() {
        return moderadorId;
    }

    public void setModeradorId(Integer moderadorId) {
        this.moderadorId = moderadorId;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public GrupoDTO() {

    }
}

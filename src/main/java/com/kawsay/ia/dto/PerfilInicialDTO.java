package com.kawsay.ia.dto;

import lombok.Data;

import java.util.List;

@Data
public class PerfilInicialDTO {
    private String nombreCompleto;
    private String carrera;
    private String descripcion;
    private List<String> preferencias;
}
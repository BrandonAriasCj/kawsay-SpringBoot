package com.kawsay.ia.dto;

import lombok.Data;

@Data
public class CitaAgendadaDTO {
    private Integer id;
    private String estudianteNombre;
    private String psicologoNombre;
    private String fechaCita;
    private String horaInicio;
    private String modalidad;
}
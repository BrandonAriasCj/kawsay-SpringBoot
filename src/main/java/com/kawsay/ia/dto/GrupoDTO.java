package com.kawsay.ia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrupoDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private boolean esPrivado;
    private Integer creadorId;
    private Integer moderadorId;
    private LocalDateTime fechaCreacion;
}

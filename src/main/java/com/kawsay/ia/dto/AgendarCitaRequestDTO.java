package com.kawsay.ia.dto;

import lombok.Data;

@Data
public class AgendarCitaRequestDTO {
    private Integer psicologoId;
    private String fecha;
    private String hora;
}
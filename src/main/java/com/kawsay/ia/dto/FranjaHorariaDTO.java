package com.kawsay.ia.dto;

import lombok.Data;

@Data
public class FranjaHorariaDTO {
    private int dayOfWeek; // día de la semana
    private String startTime; // "08:00"
    private String endTime;
    private String modality; // "VIRTUAL" o "PRESENCIAL"
}
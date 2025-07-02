package com.kawsay.ia.dto;

import lombok.Data;
import java.util.List;

@Data
public class ReglaDisponibilidadDTO {
    private Integer id;
    private String startDate;
    private String endDate;
    private int appointmentDuration;
    private List<FranjaHorariaDTO> timeSlots;
}
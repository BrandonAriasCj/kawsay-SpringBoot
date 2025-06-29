package com.kawsay.ia.mapper;
import com.kawsay.ia.dto.ReporteDTO;
import com.kawsay.ia.entity.Reporte;

public class ReporteMapper {

    public static ReporteDTO toDTO(Reporte reporte) {
        if (reporte == null) return null;

        return ReporteDTO.builder()
                .id(reporte.getId())
                .contenido(reporte.getContenido())
                .timestamp(reporte.getTimestamp())
                .usuario_id(String.valueOf(reporte.getUsuario().getId()))
                .build();
    }
}

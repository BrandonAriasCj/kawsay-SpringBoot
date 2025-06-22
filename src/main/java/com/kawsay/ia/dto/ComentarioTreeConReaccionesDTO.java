package com.kawsay.ia.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record ComentarioTreeConReaccionesDTO(
        Integer id,
        String contenido,
        Integer autorId,
        Integer publicacionId,
        LocalDateTime fechaCreacion,
        Map<String, Long> reacciones,
        List<ComentarioTreeConReaccionesDTO> respuestas
) {}

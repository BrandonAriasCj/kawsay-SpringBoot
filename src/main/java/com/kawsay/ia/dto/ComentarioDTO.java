package com.kawsay.ia.dto;

import java.time.LocalDateTime;

public record ComentarioDTO(
        Integer id,
        String contenido,
        Integer autorId,
        Integer publicacionId,
        LocalDateTime fechaCreacion
) {}

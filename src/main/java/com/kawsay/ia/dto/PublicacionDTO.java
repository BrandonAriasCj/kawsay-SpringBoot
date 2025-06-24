package com.kawsay.ia.dto;

import java.time.LocalDateTime;

public record PublicacionDTO(
        Integer id,
        String titulo,
        String contenido,
        LocalDateTime fechaPublicacion,
        Integer autorId,
        Integer grupoId
) {}

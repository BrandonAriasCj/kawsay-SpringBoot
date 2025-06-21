package com.kawsay.ia.dto;

import lombok.Data;

import java.time.LocalDateTime;


public record ReaccionDTO(
        Integer id,
        String tipo,
        Integer usuarioId,
        Integer publicacionId,
        LocalDateTime fechaCreacion
) {}
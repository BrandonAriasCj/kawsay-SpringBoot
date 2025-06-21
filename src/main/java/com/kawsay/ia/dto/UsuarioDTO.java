package com.kawsay.ia.dto;

import com.kawsay.ia.entity.Rol;

public record UsuarioDTO(
        Integer id,
        String correo,
        Integer rolId
) {}

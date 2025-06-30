package com.kawsay.ia.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RolTipo {
    ESTUDIANTE,
    MODERADOR,
    PSICOLOGO;

    @JsonCreator
    public static RolTipo from(String value) {
        return RolTipo.valueOf(value.toUpperCase());
    }
}

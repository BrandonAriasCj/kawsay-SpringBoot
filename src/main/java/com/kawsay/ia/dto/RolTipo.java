package com.kawsay.ia.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RolTipo {
    ESTUDIANTE,
    PSICOLOGO,
    MODERADOR;

    @JsonCreator
    public static RolTipo from(String value) {
        return RolTipo.valueOf(value.toUpperCase());
    }
}

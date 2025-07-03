package com.kawsay.ia.dto;

import lombok.Data;
import java.util.List;

@Data
public class PerfilDTO {
    private String email;
    private String nombreCompleto;
    private String carrera;
    private String descripcion;
    private String urlFotoPerfil;
    private boolean perfilCompletado;
}
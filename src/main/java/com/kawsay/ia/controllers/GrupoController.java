package com.kawsay.ia.controllers;


import com.kawsay.ia.dto.GrupoDTO;
import com.kawsay.ia.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")

public class GrupoController {
    @Autowired
    private GrupoRepository grupoRepository;

    @GetMapping
    public List<GrupoDTO> listarGrupos() {
        return grupoRepository.findAll().stream().map(grupo -> {
            GrupoDTO dto = new GrupoDTO();
            dto.id = grupo.getId();
            dto.nombre = grupo.getNombre();
            dto.descripcion = grupo.getDescripcion();
            dto.creadorId = grupo.getCreador().getId();
            return dto;
        }).toList();
    }
}

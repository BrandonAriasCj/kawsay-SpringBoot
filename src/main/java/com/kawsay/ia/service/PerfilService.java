package com.kawsay.ia.service;
import com.kawsay.ia.dto.PerfilDTO;
import com.kawsay.ia.entity.Perfil;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.entity.HistorialPreferencias;
import com.kawsay.ia.repository.PerfilRepository;
import com.kawsay.ia.repository.UsuarioRepository;
import com.kawsay.ia.repository.HistorialPreferenciasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerfilService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private HistorialPreferenciasRepository historialPreferenciasRepository;

    @Transactional(readOnly = true)
    public PerfilDTO getPerfilCompleto(String email) {
        Usuario usuario = usuarioRepository.findByCorreoInstitucional(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));


        Perfil perfil = perfilRepository.findByUsuario_Id(Long.valueOf(usuario.getId()))
                .orElseGet(() -> {
                    Perfil nuevoPerfil = new Perfil();
                    nuevoPerfil.setUsuario(usuario);
                    return nuevoPerfil;
                });

        List<String> preferencias = historialPreferenciasRepository.findByUsuario_Id(usuario.getId())
                .stream()
                .map(HistorialPreferencias::getContenido)
                .collect(Collectors.toList());

        return mapToDTO(usuario, perfil, preferencias);
    }

    @Transactional
    public PerfilDTO updatePerfil(String email, PerfilDTO perfilDTO) {
        Usuario usuario = usuarioRepository.findByCorreoInstitucional(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Perfil perfil = perfilRepository.findByUsuario_Id(Long.valueOf(usuario.getId()))
                .orElseGet(() -> {
                    Perfil nuevoPerfil = new Perfil();
                    nuevoPerfil.setUsuario(usuario);
                    return nuevoPerfil;
                });

        perfil.setNombreCompleto(perfilDTO.getNombreCompleto());
        perfil.setCarrera(perfilDTO.getCarrera());
        perfil.setDescripcion(perfilDTO.getDescripcion());
        perfil.setUrlFotoPerfil(perfilDTO.getUrlFotoPerfil());

        Perfil perfilGuardado = perfilRepository.save(perfil);


        List<String> preferencias = historialPreferenciasRepository.findByUsuario_Id(usuario.getId())
                .stream()
                .map(HistorialPreferencias::getContenido)
                .collect(Collectors.toList());

        return mapToDTO(usuario, perfilGuardado, preferencias);
    }

    private PerfilDTO mapToDTO(Usuario usuario, Perfil perfil, List<String> preferencias) {
        PerfilDTO dto = new PerfilDTO();
        dto.setEmail(usuario.getCorreoInstitucional());
        dto.setNombreCompleto(perfil.getNombreCompleto());
        dto.setCarrera(perfil.getCarrera());
        dto.setDescripcion(perfil.getDescripcion());
        dto.setUrlFotoPerfil(perfil.getUrlFotoPerfil());
        dto.setHistorialPreferencias(preferencias);
        return dto;
    }
}
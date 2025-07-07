package com.kawsay.ia.service;

import com.kawsay.ia.config.AuthUtils;
import com.kawsay.ia.dto.PerfilPsicologoDTO;
import com.kawsay.ia.entity.PerfilPsicologo;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.repository.PerfilPsicologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class PerfilPsicologoService {

    @Autowired private PerfilPsicologoRepository perfilPsicologoRepository;
    @Autowired private AuthUtils authUtils;

    @Transactional(readOnly = true)
    public Optional<PerfilPsicologoDTO> getMiPerfilPsicologo() {
        Usuario usuario = authUtils.getUsuarioAutenticado();
        return perfilPsicologoRepository.findByUsuarioId(usuario.getId()).map(this::convertToDto);
    }

    @Transactional
    public PerfilPsicologoDTO crearOActualizarPerfil(PerfilPsicologoDTO dto) {
        Usuario usuario = authUtils.getUsuarioAutenticado();

        PerfilPsicologo perfil = perfilPsicologoRepository.findByUsuarioId(usuario.getId())
                .orElse(new PerfilPsicologo());

        perfil.setUsuario(usuario);
        perfil.setNombreProfesional(dto.getNombreProfesional());
        perfil.setEspecialidad(dto.getEspecialidad());
        perfil.setDescripcion(dto.getDescripcion());

        PerfilPsicologo guardado = perfilPsicologoRepository.save(perfil);
        return convertToDto(guardado);
    }

    private PerfilPsicologoDTO convertToDto(PerfilPsicologo perfil) {
        PerfilPsicologoDTO dto = new PerfilPsicologoDTO();
        dto.setNombreProfesional(perfil.getNombreProfesional());
        dto.setEspecialidad(perfil.getEspecialidad());
        dto.setDescripcion(perfil.getDescripcion());
        return dto;
    }
}
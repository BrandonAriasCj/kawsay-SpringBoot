package com.kawsay.ia.service;

import com.kawsay.ia.dto.GrupoDTO;
import com.kawsay.ia.dto.PublicacionDTO;
import com.kawsay.ia.entity.*;
import com.kawsay.ia.repository.GrupoRepository;
import com.kawsay.ia.repository.PublicacionRepository;
import com.kawsay.ia.repository.UsuarioGrupoRepository;
import com.kawsay.ia.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GrupoService {

    private final GrupoRepository grupoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PublicacionRepository publicacionRepository;
    private final UsuarioService usuarioService;
    private final UsuarioGrupoRepository usuarioGrupoRepository;


    public List<GrupoDTO> obtenerTodosLosGrupos() {
        return grupoRepository.findAll().stream()
                .map(grupo -> {
                    GrupoDTO dto = new GrupoDTO();
                    dto.setId(grupo.getId());
                    dto.setNombre(grupo.getNombre());
                    dto.setDescripcion(grupo.getDescripcion());
                    dto.setCategoria(grupo.getCategoria());
                    dto.setCreadorId(grupo.getCreador().getId());
                    dto.setModeradorId(grupo.getModerador().getId());
                    dto.setFechaCreacion(grupo.getFechaCreacion());
                    return dto;
                })
                .toList();
    }

    public GrupoDTO crearGrupo(GrupoDTO dto) {
        Usuario creador = usuarioRepository.findById(dto.getCreadorId())
                .orElseThrow(() -> new RuntimeException("Creador no encontrado"));

        Usuario moderador = usuarioRepository.findById(dto.getModeradorId())
                .orElseThrow(() -> new RuntimeException("Moderador no encontrado"));

        Grupo grupo = Grupo.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .categoria(dto.getCategoria())
                .creador(creador)
                .moderador(moderador)
                .build();

        Grupo guardado = grupoRepository.save(grupo);

        GrupoDTO respuesta = new GrupoDTO();
        respuesta.setId(guardado.getId());
        respuesta.setNombre(guardado.getNombre());
        respuesta.setDescripcion(guardado.getDescripcion());
        respuesta.setCategoria(guardado.getCategoria());
        respuesta.setCreadorId(guardado.getCreador().getId());
        respuesta.setModeradorId(guardado.getModerador().getId());
        respuesta.setFechaCreacion(guardado.getFechaCreacion());

        return respuesta;
    }

    public List<PublicacionDTO> obtenerPublicacionesDeGrupo(Integer idGrupo) {
        Grupo grupo = grupoRepository.findById(idGrupo)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        return grupo.getPublicaciones().stream()
                .map(publicacion -> new PublicacionDTO(
                        publicacion.getId(),
                        publicacion.getContenido(),
                        publicacion.getFechaPublicacion(),
                        publicacion.getAutor().getId(),
                        grupo.getId()
                ))
                .toList();
    }

    public PublicacionDTO crearPublicacionEnGrupo(Integer idGrupo, PublicacionDTO dto) {
        Grupo grupo = grupoRepository.findById(idGrupo)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        Usuario autor = usuarioRepository.findById(dto.autorId())
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));

        Publicacion publicacion = Publicacion.builder()
                .contenido(dto.contenido())
                .grupo(grupo)
                .autor(autor)
                .build();

        Publicacion guardada = publicacionRepository.save(publicacion);

        return new PublicacionDTO(
                guardada.getId(),
                guardada.getContenido(),
                guardada.getFechaPublicacion(),
                autor.getId(),
                grupo.getId()
        );
    }

    @Transactional
    public void unirseAGrupo(Usuario usuario, Grupo grupo) {
        boolean yaEsMiembro = usuarioGrupoRepository.existsByUsuarioAndGrupo(usuario, grupo);
        if (yaEsMiembro) return;

        UsuarioGrupo ug = new UsuarioGrupo();
        ug.setUsuario(usuario);
        ug.setGrupo(grupo);
        usuarioGrupoRepository.save(ug);
    }


    @Transactional
    public void salirDeGrupo(Integer usuarioId, Integer grupoId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        Optional<UsuarioGrupo> relacion = grupo.getMiembros().stream()
                .filter(ug -> ug.getUsuario().getId().equals(usuarioId))
                .findFirst();

        if (relacion.isPresent()) {
            relacion.get().setEstado(false);
        } else {
            throw new RuntimeException("El usuario no pertenece al grupo.");
        }
    }


}

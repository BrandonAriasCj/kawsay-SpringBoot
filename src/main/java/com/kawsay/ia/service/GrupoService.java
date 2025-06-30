package com.kawsay.ia.service;

import com.kawsay.ia.config.AuthUtils;
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
    private final AuthUtils authUtils;


    public List<GrupoDTO> obtenerTodosLosGrupos() {
        return grupoRepository.findAll().stream()
                .map(grupo -> {
                    GrupoDTO dto = new GrupoDTO();
                    dto.setId(grupo.getId());
                    dto.setNombre(grupo.getNombre());
                    dto.setDescripcion(grupo.getDescripcion());
                    dto.setCategoria(grupo.getCategoria());
                    dto.setEsPrivado(Boolean.TRUE.equals(grupo.getEsPrivado()));
                    dto.setCreadorId(grupo.getCreador().getId());
                    dto.setModeradorId(grupo.getModerador().getId());
                    dto.setFechaCreacion(grupo.getFechaCreacion());
                    return dto;
                })
                .toList();
    }

    @Transactional
    public GrupoDTO crearGrupo(GrupoDTO dto) {
        Usuario creador = authUtils.getUsuarioAutenticado();

        // Si dto.getModeradorId() es null o igual al creador → el mismo usuario será moderador
        Usuario moderador = Optional.ofNullable(dto.getModeradorId())
                .filter(id -> !id.equals(creador.getId()))
                .flatMap(usuarioRepository::findById)
                .orElse(creador);

        Grupo grupo = Grupo.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .categoria(dto.getCategoria())
                .creador(creador)
                .moderador(moderador)
                .build();

        Grupo guardado = grupoRepository.save(grupo);

        return GrupoDTO.builder()
                .id(guardado.getId())
                .nombre(guardado.getNombre())
                .descripcion(guardado.getDescripcion())
                .categoria(guardado.getCategoria())
                .creadorId(creador.getId())
                .moderadorId(moderador.getId())
                .fechaCreacion(guardado.getFechaCreacion())
                .build();
    }



    public List<PublicacionDTO> obtenerPublicacionesDeGrupo(Integer idGrupo) {
        Grupo grupo = grupoRepository.findById(idGrupo)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        return grupo.getPublicaciones().stream()
                .map(publicacion -> new PublicacionDTO(
                        publicacion.getId(),
                        publicacion.getTitulo(),
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

        Usuario autor = authUtils.getUsuarioAutenticado(); // ✅ autenticado

        Publicacion publicacion = Publicacion.builder()
                .contenido(dto.contenido())
                .grupo(grupo)
                .autor(autor)
                .build();

        Publicacion guardada = publicacionRepository.save(publicacion);

        return new PublicacionDTO(
                guardada.getId(),
                guardada.getTitulo(),
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

    public List<GrupoDTO> obtenerGruposPorUsuario(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Grupo> grupos = grupoRepository.findByMiembros_Usuario(usuario);

        return grupos.stream()
                .map(grupo -> GrupoDTO.builder()
                        .id(grupo.getId())
                        .nombre(grupo.getNombre())
                        .descripcion(grupo.getDescripcion())
                        .categoria(grupo.getCategoria())
                        .creadorId(grupo.getCreador().getId())
                        .moderadorId(grupo.getModerador().getId())
                        .fechaCreacion(grupo.getFechaCreacion())
                        .build()
                )
                .toList();
    }

}

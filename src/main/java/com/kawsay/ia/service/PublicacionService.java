package com.kawsay.ia.service;

import com.kawsay.ia.config.AuthUtils;
import com.kawsay.ia.dto.ComentarioDTO;
import com.kawsay.ia.dto.PublicacionDTO;
import com.kawsay.ia.dto.ReaccionDTO;
import com.kawsay.ia.entity.*;
import com.kawsay.ia.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final ComentarioRepository comentarioRepository;
    private final ReaccionRepository reaccionRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioGrupoRepository usuarioGrupoRepository;
    private final GrupoRepository grupoRepository;
    private final AuthUtils authUtils;


    /**
     * Agrega un comentario a la publicación cuyo id es publicacionId.
     * Se espera que el ComentarioDTO contenga al menos el contenido del comentario
     * y el id del autor.
     */


    @Transactional
    public PublicacionDTO crearPublicacion(Integer idGrupo, PublicacionDTO dto) {
        Usuario autor = authUtils.getUsuarioAutenticado();

        Grupo grupo = grupoRepository.findById(idGrupo)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        // Verificar si el usuario pertenece al grupo
        boolean esMiembro = usuarioGrupoRepository.existsByUsuarioAndGrupo(autor, grupo);
        if (!esMiembro) {
            throw new AccessDeniedException("No estás unido al grupo.");
        }

        Publicacion nueva = Publicacion.builder()
                .titulo(dto.titulo())
                .contenido(dto.contenido())
                .fechaPublicacion(LocalDateTime.now())
                .autor(autor)
                .grupo(grupo)
                .build();

        publicacionRepository.save(nueva);

        return new PublicacionDTO(
                nueva.getId(),
                nueva.getTitulo(),
                nueva.getContenido(),
                nueva.getFechaPublicacion(),
                autor.getId(),
                grupo.getId()
        );
    }


    public ComentarioDTO agregarComentario(Integer publicacionId, ComentarioDTO comentarioDTO) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        Usuario autor = authUtils.getUsuarioAutenticado(); // ✅ desde el token

        Comentario comentario = Comentario.builder()
                .contenido(comentarioDTO.contenido())
                .autor(autor)
                .publicacion(publicacion)
                .build();

        Comentario comentarioGuardado = comentarioRepository.save(comentario);

        return new ComentarioDTO(
                comentarioGuardado.getId(),
                comentarioGuardado.getContenido(),
                autor.getId(),
                publicacion.getId(),
                comentarioGuardado.getFechaComentario()
        );
    }


    /**
     * Agrega una reacción a la publicación cuyo id es publicacionId.
     * Se espera que el ReaccionDTO contenga al menos el tipo de reacción y el id del autor.
     */
    public ReaccionDTO agregarReaccion(Integer publicacionId, ReaccionDTO reaccionDTO) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        Usuario usuario = authUtils.getUsuarioAutenticado(); // ✅ autenticado desde JWT

        Reaccion.Tipo tipoEnum = Reaccion.Tipo.valueOf(reaccionDTO.tipo().toUpperCase());

        Reaccion reaccion = Reaccion.builder()
                .tipo(tipoEnum)
                .usuario(usuario)
                .publicacion(publicacion)
                .fechaReaccion(LocalDateTime.now())
                .build();

        Reaccion reaccionGuardada = reaccionRepository.save(reaccion);

        return new ReaccionDTO(
                reaccionGuardada.getId(),
                reaccionGuardada.getTipo().name(),
                usuario.getId(),
                publicacion.getId(),
                reaccionGuardada.getFechaReaccion()
        );
    }



}

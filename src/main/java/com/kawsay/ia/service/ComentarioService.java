package com.kawsay.ia.service;

import com.kawsay.ia.config.AuthUtils;
import com.kawsay.ia.dto.ComentarioDTO;
import com.kawsay.ia.dto.ComentarioTreeConReaccionesDTO;
import com.kawsay.ia.dto.ReaccionDTO;
import com.kawsay.ia.entity.Comentario;
import com.kawsay.ia.entity.Publicacion;
import com.kawsay.ia.entity.Reaccion;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.mapper.ComentarioMapperConReacciones;
import com.kawsay.ia.repository.ComentarioRepository;
import com.kawsay.ia.repository.PublicacionRepository;
import com.kawsay.ia.repository.ReaccionRepository;
import com.kawsay.ia.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioService {


    @Autowired
    private ComentarioMapperConReacciones comentarioMapper;

    private final ComentarioRepository comentarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final PublicacionRepository publicacionRepository;
    private final ReaccionRepository reaccionRepository;
    private final AuthUtils authUtils;


    public ComentarioDTO agregarComentario(Integer publicacionId, ComentarioDTO dto) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        Usuario autor = authUtils.getUsuarioAutenticado(); // ✅ directamente desde el token

        Comentario comentario = new Comentario();
        comentario.setContenido(dto.contenido());
        comentario.setAutor(autor);
        comentario.setPublicacion(publicacion);
        comentario.setFechaComentario(LocalDateTime.now());

        Comentario guardado = comentarioRepository.save(comentario);

        return new ComentarioDTO(
                guardado.getId(),
                guardado.getContenido(),
                autor.getId(),
                publicacion.getId(),
                guardado.getFechaComentario()
        );
    }



    public ReaccionDTO reaccionar(Integer comentarioId, ReaccionDTO dto) {
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        Usuario usuario = authUtils.getUsuarioAutenticado(); // ✅ desde el token

        Reaccion reaccion = new Reaccion();
        reaccion.setComentario(comentario);
        reaccion.setUsuario(usuario);
        reaccion.setTipo(Reaccion.Tipo.valueOf(dto.tipo().toUpperCase()));
        reaccion.setFechaReaccion(LocalDateTime.now());

        Reaccion guardada = reaccionRepository.save(reaccion);

        return new ReaccionDTO(
                guardada.getId(),
                guardada.getTipo().name(),
                usuario.getId(),
                null, // si quieres puedes añadir `comentarioId`
                guardada.getFechaReaccion()
        );
    }



    public ComentarioDTO responderAComentario(Integer comentarioPadreId, ComentarioDTO dto) {
        Comentario padre = comentarioRepository.findById(comentarioPadreId)
                .orElseThrow(() -> new RuntimeException("Comentario padre no encontrado"));

        Usuario autor = authUtils.getUsuarioAutenticado(); // ✅ desde el token

        Comentario subcomentario = new Comentario();
        subcomentario.setContenido(dto.contenido());
        subcomentario.setAutor(autor);
        subcomentario.setFechaComentario(LocalDateTime.now());
        subcomentario.setComentarioPadre(padre);
        subcomentario.setPublicacion(padre.getPublicacion());

        Comentario guardado = comentarioRepository.save(subcomentario);

        return new ComentarioDTO(
                guardado.getId(),
                guardado.getContenido(),
                autor.getId(),
                guardado.getPublicacion().getId(),
                guardado.getFechaComentario()
        );
    }


    @Transactional
    public void editarComentario(Integer idComentario, ComentarioDTO dto) {
        Comentario comentario = comentarioRepository.findById(idComentario)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        Usuario actual = authUtils.getUsuarioAutenticado();

        if (!comentario.getAutor().getId().equals(actual.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes editar un comentario que no escribiste.");
        }

        comentario.setContenido(dto.contenido());
        comentario.setFechaComentario(LocalDateTime.now());
        comentarioRepository.save(comentario);
    }



    public List<ComentarioTreeConReaccionesDTO> obtenerComentariosAnidadosPorPublicacion(Integer publicacionId) {
        List<Comentario> raiz = comentarioRepository.findByPublicacionIdAndComentarioPadreIsNull(publicacionId);
        return raiz.stream()
                .map(comentarioMapper::toTreeDTO)
                .toList();
    }



}

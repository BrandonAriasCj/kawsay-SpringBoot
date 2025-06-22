package com.kawsay.ia.service;

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
import org.springframework.stereotype.Service;

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

    public ComentarioDTO agregarComentario(Integer publicacionId, ComentarioDTO dto) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        Usuario autor = usuarioRepository.findById(dto.autorId())
                .orElseThrow(() -> new RuntimeException("Autor del comentario no encontrado"));

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

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

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
                null,
                guardada.getFechaReaccion()
        );
    }



    public ComentarioDTO responderAComentario(Integer comentarioPadreId, ComentarioDTO dto) {
        Comentario padre = comentarioRepository.findById(comentarioPadreId)
                .orElseThrow(() -> new RuntimeException("Comentario padre no encontrado"));

        Usuario autor = usuarioRepository.findById(dto.autorId())
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));

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


    public List<ComentarioTreeConReaccionesDTO> obtenerComentariosAnidadosPorPublicacion(Integer publicacionId) {
        List<Comentario> raiz = comentarioRepository.findByPublicacionIdAndComentarioPadreIsNull(publicacionId);
        return raiz.stream()
                .map(comentarioMapper::toTreeDTO)
                .toList();
    }



}

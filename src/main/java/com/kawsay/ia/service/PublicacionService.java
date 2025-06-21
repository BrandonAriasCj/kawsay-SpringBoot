package com.kawsay.ia.service;

import com.kawsay.ia.dto.ComentarioDTO;
import com.kawsay.ia.dto.ReaccionDTO;
import com.kawsay.ia.entity.Comentario;
import com.kawsay.ia.entity.Publicacion;
import com.kawsay.ia.entity.Reaccion;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.repository.ComentarioRepository;
import com.kawsay.ia.repository.PublicacionRepository;
import com.kawsay.ia.repository.ReaccionRepository;
import com.kawsay.ia.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final ComentarioRepository comentarioRepository;
    private final ReaccionRepository reaccionRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Agrega un comentario a la publicación cuyo id es publicacionId.
     * Se espera que el ComentarioDTO contenga al menos el contenido del comentario
     * y el id del autor.
     */


    public ComentarioDTO agregarComentario(Integer publicacionId, ComentarioDTO comentarioDTO) {
        // Buscar la publicación
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        // Obtener el autor del comentario por su id
        Usuario autor = usuarioRepository.findById(comentarioDTO.autorId())
                .orElseThrow(() -> new RuntimeException("Autor del comentario no encontrado"));

        // Crear la entidad Comentario (asumiendo que tienes un builder en Comentario)
        Comentario comentario = Comentario.builder()
                .contenido(comentarioDTO.contenido())
                .autor(autor)
                .publicacion(publicacion)
                .build();

        Comentario comentarioGuardado = comentarioRepository.save(comentario);

        // Retornar el DTO construido a partir del comentario guardado
        return new ComentarioDTO(
                comentarioGuardado.getId(),
                comentarioGuardado.getContenido(),
                comentarioGuardado.getAutor().getId(),
                comentarioGuardado.getPublicacion().getId(),
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

        Usuario usuario = usuarioRepository.findById(reaccionDTO.usuarioId())
                .orElseThrow(() -> new RuntimeException("Autor de la reacción no encontrado"));

        Reaccion reaccion = Reaccion.builder()
                .tipo(reaccionDTO.tipo())
                .usuario(usuario) // ← CORRECTO: esto sí coincide con tu entidad
                .publicacion(publicacion)
                .build();

        Reaccion reaccionGuardada = reaccionRepository.save(reaccion);

        return new ReaccionDTO(
                reaccionGuardada.getId(),
                reaccionGuardada.getTipo(),
                reaccionGuardada.getUsuario().getId(),
                reaccionGuardada.getPublicacion().getId(),
                reaccionGuardada.getFechaReaccion() // ← lo mapeas a 'fechaCreacion' del DTO
        );
    }

}

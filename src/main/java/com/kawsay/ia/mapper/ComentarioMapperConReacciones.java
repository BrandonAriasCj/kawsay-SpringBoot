package com.kawsay.ia.mapper;

import com.kawsay.ia.dto.ComentarioTreeConReaccionesDTO;
import com.kawsay.ia.entity.Comentario;
import com.kawsay.ia.entity.Reaccion;
import com.kawsay.ia.repository.ReaccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ComentarioMapperConReacciones {

    private final ReaccionRepository reaccionRepository;

    public ComentarioTreeConReaccionesDTO toTreeDTO(Comentario comentario) {
        // Obtener reacciones agrupadas
        Map<String, Long> reacciones = reaccionRepository
                .contarReaccionesPorTipoComentario(comentario.getId())
                .stream()
                .collect(Collectors.toMap(
                        row -> ((Reaccion.Tipo) row[0]).name(),
                        row -> (Long) row[1]
                ));

        // Recursividad en respuestas
        List<ComentarioTreeConReaccionesDTO> respuestas = comentario.getRespuestas() != null
                ? comentario.getRespuestas().stream()
                .sorted(Comparator.comparing(Comentario::getFechaComentario))
                .map(this::toTreeDTO)
                .toList()
                : List.of();

        return new ComentarioTreeConReaccionesDTO(
                comentario.getId(),
                comentario.getContenido(),
                comentario.getAutor().getId(),
                comentario.getPublicacion() != null ? comentario.getPublicacion().getId() : null,
                comentario.getFechaComentario(),
                reacciones,
                respuestas
        );
    }
}

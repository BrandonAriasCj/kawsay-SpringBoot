package com.kawsay.ia.service;


import com.kawsay.ia.config.AuthUtils;
import com.kawsay.ia.entity.Publicacion;
import com.kawsay.ia.entity.Reaccion;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.repository.PublicacionRepository;
import com.kawsay.ia.repository.ReaccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service

@RequiredArgsConstructor
public class ReaccionService {


    private final AuthUtils authUtils;
    private final ReaccionRepository reaccionRepository;
    private  final PublicacionRepository publicacionRepository;


    @Transactional
    public String toggleReaccion(Integer publicacionId, String tipo) {
        Usuario usuario = authUtils.getUsuarioAutenticado();
        Reaccion.Tipo tipoEnum = Reaccion.Tipo.valueOf(tipo.toUpperCase());

        Optional<Reaccion> existente = reaccionRepository.findByUsuarioIdAndPublicacionIdAndTipo(
                usuario.getId(), publicacionId, tipoEnum);

        if (existente.isPresent()) {
            reaccionRepository.delete(existente.get());
            return "Reacción eliminada.";
        } else {
            Publicacion publicacion = publicacionRepository.findById(publicacionId)
                    .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

            Reaccion nueva = Reaccion.builder()
                    .usuario(usuario)
                    .publicacion(publicacion)
                    .tipo(tipoEnum)
                    .fechaReaccion(LocalDateTime.now())
                    .build();

            reaccionRepository.save(nueva);
            return "Reacción registrada.";
        }
    }

}

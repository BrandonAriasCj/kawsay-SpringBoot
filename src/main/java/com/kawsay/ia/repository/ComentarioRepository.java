package com.kawsay.ia.repository;
import com.kawsay.ia.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    List<Comentario> findByPublicacionIdAndComentarioPadreIsNull(Integer publicacionId);
}

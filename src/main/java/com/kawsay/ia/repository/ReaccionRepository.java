package com.kawsay.ia.repository;
import com.kawsay.ia.entity.Reaccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReaccionRepository extends JpaRepository<Reaccion, Integer> {
    List<Reaccion> findByPublicacionId(Integer idPublicacion);

}

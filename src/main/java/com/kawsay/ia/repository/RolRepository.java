package com.kawsay.ia.repository;
import com.kawsay.ia.dto.RolTipo;
import com.kawsay.ia.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol getRolById(Integer id);
    Optional<Rol> findByDenominacion(RolTipo denominacion);
    List<Rol> findAllByDenominacion(RolTipo denominacion);

}

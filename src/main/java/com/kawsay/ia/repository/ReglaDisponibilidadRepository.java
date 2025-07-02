package com.kawsay.ia.repository;

import com.kawsay.ia.entity.ReglaDisponibilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReglaDisponibilidadRepository extends JpaRepository<ReglaDisponibilidad, Integer> {
    List<ReglaDisponibilidad> findByPsicologoId(Integer psicologoId);
}
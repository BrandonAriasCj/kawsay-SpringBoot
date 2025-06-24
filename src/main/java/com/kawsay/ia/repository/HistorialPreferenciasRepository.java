package com.kawsay.ia.repository;
import com.kawsay.ia.entity.HistorialPreferencias;
import com.kawsay.ia.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialPreferenciasRepository extends JpaRepository<HistorialPreferencias, Integer> {
    List<HistorialPreferencias> findByUsuario_Id(Integer usuarioId);
}
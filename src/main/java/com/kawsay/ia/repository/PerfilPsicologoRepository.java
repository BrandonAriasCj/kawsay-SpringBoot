package com.kawsay.ia.repository;

import com.kawsay.ia.entity.PerfilPsicologo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PerfilPsicologoRepository extends JpaRepository<PerfilPsicologo, Integer> {
    Optional<PerfilPsicologo> findByUsuarioId(Integer usuarioId);
}
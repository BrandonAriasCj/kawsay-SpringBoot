package com.kawsay.ia.repository;

import com.kawsay.ia.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findByUsuario_Id(Long idUsuario);
}

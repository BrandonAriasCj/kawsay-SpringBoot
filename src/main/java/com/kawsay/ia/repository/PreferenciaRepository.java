package com.kawsay.ia.repository;

import com.kawsay.ia.entity.Preferencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PreferenciaRepository extends JpaRepository<Preferencia, Long> {

    // Buscar una preferencia específica por su valor exacto
    Optional<Preferencia> findByValor(String valor);

    // Buscar todas las preferencias de un tipo concreto (ej: "hobby", "genero")
    List<Preferencia> findByTipo(String tipo);

    // Buscar por combinación única (para evitar duplicados si lo usas así)
    Optional<Preferencia> findByTipoAndValor(String tipo, String valor);
}
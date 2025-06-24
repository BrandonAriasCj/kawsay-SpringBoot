package com.kawsay.ia.repository;

import com.kawsay.ia.entity.Grupo;
import com.kawsay.ia.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrupoRepository extends JpaRepository<Grupo, Integer> {
    List<Grupo> findByCreador(Usuario creador);
}

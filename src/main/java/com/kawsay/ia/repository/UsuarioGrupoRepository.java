package com.kawsay.ia.repository;

import com.kawsay.ia.entity.Grupo;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.entity.UsuarioGrupo;
import com.kawsay.ia.entity.UsuarioGrupoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioGrupoRepository extends JpaRepository<UsuarioGrupo, UsuarioGrupoId > {
    List<UsuarioGrupo> findByUsuarioId(Integer usuarioId);
    List<UsuarioGrupo> findByGrupoId(Integer grupoId);
}

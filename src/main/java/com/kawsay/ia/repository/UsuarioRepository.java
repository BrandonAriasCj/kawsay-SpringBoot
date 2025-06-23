package com.kawsay.ia.repository;
import com.kawsay.ia.entity.Rol;
import com.kawsay.ia.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario getUsuariosById(Integer userId);

    List<Usuario> findByRol(Rol rol);
}

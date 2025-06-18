package com.kawsay.ia.repositoty;
import com.kawsay.ia.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}

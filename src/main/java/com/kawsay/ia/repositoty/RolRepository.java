package com.kawsay.ia.repositoty;
import com.kawsay.ia.entity.Rol;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RolRepository extends JpaRepository<Rol, Integer> {
}

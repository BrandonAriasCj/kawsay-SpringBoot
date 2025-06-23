package com.kawsay.ia.service;
import com.kawsay.ia.entity.Rol;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.repository.RolRepository;
import com.kawsay.ia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAllUsuarios() {

        return usuarioRepository.findAll();
    }


    public void crearUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }


    @Autowired
    RolRepository roleRepository;
    @Autowired
    UsuarioRepository userRepository;
    public List<Usuario> findAllPsicologos() {
        Rol psicologoRol = roleRepository.getRolById((Integer) 2);
        List<Usuario> psicologos = userRepository.findByRol(psicologoRol);
        return psicologos;
    }

    public List<Usuario> findAllAlumnos() {
        Rol usuarioRol = roleRepository.getRolById((Integer) 1);

        List<Usuario> psicologos = userRepository.findByRol(usuarioRol);
        return psicologos;
    }
}

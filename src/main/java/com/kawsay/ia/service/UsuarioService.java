package com.kawsay.ia.service;
import com.kawsay.ia.entity.Usuario;
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
}

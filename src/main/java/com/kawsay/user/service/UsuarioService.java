package com.kawsay.user.service;

import com.kawsay.user.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public interface UsuarioService {
    void guardarOActualizar(String idUsuario, String correoInstitucional) throws SQLException;
    List<Usuario> listarTodos() throws SQLException;
}


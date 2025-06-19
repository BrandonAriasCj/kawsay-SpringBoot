package com.kawsay.user.dao;

import com.kawsay.user.model.Usuario;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;



public interface UsuarioDAO {
    void guardarOActualizar(String idUsuario, String correoInstitucional) throws SQLException;
    List<Usuario> listarTodos() throws SQLException;
}

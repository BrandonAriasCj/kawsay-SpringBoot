package com.kawsay.user.service.Impl;

import com.kawsay.user.dao.UsuarioDAO;
import com.kawsay.user.model.Usuario;
import com.kawsay.user.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDAO usuarioDAO;

    @Autowired
    public UsuarioServiceImpl(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public void guardarOActualizar(String idUsuario, String correoInstitucional) throws SQLException {
        usuarioDAO.guardarOActualizar(idUsuario, correoInstitucional);
    }

    @Override
    public List<Usuario> listarTodos() throws SQLException {
        return usuarioDAO.listarTodos();
    }
}

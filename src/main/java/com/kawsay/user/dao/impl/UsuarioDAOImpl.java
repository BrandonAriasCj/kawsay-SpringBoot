package com.kawsay.user.dao.impl;

import com.kawsay.user.dao.UsuarioDAO;
import com.kawsay.user.model.Usuario;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class UsuarioDAOImpl implements UsuarioDAO {

    private final DataSource dataSource;

    public UsuarioDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void guardarOActualizar(String idUsuario, String correoInstitucional) throws SQLException {
        String nombreDefecto = "Usuario"; // puedes personalizarlo si quieres

        String sql = "INSERT INTO usuario (id_usuario, nombre, correo_institucional) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE correo_institucional = VALUES(correo_institucional)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idUsuario);
            stmt.setString(2, nombreDefecto);
            stmt.setString(3, correoInstitucional);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, nombre, correo_institucional FROM usuario";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getString("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCorreoInstitucional(rs.getString("correo_institucional"));
                usuarios.add(usuario);
            }
        }

        return usuarios;
    }
}

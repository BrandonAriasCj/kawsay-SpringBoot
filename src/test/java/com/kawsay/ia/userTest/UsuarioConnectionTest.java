package com.kawsay.ia.userTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

@CrossOrigin(origins = "http://localhost:5173")
@SpringBootTest
public class UsuarioConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void guardarUsuarioEnBD() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO usuario (id_usuario, nombre, correo_institucional) VALUES (?, ?, ?)")) {

            stmt.setString(1, "test-usr-001");
            stmt.setString(2, "Test Nombre");
            stmt.setString(3, "test@correo.com");

            int filas = stmt.executeUpdate();
            assertEquals(1, filas, "Debe insertar exactamente una fila");

            System.out.println("✅ Usuario insertado correctamente");

        } catch (Exception e) {
            fail("❌ Error durante la inserción: " + e.getMessage());
        }
    }
}

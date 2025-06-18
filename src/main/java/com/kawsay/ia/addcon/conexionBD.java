import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class conexionBD {
    public static void main(String[] args) {
        // Datos de conexión
        String url = "jdbc:postgresql://localhost:5432/doc_store";
        String usuario = "myuser";
        String contraseña = "secret123";

        // Consulta de inserción con parámetros
        String sql = "INSERT INTO usuario (correo_institucional, contraseña, user_rol) VALUES (?, ?, ?)";


        try (
                Connection conexion = DriverManager.getConnection(url, usuario, contraseña);
                PreparedStatement statement = conexion.prepareStatement(sql)
        ) {
            // Reemplazar parámetros en orden (1-based index)
            statement.setString(1, "elm@correo.com");
            statement.setString(2, "contrasena123");
            statement.setInt(3, 1); // Por ejemplo, rol 1

            int filasAfectadas = statement.executeUpdate();
            System.out.println("Filas insertadas: " + filasAfectadas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



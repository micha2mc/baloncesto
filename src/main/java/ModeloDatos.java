import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.sql.*;

public class ModeloDatos {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModeloDatos.class);
    private static final String MESSAGE_ERROR = "El error es: ";

    private Connection con;
    private Statement set;
    private ResultSet rs;

    public void abrirConexion() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Con variables de entorno
            String dbHost = System.getenv().get("DATABASE_HOST");
            String dbPort = System.getenv().get("DATABASE_PORT");
            String dbName = System.getenv().get("DATABASE_NAME");
            String dbUser = System.getenv().get("DATABASE_USER");
            String dbPass = System.getenv().get("DATABASE_PASS");

            String url = dbHost + ":" + dbPort + "/" + dbName;
            con = DriverManager.getConnection(url, dbUser, dbPass);

        } catch (Exception e) {
            // No se ha conectado
            LOGGER.error("No se ha podido conectar");
            LOGGER.error(MESSAGE_ERROR + e.getMessage());
        }
    }

    public boolean existeJugador(String nombre) {
        boolean existe = false;
        String cad;
        try {
            set = con.createStatement();
            rs = set.executeQuery("SELECT * FROM Jugadores");
            while (rs.next()) {
                cad = rs.getString("Nombre");
                cad = cad.trim();
                if (cad.compareTo(nombre.trim()) == 0) {
                    existe = true;
                }
            }
            rs.close();
            set.close();
        } catch (Exception e) {
            // No lee de la tabla
            LOGGER.error("No lee de la tabla");
            LOGGER.error(MESSAGE_ERROR + e.getMessage());
        }
        return (existe);
    }

    public void actualizarJugador(String nombre) {
        String query = "UPDATE Jugadores SET votos=votos+1 WHERE nombre  LIKE ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + nombre + "%");
            preparedStatement.executeUpdate();
            rs.close();
            set.close();
        } catch (Exception e) {
            // No modifica la tabla

            LOGGER.error("No modifica la tabla");
            LOGGER.error(MESSAGE_ERROR + e.getMessage());
        }
    }

    public void insertarJugador(String nombre) {
        String sql = "INSERT INTO Jugadores  (nombre, votos) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setInt(2, 1);
            preparedStatement.executeUpdate();
            rs.close();
            set.close();
        } catch (Exception e) {
            // No inserta en la tabla
            LOGGER.error("No inserta en la tabla");
            LOGGER.error(MESSAGE_ERROR + e.getMessage());
        }
    }

    public void cerrarConexion() {
        try {
            con.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

}

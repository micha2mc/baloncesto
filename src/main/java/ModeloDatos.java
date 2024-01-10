import model.Jugador;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModeloDatos implements DataService {

    private static final Logger logger = LogManager.getLogger("ModeloDatos");
    private static final String MESSAGE_ERROR = "El error es: {}";

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
            logger.info("No se ha podido conectar");
            logger.error(MESSAGE_ERROR, e.getMessage());
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
            logger.info("No lee de la tabla");
            logger.error(MESSAGE_ERROR, e.getMessage());
        }
        return (existe);
    }

    public void actualizarJugador(String nombre, int sumVotos) {
        String query = "UPDATE Jugadores SET votos=? WHERE nombre  LIKE ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, sumVotos);
            preparedStatement.setString(2, "%" + nombre + "%");
            preparedStatement.executeUpdate();
            rs.close();
            set.close();
        } catch (Exception e) {
            // No modifica la tabla

            logger.info("No modifica la tabla");
            logger.error(MESSAGE_ERROR, e.getMessage());
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
            logger.info("No inserta en la tabla");
            logger.error(MESSAGE_ERROR, e.getMessage());
        }
    }

    public void cerrarConexion() {
        try {
            con.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void resetVotos() {
        String query = "UPDATE Jugadores SET votos=0";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.executeUpdate();
            rs.close();
            set.close();
        } catch (Exception e) {
            logger.error(MESSAGE_ERROR, e.getMessage());
        }
    }

    public List<Jugador> getAllJugadores() {
        List<Jugador> listJug = new ArrayList<>();
        logger.info("Method getAllJugadores start");
        try {
            set = con.createStatement();
            rs = set.executeQuery("SELECT * FROM Jugadores");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int votos = rs.getInt("votos");
                Jugador jugador = new Jugador(id, nombre, votos);

                listJug.add(jugador);
            }
            rs.close();
            set.close();
        } catch (Exception e) {
            logger.error(MESSAGE_ERROR, e.getMessage());
        }
        return listJug;
    }

    @Override
    public int getListOfVotos(String nombre) {
        int numVotos = 0;
        try {
            PreparedStatement pStatement = con.prepareStatement("SELECT * FROM Jugadores WHERE nombre = ?");
            pStatement.setString(1, nombre);
            rs = pStatement.executeQuery();
            while (rs.next()) {
                numVotos = rs.getInt("votos");
            }
            rs.close();
            set.close();
        } catch (Exception e) {
            // No lee de la tabla
            logger.info("No lee de la tabla");
            logger.error(MESSAGE_ERROR, e.getMessage());
        }
        logger.info("El número de votos del jugador {} es {}", nombre, numVotos);
        return numVotos;
    }
}

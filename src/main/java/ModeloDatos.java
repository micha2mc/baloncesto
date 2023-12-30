import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModeloDatos {

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
            System.err.println("No se ha podido conectar");
            System.err.println(MESSAGE_ERROR + e.getMessage());
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
            System.err.println("No lee de la tabla");
            System.err.println(MESSAGE_ERROR + e.getMessage());
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

            System.err.println("No modifica la tabla");
            System.err.println(MESSAGE_ERROR + e.getMessage());
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
            System.err.println("No inserta en la tabla");
            System.err.println(MESSAGE_ERROR + e.getMessage());
        }
    }

    public void cerrarConexion() {
        try {
            con.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void resetVotos() {
        String query = "UPDATE Jugadores SET votos=0";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.executeUpdate();
            rs.close();
            set.close();
        } catch (Exception e) {
            System.err.println("No modifica la tabla");
            System.err.println(MESSAGE_ERROR + e.getMessage());
        }
    }

    public List<Jugador> getAllJugadores(){
        List<Jugador> listJug = new ArrayList<>();
        try {
            set = con.createStatement();
            rs = set.executeQuery("SELECT * FROM Jugadores");
            while (rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int votos = rs.getInt("votos");
                Jugador jugador = new Jugador(id, nombre, votos);
                listJug.add(jugador);
            }
            rs.close();
            set.close();
        } catch (Exception e) {
            System.err.println("No modifica la tabla");
            System.err.println(MESSAGE_ERROR + e.getMessage());
        }
        return listJug;
    }
}

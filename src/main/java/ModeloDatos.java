import java.sql.*;

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
        int numeroVotos = getNumeroVotos(nombre);
        String query = "UPDATE Jugadores SET votos=? WHERE nombre  LIKE ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, numeroVotos + 1);
            preparedStatement.setString(2, "%" + nombre + "%");
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

    private int getNumeroVotos(String nombre) {
        int number = 0;
        String querySelect = "SELECT * FROM Jugadores WHERE nombre = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(querySelect)) {
            preparedStatement.setString(1, nombre);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                number = rs.getInt("votos");
            }
            rs.close();
            set.close();
        } catch (Exception e) {
            // No inserta en la tabla
            System.err.println("No inserta en la tabla");
            System.err.println(MESSAGE_ERROR + e.getMessage());
        }
        return number;
    }

}

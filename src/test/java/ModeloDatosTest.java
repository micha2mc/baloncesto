import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ModeloDatosTest {

    @InjectMocks
    private ModeloDatos modeloDatos;
    @Mock
    Connection mockConnection;

    @Mock
    Statement set;

    @Mock
    PreparedStatement mockPreparedStatement;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testExisteJugador() {
        System.out.println("Prueba de existeJugador");
        String nombre = "";
        ModeloDatos instance = new ModeloDatos();
        boolean expResult = false;
        boolean result = instance.existeJugador(nombre);
        assertEquals(expResult, result);
        //fail("Fallo forzado.");
    }

    @Test
    void testActualizarJugador() throws SQLException {
        // Arrange
        String nombreJugador = "JugadorParaActualizar";
        when(mockConnection.prepareStatement(any())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // 1 fila actualizada

        // Act
        modeloDatos.actualizarJugador(nombreJugador);

        // Assert
        verify(mockPreparedStatement).setString(1, "%" + nombreJugador + "%");
        verify(mockPreparedStatement).executeUpdate();
    }
}

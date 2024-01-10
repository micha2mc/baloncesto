import model.Jugador;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class Acb extends HttpServlet implements Serializable {

    private ModeloDatos bd;
    private VotosService votosService;

    public Acb(VotosService votosService) {
        this.votosService = votosService;
    }

    @Override
    public void init(ServletConfig cfg) throws ServletException {
        bd = new ModeloDatos();
        bd.abrirConexion();
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession s = req.getSession(true);
        String nombreP = req.getParameter("txtNombre");
        String nombre = req.getParameter("R1");
        String resetVotos = req.getParameter("B3");
        if ("B3".equalsIgnoreCase(resetVotos)) {
            bd.resetVotos();
            res.sendRedirect(res.encodeRedirectURL("index.html"));
        } else if ("B4".equalsIgnoreCase(req.getParameter("B4"))) {
            List<Jugador> listJugadores = bd.getAllJugadores();
            s.setAttribute("jugadores", listJugadores);
            // Llamada a la página jsp con la tabla de Votos
            res.sendRedirect(res.encodeRedirectURL("VerVotos.jsp"));
        } else {
            if (nombre.equals("Otros")) {
                nombre = req.getParameter("txtOtros");
            }
            if (bd.existeJugador(nombre)) {
                int sumVotos = votosService.sumarVotos(nombre);
                bd.actualizarJugador(nombre, sumVotos);
            } else {
                bd.insertarJugador(nombre);
            }
            s.setAttribute("nombreCliente", nombreP);
            // Llamada a la página jsp que nos da las gracias
            res.sendRedirect(res.encodeRedirectURL("TablaVotos.jsp"));
        }

    }

    @Override
    public void destroy() {
        bd.cerrarConexion();
        super.destroy();
    }
}

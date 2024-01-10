import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PruebasPhantomjsIT {
    private static final String URL = "http://localhost:8080/Baloncesto/";
    private static WebDriver driver = null;

    private final By registerPageLocator = By.xpath("//table[@aria-describedby='jugadores']");


    @BeforeEach
    void setUp() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/usr/bin/phantomjs");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{"--web-security=no", "--ignore-ssl-errors=yes"});
        driver = new PhantomJSDriver(caps);
        driver.navigate().to(URL);
    }

    @Test
    void tituloIndexTest() {
        System.out.println("************************tituloIndexTest*******************");
        assertEquals("Votacion mejor jugador liga ACB", driver.getTitle(), "El titulo no es correcto");
        System.out.println(driver.getTitle());
    }

    @Test
    void botonVotosCeroTest() {
        System.out.println("************************botonVotosCeroTest*******************");
        //1.- Localizo el boton "poner votos a cero" y es pulsado
        driver.findElement(By.name("B3")).click();
        System.out.println("Votos reseteados");

        //2.- Localizo el boton "ver votos" y es pulsado
        driver.findElement(By.name("B4")).click();
        System.out.println("botón verVotos pulsado");
        //3.- Localiza la nueva página (tabla) y compruebo los valores de los votos
        if (driver.findElement(registerPageLocator).isDisplayed()) {
            List<WebElement> listaFilas = driver.findElements(By.className("filas"));
            for (int i = 2; i <= listaFilas.size(); ) {
                System.out.println("Nombre del jugador: " + listaFilas.get(i - 1).getText());
                assertEquals("0", listaFilas.get(i).getText());
                i = i + 3;
            }
        } else {
            System.out.println("Tabla no localizada.");
        }
    }

    @Test
    void botonOtroTest() {
        System.out.println("************************botonOtroTest*******************");
        By radioOtrosLocator = By.xpath("//input[@value='Otros']");
        By nameOtrosLocator = By.name("txtOtros");
        String nombreNuevoJuagor = "Test";

        //1.- seleccion de Otros, insercion de nuevo jugador y click en Votar
        driver.findElement(radioOtrosLocator).click();
        driver.findElement(nameOtrosLocator).sendKeys(nombreNuevoJuagor);
        driver.findElement(By.name("B1")).click();
        System.out.println("Boton votar pulsado");

        //2.- Confirmar página "Gracias" y volver a la página principal
        if (driver.findElement(By.className("resultado")).isDisplayed()) {
            System.out.println("Pagina de las Gracias");
            driver.findElement(By.linkText("Ir al comienzo")).click();
            //3.- click en el boton ver votos y comprobacion de votos del nuevo jugador.
            driver.findElement(By.name("B4")).click();
            if (driver.findElement(registerPageLocator).isDisplayed()) {
                System.out.println("Ver la tabla de votos");
                List<WebElement> listaFilas = driver.findElements(By.className("filas"));
                for (int i = 0; i < listaFilas.size(); i++) {
                    if (nombreNuevoJuagor.equalsIgnoreCase(listaFilas.get(i).getText())) {
                        System.out.println("Nombre del nuevo jugador encontrado");
                        assertEquals("1", listaFilas.get(i + 1).getText());
                    }
                }
            }
        } else {
            System.out.println("Nuevo jugador no insertado.");
        }
    }

    @AfterEach
    void tearDown() {
        driver.close();
        driver.quit();
    }
}
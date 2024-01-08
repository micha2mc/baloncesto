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

    By registerPageLocator = By.xpath("//table[@aria-describedby='jugadores']");

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
        assertEquals("Votacion mejor jugador liga ACB", driver.getTitle(), "El titulo no es correcto");
        System.out.println(driver.getTitle());

    }

    @Test
    void botonVotosCeroTest() {

        //1.- Localizo el boton "poner votos a cero" y es pulsado
        driver.findElement(By.name("B3")).click();
        System.out.println("Votos reseteatos");

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
        }
    }

    @AfterEach
    void tearDown() {
        driver.close();
        driver.quit();
    }
}
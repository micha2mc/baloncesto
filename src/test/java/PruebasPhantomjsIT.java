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
    private static WebDriver driver = null;

    By registerPageLocator = By.xpath("//table[@aria-describedby='jugadores']");

    @Test
    void tituloIndexTest() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/usr/bin/phantomjs");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{"--web-security=no", "--ignore-ssl-errors=yes"});
        driver = new PhantomJSDriver(caps);
        driver.navigate().to("http://localhost:8080/Baloncesto/");
        assertEquals("Votacion mejor jugador liga ACB", driver.getTitle(), "El titulo no es correcto");
        System.out.println(driver.getTitle());
        driver.close();
        driver.quit();
    }

    @Test
    void botonIndexTest() {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/usr/bin/phantomjs");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{"--web-security=no", "--ignore-ssl-errors=yes"});
        driver = new PhantomJSDriver(caps);
        driver.navigate().to("http://localhost:8080/Baloncesto/");
        //1.- Localizo el boton "poner votos a cero" y es pulsado
        WebElement botonVotosCero = driver.findElement(By.name("B3"));
        botonVotosCero.click();
        System.out.println("Votos reseteatos");

        //2.- Localizo el boton "ver votos" y es pulsado
        WebElement botonVerVotos = driver.findElement(By.name("B4"));
        botonVerVotos.click();
        System.out.println("Boton ver todos clickeado");

        //3.- Localiza la nueva página
        if(driver.findElement(registerPageLocator).isDisplayed()){
            List<WebElement> listaFilas = driver.findElements(By.className("filas"));
            for (WebElement webElement: listaFilas){
                System.out.println(webElement.getText());
            }
        }

        driver.close();
        driver.quit();
    }
}
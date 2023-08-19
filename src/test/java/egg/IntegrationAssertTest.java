package egg;


import org.apache.hc.core5.util.Asserts;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

class IntegrationAssertTest {

    private static WebDriver miDriver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.firefoxdriver().setup(); // Configurar el controlador de Chrome
    }

    @BeforeEach
    void lalala(){
        miDriver = new FirefoxDriver();
    }

    @AfterEach
    void lololo(){
        miDriver.close();
    }                           

    @Test
    void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    void ejercicioCssSelector1() {
        miDriver.get("https://www.google.com");
        // TAG + ID
        WebElement buscador = miDriver.findElement(By.cssSelector("textarea#APjFqb"));

        Assertions.assertNotEquals(null, buscador);
    }

/*
 * Ejercicio 1: Verificar el título de la página de Wikipedia y el logo
En este ejercicio combinarás la localización de elementos mediante ID, 
la utilización de assertions para verificar el título de la página y un 
Implicit Wait para asegurar que la página se haya cargado correctamente.
 */
    @Test 
    public void ejercicio1() {
        //  Seteamos la url destino
        String myUrl = "https://www.wikipedia.org";
        miDriver.get(myUrl);

        // Realizamos espera implicita de 1.5 segundos
        miDriver.manage().timeouts().implicitlyWait(1500, TimeUnit.MILLISECONDS); 

        /*  TAG + CLASSNAME */
        // CssSelector para el WebElement del titulo
        String myCssSelectorTitle = "span.central-textlogo__image"; 

        /*  TAG + CLASSNAME + ATTRIBUTE */
        // CssSelector para el WebElement de la imagen 
        String myCssSelectorImg = "img.central-featured-logo[alt='Wikipedia']"; 
        
        // La busqueda de elementos web
        WebElement elWikiTitulo = miDriver.findElement(By.cssSelector(myCssSelectorTitle));
        WebElement laWikiImagen = miDriver.findElement(By.cssSelector(myCssSelectorImg));

        //  Validación por medio de los Assertions
        assertTrue(elWikiTitulo.isDisplayed(), "El titulo no se muestra.");
        assertTrue(laWikiImagen.isDisplayed(), "La imagen no se muestra.");
    }

/*
 * Ejercicio 2: Comprobar la existencia de un enlace y realizar una búsqueda en Google
Para este ejercicio utilizarás un Explicit Wait para esperar a 
que el elemento link esté presente, la ejecución de una acción 
(enviar una búsqueda) y la verificación del resultado utilizando 
una aserción.
 */
    @Test 
    public void ejercicio2() {
        //  Seteamos la url destino
        String myUrl = "https://www.google.com";
        miDriver.get(myUrl);

        // Realizamos espera explicita hasta que aparezca el textArea del buscador
        String idTextAreaSearch = "APjFqb";
        WebDriverWait myWaitReCheto = new WebDriverWait(miDriver, 15);
        myWaitReCheto.until(ExpectedConditions.visibilityOfElementLocated(By.id(idTextAreaSearch)));
        WebElement laBarritaDeBuscador = miDriver.findElement(By.id(idTextAreaSearch));
        laBarritaDeBuscador.sendKeys("eeeaaaooo");

        String miListaOrdenadaCssSelector = "li.sbct[role='presentation']";
        // Realizamos espera explicita hasta que aparezca el textArea del buscador
        myWaitReCheto.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(miListaOrdenadaCssSelector)));
        List<WebElement> miListaDeResultados = miDriver.findElements(By.cssSelector(miListaOrdenadaCssSelector));
        miListaDeResultados.get(0).click();

        //  Validación por medio de los Assertions
        String resultadoBusquedaCssSelector = "h3.MBeuO";
        myWaitReCheto.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(resultadoBusquedaCssSelector)));
        List<WebElement> listaDeResultados = miDriver.findElements(By.cssSelector(resultadoBusquedaCssSelector));

        assertTrue(listaDeResultados.size() > 0, "Se encontró una lista vacía.");
        assertFalse(listaDeResultados.size() == 0, "Se encontró una lista vacía.");
    }

    /*
     * Ejercicio 3: Verificar el cambio de idioma en la página de Wikipedia
En este ejercicio deberás emplear un implicit wait, una acción para hacer 
click y un assertion de igualdad para comparar un tipo de texto que ha sido 
traducido al español. Recomendamos probar con el título de la página.
     */
    @Test 
    public void ejercicio3() {
        //  Seteamos la url destino
        String myUrl = "https://www.wikipedia.org";
        miDriver.get(myUrl);

        // Realizamos espera implicita de 2.5 segundos
        miDriver.manage().timeouts().implicitlyWait(2500, TimeUnit.MILLISECONDS); 

        miDriver.findElement(By.id("js-link-box-es")).click();
        // Realizamos espera implicita de 1.5 segundos
        miDriver.manage().timeouts().implicitlyWait(1500, TimeUnit.MILLISECONDS); 
        String miBienvenidaEnEspaniol = miDriver.findElement(By.cssSelector("span.mw-headline[data-mw-thread-id='h-Bienvenidos_a_Wikipedia,']")).getText();

        miDriver.navigate().back();
    
        // Realizamos espera implicita de 2.5 segundos
        miDriver.manage().timeouts().implicitlyWait(2500, TimeUnit.MILLISECONDS); 

        miDriver.findElement(By.id("js-link-box-en")).click();
        // Realizamos espera implicita de 1.5 segundos
        miDriver.manage().timeouts().implicitlyWait(1500, TimeUnit.MILLISECONDS); 
        String miBienvenidaEnIngles = miDriver.findElement(By.id("Welcome_to_Wikipedia")).getText();


        //  Validación por medio de los Assertions
        assertEquals("Welcome to Wikipedia", miBienvenidaEnIngles, "No coinciden los titulos en inglés.");
        assertEquals("Bienvenidos a Wikipedia,", miBienvenidaEnEspaniol, "No coinciden los titulos en Español.");
    }

/*
 * Ejercicio 4: Verificar la función de búsqueda en YouTube
Aquí deberás usar un explicit wait que espere a que el resutlado 
de una búsqueda se haga presente y un assert que indique que sí 
hay elementos presentes luego de la búsqueda. No olvides la acción 
de .sendkeys.
 */
    @Test 
    public void ejercicio4() {
        //  Seteamos la url destino
        String myUrl = "https://www.wikipedia.org";
        miDriver.get(myUrl);

        // Realizamos espera implicita de 2.5 segundos
        miDriver.manage().timeouts().implicitlyWait(2500, TimeUnit.MILLISECONDS); 

        miDriver.findElement(By.id("js-link-box-es")).click();
        // Realizamos espera implicita de 1.5 segundos
        miDriver.manage().timeouts().implicitlyWait(1500, TimeUnit.MILLISECONDS); 
        String miBienvenidaEnEspaniol = miDriver.findElement(By.cssSelector("span.mw-headline[data-mw-thread-id='h-Bienvenidos_a_Wikipedia,']")).getText();

        miDriver.navigate().back();
    
        // Realizamos espera implicita de 2.5 segundos
        miDriver.manage().timeouts().implicitlyWait(2500, TimeUnit.MILLISECONDS); 

        miDriver.findElement(By.id("js-link-box-en")).click();
        // Realizamos espera implicita de 1.5 segundos
        miDriver.manage().timeouts().implicitlyWait(1500, TimeUnit.MILLISECONDS); 
        String miBienvenidaEnIngles = miDriver.findElement(By.id("Welcome_to_Wikipedia")).getText();


        //  Validación por medio de los Assertions
        assertEquals("Welcome to Wikipedia", miBienvenidaEnIngles, "No coinciden los titulos en inglés.");
        assertEquals("Bienvenidos a Wikipedia,", miBienvenidaEnEspaniol, "No coinciden los titulos en Español.");
    }

}


                     


    // @Test
    // void ejercicioCssSelector2(){
    //     miDriver.get("https://github.com/");
    //     // TAG + CLASSNAME
    //     WebElement elBotonMagicoDelGit = miDriver.findElement(By.cssSelector("button.HeaderMenu-link"));
    //     elBotonMagicoDelGit.click();

    //     WebElement enlaceReLoco = miDriver.findElement(By.cssSelector("a.HeaderMenu-dropdown-link"));
    //     enlaceReLoco.click();

    //     Assertions.assertNotEquals(null, elBotonMagicoDelGit);
    //     Assertions.assertNotEquals(null, enlaceReLoco);
    // }
    
    // @Test
    // void ejercicioCssSelector3(){
    //     miDriver.get("https://twitter.com/");
    //     // TAG + ATRIBUTE
    //     WebElement elBotonDeSinUP = miDriver.findElement(By.cssSelector("a[data-testid=signupButton]"));
    //     elBotonDeSinUP.click();
    //     Assertions.assertNotEquals(null, elBotonDeSinUP);
    // }
    
    // @Test
    // void ejercicioCssSelector4(){
    //     miDriver.get("https://www.linkedin.com/");
    //     // TAG + CLASSNAME + ATRIBUTE
    //     WebElement elBotonDelAlzheimer = miDriver.findElement(By.cssSelector(
    //         "a.sign-in-form__forgot-password--full-width[data-id=sign-in-form__forgot-password]"
    //         ));
    //     elBotonDelAlzheimer.click();

    //     Assertions.assertNotEquals(null, elBotonDelAlzheimer);
    // }

    // @Test
    // void ejercicioCssSelector5(){
    //     miDriver.get("https://medium.com/");

    //     // XPATH
    //     String xpathDelArticulo = "//*[@id='root']/div/div[4]/div[1]/div/div/div/div[2]/div/div[1]/div/div/div[2]/div[2]/a";
    //     WebElement elArticuloConRutaFea = miDriver.findElement(By.xpath(xpathDelArticulo));
    //     elArticuloConRutaFea.click();

    //     Assertions.assertNotEquals(null, elArticuloConRutaFea);
    // }
    
    // @Test
    // // 6. Etiqueta + Clase: Encuentra y haz clic en el enlace "Contact" en el pie de página del sitio Stack Overflow.
    // void ejercicioCssSelector6(){
    //     miDriver.get("https://es.stackoverflow.com/");
    //     // TAG + CLASSNAME
    //     List<WebElement> anchorFeoDeS = miDriver.findElements(By.cssSelector("a.js-gps-track"));
    //     WebElement contactoDelStackOverflow = anchorFeoDeS.get(46);
    //     contactoDelStackOverflow.click();
    //     Assertions.assertNotEquals(null, contactoDelStackOverflow);
    // }
    
    // @Test
    // // 7. Etiqueta + ID: Encuentra y muestra la descripción del primer producto en la página de inicio de Amazon.
    // void ejercicioCssSelector7(){
    //     miDriver.get("https://www.amazon.com/");
    //     // TAG + ID
    //     // List<WebElement> carruselDeAmazon = miDriver.findElements(By.cssSelector("div#685bf823-01f6-4791-8e4f-27661a121844"));
    //     // WebElement contactoDelStackOverflow = miDriver.findElements(By.cssSelector("div#685bf823-01f6-4791-8e4f-27661a121844")).get(1);
    //     WebElement elemento = miDriver.findElement(By.cssSelector("img.product-image[alt='SAMSUNG 980 PRO SSD with Heatsink 2TB PCIe Gen 4 NVMe M.2 Internal Solid State Drive + 2mo Adobe CC Photography, Heat...']"));
        
    //     String descripcion = elemento.getAttribute("alt");
    //     Assertions.assertEquals("SAMSUNG 980 PRO SSD with Heatsink 2TB PCIe Gen 4 NVMe M.2 Internal Solid State Drive + 2mo Adobe CC Photography, Heat...", descripcion);
    // }

    // @Test
    // void poneleMecha(){         
    //     miDriver.get("https://www.crunchyroll.com/es"); 

    //     miDriver.manage().timeouts().implicitlyWait(12000, TimeUnit.MILLISECONDS); 

    //     WebDriverWait espera = new WebDriverWait(miDriver, 15);
    //     espera.notify();

    //     Wait myFluentWait = new FluentWait(miDriver)
    //     .withTimeout(30, TimeUnit.SECONDS).
    //     pollingEvery(4, TimeUnit.SECONDS);

    //     // Wait popUpHorrible = new FluentWait(miDriver).
    //     // withTimeout(40, TimeUnit.SECONDS).
    //     // pollingEvery(5, TimeUnit.SECONDS).
    //     // ignoring(NoSuchElementException.class, TimeoutException.class);

    //     // // TAG + CLASSNAME + ATRIBUTE
    //     // String miCssSelectorPOP = "//*[@id=\'content\']/div/div[1]/div[1]/div[3]/ul/li[1]/div/div/svg";

    //     // popUpHorrible.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(miCssSelectorPOP)));
    //     // WebElement popuRepopo = miDriver.findElement(By.xpath(miCssSelectorPOP));
    //     // popuRepopo.click();
 
    //     // TAG + CLASSNAME + ATRIBUTE
    //     String miCssSelector = "button.carousel-tabs__tab--2llK3[data-t='carousel-tab']";

    //     // myFluentWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(miCssSelector)));
    //     List<WebElement> lsitaDeBotonesChingones = miDriver.findElements(By.cssSelector(miCssSelector));

    //     lsitaDeBotonesChingones.get(4).click();

    //     miDriver.manage().timeouts().implicitlyWait(1500, TimeUnit.MILLISECONDS); 

    //     lsitaDeBotonesChingones.get(1).click();

    // }


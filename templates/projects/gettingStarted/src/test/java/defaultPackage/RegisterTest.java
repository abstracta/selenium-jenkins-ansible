package defaultPackage;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.*;

import pages.*;

@RunWith(Parameterized.class)
public class RegisterTest {

    private WebDriver driver;
    private HomePage homePage;
    //data driven testing params
    private String username;
    private String lastname;
    private String email;
    private String telephone;
    private String address;
    private String city;
    private String postcode;
    private String password;

    public RegisterTest(String username, String lastname, String email, 
        String telephone, String address, String city, 
        String postcode, String password) {
        this.username = username;
        this.lastname = lastname;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
        this.city = city;
        this.postcode = postcode;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Collection inputData() {
        return Arrays.asList(new Object[][] {
            { "arsenal", "arsenal", "ars@ars.ars", "123", "arsenal", "arsenal", "123", "arsenal123" },
            { "chelsea", "chelsea", "che@che.che", "123", "chelsea", "chelsea", "123", "arsenal123" },
            { "tottenham", "tottenham", "tot@tot.tot", "123", "tottenham", "tottenham", "123", "tottenham123" },
            { "united", "united", "uni@uni.uni", "123", "united", "united", "123", "united123" },
            { "city", "city", "cit@cit.cit", "123", "city", "city", "123", "city123" }
        });
    }

    @Before
    public void setUp(){
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");
        System.setProperty("webdriver.gecko.driver","/home/juanpa/drivers/geckodriver");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        driver = new FirefoxDriver(firefoxOptions);
    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Simulo ingreso a la homepage
        driver.get("http://opencart.abstracta.us");
        homePage = new HomePage(driver);
    }

    @Test
    public void registerUserTest() throws InterruptedException {
        homePage.clickRegister();
        //aca cambio a la pagina de registro
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.setUsername(this.username);
        registerPage.setLastname(this.lastname);
        registerPage.setEmail(this.email);
        registerPage.setTelephone(this.telephone);
        registerPage.setAddress(this.address);
        registerPage.setCity(this.city);
        registerPage.setPostCode(this.postcode);
        registerPage.setZoneOption();
        registerPage.setPassword(this.password);
        registerPage.agreeToTerms();
        registerPage.confirm();

        String expectedTitle = "Your Account Has Been Created!";
        String actualTitle = driver.getTitle();

        assertEquals(expectedTitle, actualTitle);
    }
}
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
public class RemoveFromWishListTest {
    private WebDriver driver;
    private HomePage homePage;

    private String email;
    private String password;

    public RemoveFromWishListTest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Collection inputData() {
        return Arrays.asList(new Object[][] {
            { "ars@ars.ars", "arsenal123" },
            { "che@che.che", "arsenal123" },
            { "tot@tot.tot", "tottenham123" },
            { "uni@uni.uni", "united123" },
            { "cit@cit.cit", "city123" }
        });
    }

    @Before
    public void setUp() {
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");
        System.setProperty("webdriver.gecko.driver", "/home/juanpa/drivers/geckodriver");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://opencart.abstracta.us");
        homePage = new HomePage(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void removeFromWishListTest() {
        homePage.clickLogin();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(this.email);
        loginPage.setPassword(this.password);
        loginPage.clickLogin();
        //ya autenticado voy a mi wishlist

        homePage.goToWishList();
        homePage.removeElementFromWishList();

        assertEquals(true, homePage.removedConfirmed());
    }
}
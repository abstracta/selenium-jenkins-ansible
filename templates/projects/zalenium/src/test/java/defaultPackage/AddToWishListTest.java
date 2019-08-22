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

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import pages.*;

@RunWith(Parameterized.class)
public class AddToWishListTest {
    private WebDriver driver;
    private HomePage homePage;

    private String productName;
    private String email;
    private String password;

    public AddToWishListTest(String productname, String email, String password) {
        this.productName = productname;
        this.email = email;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Collection inputData() {
        return Arrays.asList(new Object[][] {
            { "iphone", "ars@ars.ars", "arsenal123" },
            { "samsung", "che@che.che", "arsenal123" },
            { "imac", "tot@tot.tot", "tottenham123" },
            { "canon", "uni@uni.uni", "united123" },
            { "nikon", "cit@cit.cit", "city123" }
        });
    }

    @Before
    public void setUp() throws Exception{
        DesiredCapabilities capabilities;
        capabilities = DesiredCapabilities.firefox();

        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://opencart.abstracta.us");
        homePage = new HomePage(driver);
    }
    
    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void addToWishListTest() {
        homePage.clickLogin();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(this.email);
        loginPage.setPassword(this.password);
        loginPage.clickLogin();
        //ya autenticado busco el producto
        homePage.search(this.productName);
        homePage.selectFirstSearchResult();
        //ya en el producto lo agrego a mi wishlist
        homePage.addToWishlist();

        assertEquals(true, homePage.addedConfirmed());
        //ahora ya autenticado, busco el producto y lo agrego a mi 
    }
}
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
public class SearchTest {
    private WebDriver driver;
    private HomePage homePage;

    private String productName;

    public SearchTest(String productname) {
        this.productName = productname;
    }

    @Parameterized.Parameters
    public static Collection inputData() {
        return Arrays.asList(new Object[][] {
            { "iphone" },
            { "samsung" },
            { "imac" },
            { "canon" },
            { "nikon" }
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
    public void searchTest() {
        homePage.search(this.productName);
        homePage.selectFirstSearchResult();

        String actualTitle = driver.getTitle();
        assertEquals(true, actualTitle.toLowerCase().contains(this.productName));
    }
}
//buscar el producto
//clickear en la primer opcion
//assertear titulo.tolower.contains(productName)
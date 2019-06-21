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
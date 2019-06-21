package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;
    By emailField = By.id("input-email");
    By passwordField = By.id("input-password");
    By loginButton = By.xpath("//input[@value='Login']");

    public LoginPage(WebDriver webDriver) {
        driver = webDriver;
    }

    public void setEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void setPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }
}
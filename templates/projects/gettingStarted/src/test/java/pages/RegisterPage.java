package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class RegisterPage {

    WebDriver driver;
    By usernameInput = By.id("input-firstname");
    By lastnameInput = By.id("input-lastname");
    By emailInput = By.id("input-email");
    By telephoneInput = By.id("input-telephone");
    By addressInput = By.id("input-address-1");
    By cityInput = By.id("input-city");
    By postcodeInput = By.id("input-postcode");
    By zoneInput = By.id("input-zone");
    By passwordInput = By.id("input-password");//password
    By confirmInput = By.id("input-confirm");//password de nuevo
    By agreeButton = By.name("agree");//hay que clickear esto para el agree de terms and conditions
    By registerButton = By.xpath("//input[@value='Continue']");
    //esto es el boton de create user digamos
    //el assert final de la test deberia ser que el titulo de la pagina sea
    //Your Account Has Been Created!

    public RegisterPage(WebDriver webDriver){
        driver = webDriver;
    }

    public void setUsername(String username){
        driver.findElement(usernameInput).sendKeys(username);
    }

    public void setLastname(String lastname){
        driver.findElement(lastnameInput).sendKeys(lastname);
    }

    public void setEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void setTelephone(String telephone){
        driver.findElement(telephoneInput).sendKeys(telephone);
    }

    public void setAddress(String address){
        driver.findElement(addressInput).sendKeys(address);
    }

    public void setCity(String city){
        driver.findElement(cityInput).sendKeys(city);
    }

    public void setPostCode(String postCode){
        driver.findElement(postcodeInput).sendKeys(postCode);
    }

    public void setZoneOption(){
        Select dropDownZone = new Select(driver.findElement(zoneInput));
        dropDownZone.selectByVisibleText("Aberdeen");
    }

    public void setPassword(String password){
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(confirmInput).sendKeys(password);
    }

    public void agreeToTerms(){
        driver.findElement(agreeButton).click();
    }

    public void confirm(){
        driver.findElement(registerButton).click();
    }
}
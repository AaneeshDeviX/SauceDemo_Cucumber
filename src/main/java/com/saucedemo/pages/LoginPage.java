package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "user-name")       private WebElement usernameField;
    @FindBy(id = "password")        private WebElement passwordField;
    @FindBy(id = "login-button")    private WebElement loginButton;
    @FindBy(css = "[data-test='error']") private WebElement errorMessage;
    @FindBy(css = ".login_logo")    private WebElement logo;
    @FindBy(id = "login_credentials") private WebElement credentialsSection;

    public LoginPage(WebDriver driver) { super(driver); }

    public void enterUsername(String username) { type(usernameField, username); }
    public void enterPassword(String password) { type(passwordField, password); }
    public void clickLogin() { click(loginButton); }

    public void loginAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public String getErrorMessage()        { return getText(errorMessage); }
    public boolean isErrorDisplayed()      { return isDisplayed(errorMessage); }
    public boolean isLogoDisplayed()       { return isDisplayed(logo); }
    public String getLogoText()            { return getText(logo); }
    public boolean isLoginButtonDisplayed(){ return isDisplayed(loginButton); }
    public boolean isOnLoginPage()         { return isDisplayed(loginButton); }
    public String getPasswordFieldType()   { return passwordField.getAttribute("type"); }
    public boolean areCredentialsVisible() { return isDisplayed(credentialsSection); }
}

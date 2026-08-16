package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutStepOnePage extends BasePage {

    @FindBy(id = "first-name")                  private WebElement firstNameField;
    @FindBy(id = "last-name")                   private WebElement lastNameField;
    @FindBy(id = "postal-code")                 private WebElement postalCodeField;
    @FindBy(css = "[data-test='continue']")     private WebElement continueBtn;
    @FindBy(css = "[data-test='cancel']")       private WebElement cancelBtn;
    @FindBy(css = "[data-test='error']")        private WebElement errorMessage;
    @FindBy(css = ".title")                     private WebElement pageTitle;

    public CheckoutStepOnePage(WebDriver driver) { super(driver); }

    public boolean isOnStepOne() {
        try { return getText(pageTitle).contains("Your Information"); }
        catch (Exception e) { return false; }
    }

    public void enterFirstName(String name)   { type(firstNameField, name); }
    public void enterLastName(String name)    { type(lastNameField, name); }
    public void enterPostalCode(String code)  { type(postalCodeField, code); }

    public void fillInfo(String first, String last, String zip) {
        enterFirstName(first);
        enterLastName(last);
        enterPostalCode(zip);
    }

    public void clickContinue() {
        scrollIntoView(continueBtn);
        continueBtn.click();
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
    }

    public void clickCancel() {
        driver.get("https://www.saucedemo.com/cart.html");
    }

    public String getErrorMessage()   { return getText(errorMessage); }
    public boolean isErrorDisplayed() { return isDisplayed(errorMessage); }
    public String getFirstNameValue() { return firstNameField.getAttribute("value"); }
}

package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutCompletePage extends BasePage {

    @FindBy(css = ".complete-header")                private WebElement completeHeader;
    @FindBy(css = ".complete-text")                  private WebElement completeText;
    @FindBy(css = "[data-test='back-to-products']")  private WebElement backHomeBtn;
    @FindBy(css = ".title")                          private WebElement pageTitle;

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".complete-header")));
    }

    public boolean isOnCompletePage() {
        try { return getText(pageTitle).contains("Complete"); }
        catch (Exception e) { return false; }
    }

    public String getCompleteHeader()  { return getText(completeHeader); }
    public String getCompleteText()    { return getText(completeText); }

    public void clickBackHome() {
        driver.get("https://www.saucedemo.com/inventory.html");
    }
}

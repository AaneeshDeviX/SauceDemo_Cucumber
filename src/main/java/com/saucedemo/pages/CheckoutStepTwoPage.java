package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CheckoutStepTwoPage extends BasePage {

    @FindBy(css = ".cart_item")                          private List<WebElement> cartItems;
    @FindBy(css = "[data-test='subtotal-label']")        private WebElement subtotalLabel;
    @FindBy(css = "[data-test='tax-label']")             private WebElement taxLabel;
    @FindBy(css = "[data-test='total-label']")           private WebElement totalLabel;
    @FindBy(css = "[data-test='payment-info-value']")    private WebElement paymentInfo;
    @FindBy(css = "[data-test='shipping-info-value']")   private WebElement shippingInfo;
    @FindBy(css = "[data-test='finish']")                private WebElement finishBtn;
    @FindBy(css = "[data-test='cancel']")                private WebElement cancelBtn;
    @FindBy(css = ".title")                              private WebElement pageTitle;

    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='finish']")));
    }

    public boolean isOnStepTwo() {
        try { return getText(pageTitle).contains("Overview"); }
        catch (Exception e) { return false; }
    }

    public int getItemCount()         { return cartItems.size(); }
    public String getSubtotal()       { return getText(subtotalLabel); }
    public String getTax()            { return getText(taxLabel); }
    public String getTotal()          { return getText(totalLabel); }
    public String getPaymentInfo()    { return getText(paymentInfo); }
    public String getShippingInfo()   { return getText(shippingInfo); }

    public double getSubtotalAsDouble() { return Double.parseDouble(getSubtotal().replaceAll("[^0-9.]", "")); }
    public double getTaxAsDouble()      { return Double.parseDouble(getTax().replaceAll("[^0-9.]", "")); }
    public double getTotalAsDouble()    { return Double.parseDouble(getTotal().replaceAll("[^0-9.]", "")); }

    public void clickFinish() {
        scrollIntoView(finishBtn);
        finishBtn.click();
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
    }

    public void clickCancel() {
        driver.get("https://www.saucedemo.com/inventory.html");
    }
}

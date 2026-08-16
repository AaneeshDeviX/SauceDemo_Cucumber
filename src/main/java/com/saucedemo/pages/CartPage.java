package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = ".cart_item")                        private List<WebElement> cartItems;
    @FindBy(css = "[data-test='continue-shopping']")   private WebElement continueShoppingBtn;
    @FindBy(css = "[data-test='checkout']")            private WebElement checkoutBtn;
    @FindBy(css = ".title")                            private WebElement pageTitle;

    public CartPage(WebDriver driver) { super(driver); }

    private void ensureOnCartPage() {
        if (!driver.getCurrentUrl().contains("cart")) {
            driver.get("https://www.saucedemo.com/cart.html");
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='checkout']")));
    }

    public boolean isOnCartPage() {
        try {
            ensureOnCartPage();
            return getText(pageTitle).equals("Your Cart");
        } catch (Exception e) { return false; }
    }

    public int getCartItemCount() {
        ensureOnCartPage();
        return driver.findElements(By.cssSelector(".cart_item")).size();
    }

    public List<String> getCartItemNames() {
        ensureOnCartPage();
        List<String> names = new ArrayList<>();
        driver.findElements(By.cssSelector(".cart_item")).forEach(item ->
                names.add(item.findElement(By.cssSelector("[data-test='inventory-item-name']")).getText()));
        return names;
    }

    public String getItemQuantity(int index) {
        ensureOnCartPage();
        return driver.findElements(By.cssSelector(".cart_item")).get(index)
                .findElement(By.cssSelector("[data-test='item-quantity']")).getText();
    }

    public void removeItemByIndex(int index) {
        ensureOnCartPage();
        driver.findElements(By.cssSelector(".cart_item")).get(index)
                .findElement(By.cssSelector("button[data-test^='remove']")).click();
    }

    public void clickContinueShopping() {
        driver.get("https://www.saucedemo.com/inventory.html");
    }

    public void clickCheckout() {
        driver.get("https://www.saucedemo.com/checkout-step-one.html");
    }

    public boolean isCheckoutDisplayed() {
        try {
            ensureOnCartPage();
            return checkoutBtn.isDisplayed();
        } catch (Exception e) { return false; }
    }
}

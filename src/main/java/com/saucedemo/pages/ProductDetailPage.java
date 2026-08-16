package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductDetailPage extends BasePage {

    @FindBy(css = "[data-test='inventory-item-name']")  private WebElement productName;
    @FindBy(css = "[data-test='inventory-item-desc']")  private WebElement productDesc;
    @FindBy(css = "[data-test='inventory-item-price']") private WebElement productPrice;
    @FindBy(css = "img.inventory_details_img")          private WebElement productImage;
    @FindBy(css = "button[data-test^='add-to-cart']")   private WebElement addToCartBtn;
    @FindBy(css = "button[data-test^='remove']")        private WebElement removeBtn;
    @FindBy(css = "[data-test='back-to-products']")     private WebElement backBtn;

    public ProductDetailPage(WebDriver driver) { super(driver); }

    public String getProductName()         { return getText(productName); }
    public String getProductPrice()        { return getText(productPrice); }
    public String getProductDescription()  { return getText(productDesc); }
    public boolean isImageDisplayed()      { return isDisplayed(productImage); }
    public boolean isAddToCartDisplayed()  { return isDisplayed(addToCartBtn); }
    public boolean isRemoveDisplayed()     { return isDisplayed(removeBtn); }
    public void addToCart()                { click(addToCartBtn); }
    public void removeFromCart()           { click(removeBtn); }
    public void goBackToProducts() {
        driver.get("https://www.saucedemo.com/inventory.html");
    }
}

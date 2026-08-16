package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage extends BasePage {

    @FindBy(css = ".title")                    private WebElement pageTitle;
    @FindBy(css = ".inventory_item")           private List<WebElement> inventoryItems;
    @FindBy(css = ".product_sort_container")   private WebElement sortDropdown;
    @FindBy(css = ".shopping_cart_link")        private WebElement cartLink;
    @FindBy(css = ".shopping_cart_badge")       private WebElement cartBadge;
    @FindBy(css = "[data-test='open-menu']")   private WebElement burgerMenuBtn;
    @FindBy(css = ".inventory_item_name")      private List<WebElement> productNames;
    @FindBy(css = ".inventory_item_price")     private List<WebElement> productPrices;
    @FindBy(css = ".inventory_item_img img")   private List<WebElement> productImages;
    @FindBy(css = ".social a")                 private List<WebElement> socialLinks;
    @FindBy(css = ".footer_copy")              private WebElement footerText;

    public InventoryPage(WebDriver driver) { super(driver); }

    public boolean isOnInventoryPage() {
        try { return getText(pageTitle).equals("Products"); }
        catch (Exception e) { return false; }
    }

    public String getPageTitle() { return getText(pageTitle); }
    public int getProductCount() { return inventoryItems.size(); }

    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();
        productNames.forEach(el -> names.add(el.getText()));
        return names;
    }

    public List<Double> getProductPricesAsDouble() {
        List<Double> prices = new ArrayList<>();
        productPrices.forEach(el -> prices.add(Double.parseDouble(el.getText().replace("$", ""))));
        return prices;
    }

    public List<String> getProductImageSources() {
        List<String> srcs = new ArrayList<>();
        productImages.forEach(el -> srcs.add(el.getAttribute("src")));
        return srcs;
    }

    public boolean areAllImagesUnique() {
        List<String> srcs = getProductImageSources();
        return srcs.stream().distinct().count() == srcs.size();
    }

    public void sortBy(String value) { new Select(sortDropdown).selectByValue(value); }
    public void sortByNameAZ()       { sortBy("az"); }
    public void sortByNameZA()       { sortBy("za"); }
    public void sortByPriceLowHigh() { sortBy("lohi"); }
    public void sortByPriceHighLow() { sortBy("hilo"); }

    public void addProductToCartByIndex(int index) {
        WebElement btn = inventoryItems.get(index).findElement(By.cssSelector("button[id^='add-to-cart']"));
        scrollIntoView(btn);
        btn.click();
    }

    public void removeProductByName(String name) {
        String id = "remove-" + name.toLowerCase().replace(" ", "-").replace("(", "").replace(")", "");
        WebElement btn = driver.findElement(By.id(id));
        scrollIntoView(btn);
        btn.click();
    }

    public int getCartBadgeCount() {
        try { return Integer.parseInt(cartBadge.getText()); }
        catch (Exception e) { return 0; }
    }

    public boolean isCartBadgeDisplayed() { return isDisplayed(cartBadge); }

    public void clickCart() {
        driver.get("https://www.saucedemo.com/cart.html");
    }

    public void clickProductByIndex(int i) { click(productNames.get(i)); }

    public void clickProductByName(String name) {
        productNames.stream().filter(el -> el.getText().equals(name)).findFirst().ifPresent(this::click);
    }

    public void openBurgerMenu() { click(burgerMenuBtn); }
    public int getSocialLinkCount() { return socialLinks.size(); }
    public String getFooterText() { return getText(footerText); }
}

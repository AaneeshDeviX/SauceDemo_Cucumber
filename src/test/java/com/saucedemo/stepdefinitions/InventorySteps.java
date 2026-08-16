package com.saucedemo.stepdefinitions;

import com.saucedemo.config.ConfigReader;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.SidebarMenu;
import com.saucedemo.utils.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.List;

public class InventorySteps {

    private InventoryPage inventoryPage;

    @Given("I am logged in as standard user")
    public void iAmLoggedInAsStandardUser() {
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.loginAs(ConfigReader.get("standard.username"), ConfigReader.get("standard.password"));
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Should be on inventory after login");
    }

    @Then("I should be redirected to the inventory page")
    public void iShouldBeOnInventoryPage() {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Should be on inventory page");
    }

    @Then("the page title should be {string}")
    public void pageTitleShouldBe(String expected) {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        Assert.assertEquals(inventoryPage.getPageTitle(), expected);
    }

    @Then("I should see {int} products on the inventory page")
    public void iShouldSeeProducts(int count) {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        Assert.assertEquals(inventoryPage.getProductCount(), count);
    }

    @Then("all product names should be visible")
    public void allProductNamesShouldBeVisible() {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        Assert.assertEquals(inventoryPage.getProductNames().size(), 6);
    }

    @Then("product {string} should be listed")
    public void productShouldBeListed(String name) {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        Assert.assertTrue(inventoryPage.getProductNames().contains(name), name + " should be listed");
    }

    @When("I sort products by {string}")
    public void iSortProductsBy(String sortValue) {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        inventoryPage.sortBy(sortValue);
    }

    @Then("the first product should be {string}")
    public void firstProductShouldBe(String expected) {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        Assert.assertEquals(inventoryPage.getProductNames().get(0), expected);
    }

    @Then("the first product should start with {string}")
    public void firstProductShouldStartWith(String prefix) {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        Assert.assertTrue(inventoryPage.getProductNames().get(0).startsWith(prefix));
    }

    @Then("products should be sorted by price in ascending order")
    public void productsSortedAscending() {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        List<Double> prices = inventoryPage.getProductPricesAsDouble();
        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(prices.get(i) <= prices.get(i + 1), "Prices should be ascending");
        }
    }

    @Then("products should be sorted by price in descending order")
    public void productsSortedDescending() {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        List<Double> prices = inventoryPage.getProductPricesAsDouble();
        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(prices.get(i) >= prices.get(i + 1), "Prices should be descending");
        }
    }

    @When("I add product at index {int} to cart")
    public void iAddProductAtIndex(int index) {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        inventoryPage.addProductToCartByIndex(index);
    }

    @When("I remove product {string} from inventory")
    public void iRemoveProductFromInventory(String name) {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        inventoryPage.removeProductByName(name);
    }

    @Then("the cart badge should show {int}")
    public void cartBadgeShouldShow(int count) {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), count);
    }

    @Then("the cart badge should not be displayed")
    public void cartBadgeShouldNotBeDisplayed() {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        Assert.assertFalse(inventoryPage.isCartBadgeDisplayed(), "Cart badge should be gone");
    }

    @When("I click on product {string}")
    public void iClickOnProduct(String name) {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        inventoryPage.clickProductByName(name);
    }

    @When("I click on product at index {int}")
    public void iClickOnProductAtIndex(int index) {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        inventoryPage.clickProductByIndex(index);
    }

    @Then("the footer should have {int} social links")
    public void footerShouldHaveSocialLinks(int count) {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        Assert.assertEquals(inventoryPage.getSocialLinkCount(), count);
    }

    @Then("all product images should be identical")
    public void allProductImagesShouldBeIdentical() {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        List<String> images = inventoryPage.getProductImageSources();
        boolean allSame = images.stream().allMatch(src -> src.equals(images.get(0)));
        Assert.assertTrue(allSame, "DEFECT: All images are identical for problem_user");
    }

    @When("I navigate to the cart page")
    public void iNavigateToCart() {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        inventoryPage.clickCart();
    }

    @When("I open the burger menu")
    public void iOpenBurgerMenu() {
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
        inventoryPage.openBurgerMenu();
    }

    @Given("I navigate directly to inventory page")
    public void iNavigateDirectlyToInventory() {
        DriverFactory.getDriver().get("https://www.saucedemo.com/inventory.html");
    }
}

package com.saucedemo.stepdefinitions;

import com.saucedemo.pages.ProductDetailPage;
import com.saucedemo.utils.DriverFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class ProductDetailSteps {

    private ProductDetailPage detailPage;

    private ProductDetailPage getPage() {
        if (detailPage == null) detailPage = new ProductDetailPage(DriverFactory.getDriver());
        return detailPage;
    }

    @Then("I should be on the product detail page")
    public void iShouldBeOnDetailPage() {
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("inventory-item"));
    }

    @Then("the product name should be {string}")
    public void productNameShouldBe(String expected) {
        Assert.assertEquals(getPage().getProductName(), expected);
    }

    @Then("the product price should be {string}")
    public void productPriceShouldBe(String expected) {
        Assert.assertEquals(getPage().getProductPrice(), expected);
    }

    @Then("the product image should be displayed")
    public void productImageShouldBeDisplayed() {
        Assert.assertTrue(getPage().isImageDisplayed());
    }

    @Then("the add to cart button should be displayed")
    public void addToCartButtonShouldBeDisplayed() {
        Assert.assertTrue(getPage().isAddToCartDisplayed());
    }

    @When("I click add to cart on detail page")
    public void iClickAddToCartOnDetail() {
        getPage().addToCart();
    }

    @When("I click remove on detail page")
    public void iClickRemoveOnDetail() {
        getPage().removeFromCart();
    }

    @Then("the remove button should be displayed on detail page")
    public void removeButtonShouldBeDisplayed() {
        Assert.assertTrue(getPage().isRemoveDisplayed());
    }

    @When("I click back to products")
    public void iClickBackToProducts() {
        getPage().goBackToProducts();
    }
}

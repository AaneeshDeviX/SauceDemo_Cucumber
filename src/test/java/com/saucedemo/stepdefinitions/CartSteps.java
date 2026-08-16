package com.saucedemo.stepdefinitions;

import com.saucedemo.pages.CartPage;
import com.saucedemo.utils.DriverFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class CartSteps {

    private CartPage getPage() {
        return new CartPage(DriverFactory.getDriver());
    }

    @Then("I should be on the cart page")
    public void iShouldBeOnCartPage() {
        Assert.assertTrue(getPage().isOnCartPage(), "Should be on cart page");
    }

    @Then("the cart should contain {int} items")
    public void cartShouldContainItems(int count) {
        Assert.assertEquals(getPage().getCartItemCount(), count);
    }

    @Then("item at index {int} should have quantity {string}")
    public void itemShouldHaveQuantity(int index, String qty) {
        Assert.assertEquals(getPage().getItemQuantity(index), qty);
    }

    @When("I remove item at index {int} from cart")
    public void iRemoveItemFromCart(int index) {
        getPage().removeItemByIndex(index);
    }

    @When("I click continue shopping")
    public void iClickContinueShopping() {
        getPage().clickContinueShopping();
    }

    @When("I click checkout")
    public void iClickCheckout() {
        getPage().clickCheckout();
    }

    @Then("the checkout button should be displayed")
    public void checkoutButtonShouldBeDisplayed() {
        Assert.assertTrue(getPage().isCheckoutDisplayed());
    }
}

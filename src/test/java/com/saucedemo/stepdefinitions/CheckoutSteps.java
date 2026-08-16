package com.saucedemo.stepdefinitions;

import com.saucedemo.pages.CheckoutCompletePage;
import com.saucedemo.pages.CheckoutStepOnePage;
import com.saucedemo.pages.CheckoutStepTwoPage;
import com.saucedemo.utils.DriverFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class CheckoutSteps {

    private CheckoutStepOnePage getStepOne() {
        return new CheckoutStepOnePage(DriverFactory.getDriver());
    }

    private CheckoutStepTwoPage getStepTwo() {
        return new CheckoutStepTwoPage(DriverFactory.getDriver());
    }

    private CheckoutCompletePage getComplete() {
        return new CheckoutCompletePage(DriverFactory.getDriver());
    }

    @Then("I should be on checkout step one")
    public void iShouldBeOnStepOne() {
        Assert.assertTrue(getStepOne().isOnStepOne(), "Should be on checkout step one");
    }

    @Then("I should be on checkout step two")
    public void iShouldBeOnStepTwo() {
        Assert.assertTrue(getStepTwo().isOnStepTwo(), "Should be on checkout step two");
    }

    @When("I enter first name {string} on checkout")
    public void iEnterFirstName(String name) {
        getStepOne().enterFirstName(name);
    }

    @When("I enter last name {string} on checkout")
    public void iEnterLastName(String name) {
        getStepOne().enterLastName(name);
    }

    @When("I fill checkout info {string} {string} {string}")
    public void iFillCheckoutInfo(String first, String last, String zip) {
        getStepOne().fillInfo(first, last, zip);
    }

    @When("I click continue on checkout")
    public void iClickContinue() {
        getStepOne().clickContinue();
    }

    @When("I click cancel on checkout step one")
    public void iClickCancelStepOne() {
        getStepOne().clickCancel();
    }

    @Then("I should see checkout error {string}")
    public void iShouldSeeCheckoutError(String expected) {
        CheckoutStepOnePage page = getStepOne();
        Assert.assertTrue(page.isErrorDisplayed(), "Error should be displayed");
        Assert.assertTrue(page.getErrorMessage().contains(expected),
                "Error should contain: " + expected);
    }

    @Then("the overview should show {int} item")
    public void overviewShouldShowItems(int count) {
        Assert.assertEquals(getStepTwo().getItemCount(), count);
    }

    @Then("the total should equal subtotal plus tax")
    public void totalShouldEqualSubtotalPlusTax() {
        CheckoutStepTwoPage page = getStepTwo();
        double subtotal = page.getSubtotalAsDouble();
        double tax = page.getTaxAsDouble();
        double total = page.getTotalAsDouble();
        Assert.assertEquals(total, subtotal + tax, 0.01, "Total should = subtotal + tax");
    }

    @Then("payment info should be displayed")
    public void paymentInfoDisplayed() {
        Assert.assertFalse(getStepTwo().getPaymentInfo().isEmpty());
    }

    @Then("shipping info should be displayed")
    public void shippingInfoDisplayed() {
        Assert.assertFalse(getStepTwo().getShippingInfo().isEmpty());
    }

    @When("I click finish on checkout")
    public void iClickFinish() {
        getStepTwo().clickFinish();
    }

    @Then("I should see {string}")
    public void iShouldSeeMessage(String expected) {
        Assert.assertEquals(getComplete().getCompleteHeader(), expected);
    }

    @Then("I should be on the checkout complete page")
    public void iShouldBeOnCompletePage() {
        Assert.assertTrue(getComplete().isOnCompletePage());
    }

    @When("I click back home")
    public void iClickBackHome() {
        getComplete().clickBackHome();
    }

    @Then("the first name field value should be verified for problem user")
    public void firstNameFieldValueForProblemUser() {
        String value = getStepOne().getFirstNameValue();
        if (value == null || value.isEmpty()) {
            Assert.fail("DEFECT USB_004: First Name field does not accept input for problem_user");
        }
    }
}

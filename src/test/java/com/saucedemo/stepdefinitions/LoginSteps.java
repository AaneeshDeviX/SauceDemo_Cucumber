package com.saucedemo.stepdefinitions;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.utils.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {

    private LoginPage loginPage;

    @Given("I am on the SauceDemo login page")
    public void iAmOnLoginPage() {
        loginPage = new LoginPage(DriverFactory.getDriver());
        Assert.assertTrue(loginPage.isLogoDisplayed(), "Logo should be visible on login page");
    }

    @When("I enter username {string} and password {string}")
    public void iEnterCredentials(String username, String password) {
        loginPage = new LoginPage(DriverFactory.getDriver());
        if (!username.isEmpty()) loginPage.enterUsername(username);
        if (!password.isEmpty()) loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void iClickLogin() {
        loginPage.clickLogin();
    }

    @Then("I should see error message containing {string}")
    public void iShouldSeeError(String expectedText) {
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error should be displayed");
        String actual = loginPage.getErrorMessage();
        Assert.assertTrue(actual.contains(expectedText),
                "Error should contain '" + expectedText + "' but was '" + actual + "'");
    }

    @Then("the password field type should be {string}")
    public void passwordFieldType(String expectedType) {
        loginPage = new LoginPage(DriverFactory.getDriver());
        Assert.assertEquals(loginPage.getPasswordFieldType(), expectedType);
    }

    @Then("credentials should not be displayed on the login page")
    public void credentialsShouldNotBeDisplayed() {
        loginPage = new LoginPage(DriverFactory.getDriver());
        // This is a KNOWN DEFECT - credentials ARE visible
        if (loginPage.areCredentialsVisible()) {
            Assert.fail("DEFECT: Credentials are displayed on the login page (security risk)");
        }
    }

    @Then("I should be on the login page")
    public void iShouldBeOnLoginPage() {
        loginPage = new LoginPage(DriverFactory.getDriver());
        Assert.assertTrue(loginPage.isOnLoginPage(), "Should be on login page");
    }

    @Then("I should see an error on login page")
    public void iShouldSeeErrorOnLoginPage() {
        loginPage = new LoginPage(DriverFactory.getDriver());
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error should be shown for unauthorized access");
    }
}

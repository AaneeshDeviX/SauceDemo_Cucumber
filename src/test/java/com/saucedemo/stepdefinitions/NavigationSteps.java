package com.saucedemo.stepdefinitions;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.SidebarMenu;
import com.saucedemo.utils.DriverFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class NavigationSteps {

    @Then("the sidebar menu should be displayed")
    public void sidebarMenuShouldBeDisplayed() {
        SidebarMenu menu = new SidebarMenu(DriverFactory.getDriver());
        Assert.assertTrue(menu.isMenuDisplayed(), "Sidebar menu should be visible");
    }

    @When("I click logout")
    public void iClickLogout() {
        SidebarMenu menu = new SidebarMenu(DriverFactory.getDriver());
        menu.clickLogout();
    }

    @When("I click reset app state")
    public void iClickResetAppState() {
        SidebarMenu menu = new SidebarMenu(DriverFactory.getDriver());
        menu.clickResetAppState();
    }

    @When("I close the sidebar menu")
    public void iCloseSidebarMenu() {
        SidebarMenu menu = new SidebarMenu(DriverFactory.getDriver());
        menu.closeMenu();
        // Small wait for menu animation to complete
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }
}

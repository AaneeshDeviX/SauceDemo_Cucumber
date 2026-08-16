package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SidebarMenu extends BasePage {

    @FindBy(id = "inventory_sidebar_link")     private WebElement allItemsLink;
    @FindBy(id = "about_sidebar_link")         private WebElement aboutLink;
    @FindBy(id = "logout_sidebar_link")        private WebElement logoutLink;
    @FindBy(id = "reset_sidebar_link")         private WebElement resetLink;
    @FindBy(css = "[data-test='close-menu']")  private WebElement closeBtn;

    public SidebarMenu(WebDriver driver) {
        super(driver);
        // Wait for sidebar links to become visible
        wait.until(ExpectedConditions.visibilityOf(allItemsLink));
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }

    public void clickAllItems()      { wait.until(ExpectedConditions.elementToBeClickable(allItemsLink)).click(); }
    public void clickAbout()         { wait.until(ExpectedConditions.elementToBeClickable(aboutLink)).click(); }
    public void clickLogout()        { wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click(); }
    public void clickResetAppState() { wait.until(ExpectedConditions.elementToBeClickable(resetLink)).click(); }
    public void closeMenu()          { wait.until(ExpectedConditions.elementToBeClickable(closeBtn)).click(); }
    public boolean isMenuDisplayed() {
        try { return allItemsLink.isDisplayed(); }
        catch (Exception e) { return false; }
    }
}

@inventory
Feature: Inventory Page Functionality
  As a logged-in user
  I want to browse and sort products
  So that I can find items to purchase

  Background:
    Given I am logged in as standard user

  @smoke
  Scenario: Verify inventory page displays 6 products
    Then I should see 6 products on the inventory page

  Scenario: Verify all product names are displayed
    Then all product names should be visible
    And product "Sauce Labs Backpack" should be listed
    And product "Sauce Labs Onesie" should be listed

  @sort
  Scenario: Sort products by Name A to Z
    When I sort products by "az"
    Then the first product should be "Sauce Labs Backpack"

  @sort
  Scenario: Sort products by Name Z to A
    When I sort products by "za"
    Then the first product should start with "Test.allTheThings"

  @sort
  Scenario: Sort products by Price Low to High
    When I sort products by "lohi"
    Then products should be sorted by price in ascending order

  @sort
  Scenario: Sort products by Price High to Low
    When I sort products by "hilo"
    Then products should be sorted by price in descending order

  @cart
  Scenario: Add product to cart from inventory
    When I add product at index 0 to cart
    Then the cart badge should show 1

  @cart
  Scenario: Add multiple products to cart
    When I add product at index 0 to cart
    And I add product at index 1 to cart
    And I add product at index 2 to cart
    Then the cart badge should show 3

  @cart
  Scenario: Remove product from inventory
    When I add product at index 0 to cart
    Then the cart badge should show 1
    When I remove product "Sauce Labs Backpack" from inventory
    Then the cart badge should not be displayed

  Scenario: Click product navigates to detail page
    When I click on product "Sauce Labs Backpack"
    Then I should be on the product detail page
    And the product name should be "Sauce Labs Backpack"

  Scenario: Verify footer has social links
    Then the footer should have 3 social links

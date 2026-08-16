@product-detail
Feature: Product Detail Page
  As a logged-in user
  I want to view product details
  So that I can make informed purchase decisions

  Background:
    Given I am logged in as standard user

  @smoke
  Scenario: Product detail page displays all elements
    When I click on product "Sauce Labs Backpack"
    Then the product name should be "Sauce Labs Backpack"
    And the product price should be "$29.99"
    And the product image should be displayed
    And the add to cart button should be displayed

  Scenario: Add to cart from product detail page
    When I click on product "Sauce Labs Backpack"
    And I click add to cart on detail page
    Then the remove button should be displayed on detail page

  Scenario: Remove from product detail page
    When I click on product "Sauce Labs Backpack"
    And I click add to cart on detail page
    And I click remove on detail page
    Then the add to cart button should be displayed

  Scenario: Navigate back to products
    When I click on product "Sauce Labs Backpack"
    And I click back to products
    Then I should be redirected to the inventory page

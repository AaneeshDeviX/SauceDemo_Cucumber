@cart
Feature: Shopping Cart
  As a user with items in my cart
  I want to manage my cart items
  So that I can proceed to checkout

  Background:
    Given I am logged in as standard user

  @smoke
  Scenario: Cart page displays added items
    When I add product at index 0 to cart
    And I add product at index 1 to cart
    And I navigate to the cart page
    Then the cart should contain 2 items

  Scenario: Verify item quantity is 1
    When I add product at index 0 to cart
    And I navigate to the cart page
    Then item at index 0 should have quantity "1"

  Scenario: Remove item from cart
    When I add product at index 0 to cart
    And I add product at index 1 to cart
    And I navigate to the cart page
    And I remove item at index 0 from cart
    Then the cart should contain 1 items

  Scenario: Continue Shopping returns to inventory
    When I add product at index 0 to cart
    And I navigate to the cart page
    And I click continue shopping
    Then I should be redirected to the inventory page

  Scenario: Checkout button is displayed
    When I add product at index 0 to cart
    And I navigate to the cart page
    Then the checkout button should be displayed

  Scenario: Cart persists after navigation
    When I add product at index 0 to cart
    And I add product at index 1 to cart
    And I click on product at index 2
    And I click back to products
    Then the cart badge should show 2

@checkout
Feature: Checkout Flow
  As a user ready to purchase
  I want to complete the checkout process
  So that I can buy products

  Background:
    Given I am logged in as standard user
    And I add product at index 0 to cart
    And I navigate to the cart page
    And I click checkout

  @smoke
  Scenario: Checkout step one form loads
    Then I should be on checkout step one

  @negative
  Scenario: Empty form shows first name required error
    When I click continue on checkout
    Then I should see checkout error "First Name is required"

  @negative
  Scenario: Missing last name shows error
    When I enter first name "John" on checkout
    And I click continue on checkout
    Then I should see checkout error "Last Name is required"

  @negative
  Scenario: Missing postal code shows error
    When I enter first name "John" on checkout
    And I enter last name "Doe" on checkout
    And I click continue on checkout
    Then I should see checkout error "Postal Code is required"

  @smoke
  Scenario: Valid info proceeds to overview
    When I fill checkout info "John" "Doe" "10001"
    And I click continue on checkout
    Then I should be on checkout step two

  Scenario: Cancel returns to cart
    When I click cancel on checkout step one
    Then I should be on the cart page

  Scenario: Overview shows correct item count
    When I fill checkout info "John" "Doe" "10001"
    And I click continue on checkout
    Then the overview should show 1 item

  Scenario: Total equals subtotal plus tax
    When I fill checkout info "John" "Doe" "10001"
    And I click continue on checkout
    Then the total should equal subtotal plus tax

  Scenario: Payment and shipping info displayed
    When I fill checkout info "John" "Doe" "10001"
    And I click continue on checkout
    Then payment info should be displayed
    And shipping info should be displayed

  @smoke @e2e
  Scenario: Complete end-to-end purchase
    When I fill checkout info "John" "Doe" "10001"
    And I click continue on checkout
    And I click finish on checkout
    Then I should see "Thank you for your order!"
    And I should be on the checkout complete page

  Scenario: Cart clears after purchase
    When I fill checkout info "John" "Doe" "10001"
    And I click continue on checkout
    And I click finish on checkout
    And I click back home
    Then the cart badge should not be displayed

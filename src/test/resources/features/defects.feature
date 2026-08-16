@defect
Feature: Known Defects Verification
  As a QA engineer
  I want to verify known defects exist
  So that they are documented and tracked

  @security
  Scenario: Credentials should not be visible on login page
    Given I am on the SauceDemo login page
    Then credentials should not be displayed on the login page

  @defect-images
  Scenario: Problem user sees broken images
    Given I am on the SauceDemo login page
    When I enter username "problem_user" and password "secret_sauce"
    And I click the login button
    Then all product images should be identical

  @defect-checkout
  Scenario: Problem user cannot type first name
    Given I am on the SauceDemo login page
    When I enter username "problem_user" and password "secret_sauce"
    And I click the login button
    And I add product at index 0 to cart
    And I navigate to the cart page
    And I click checkout
    And I enter first name "Jane" on checkout
    Then the first name field value should be verified for problem user

  @defect-performance
  Scenario: Performance glitch user login delay
    Given I am on the SauceDemo login page
    When I enter username "performance_glitch_user" and password "secret_sauce"
    And I click the login button
    Then I should be redirected to the inventory page

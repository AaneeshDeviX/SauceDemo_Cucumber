@navigation
Feature: Navigation and Sidebar Menu
  As a logged-in user
  I want to navigate the application
  So that I can access all sections

  Background:
    Given I am logged in as standard user

  Scenario: Burger menu opens
    When I open the burger menu
    Then the sidebar menu should be displayed

  @smoke
  Scenario: Logout redirects to login page
    When I open the burger menu
    And I click logout
    Then I should be on the login page

  Scenario: Reset app state clears cart
    When I add product at index 0 to cart
    And I add product at index 1 to cart
    When I open the burger menu
    And I click reset app state
    And I close the sidebar menu
    Then the cart badge should not be displayed

  Scenario: Footer social links are present
    Then the footer should have 3 social links

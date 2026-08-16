@login
Feature: Login Functionality
  As a user of SauceDemo
  I want to login with valid credentials
  So that I can access the product store

  Background:
    Given I am on the SauceDemo login page

  @smoke @positive
  Scenario: Successful login with standard user
    When I enter username "standard_user" and password "secret_sauce"
    And I click the login button
    Then I should be redirected to the inventory page
    And the page title should be "Products"

  @negative
  Scenario: Login with empty username
    When I enter username "" and password "secret_sauce"
    And I click the login button
    Then I should see error message containing "Username is required"

  @negative
  Scenario: Login with empty password
    When I enter username "standard_user" and password ""
    And I click the login button
    Then I should see error message containing "Password is required"

  @negative
  Scenario: Login with invalid credentials
    When I enter username "invalid_user" and password "invalid_pass"
    And I click the login button
    Then I should see error message containing "do not match"

  @negative
  Scenario: Login with locked out user
    When I enter username "locked_out_user" and password "secret_sauce"
    And I click the login button
    Then I should see error message containing "locked out"

  @security
  Scenario: Password field should be masked
    Then the password field type should be "password"

  @negative
  Scenario Outline: Login with various invalid credentials
    When I enter username "<username>" and password "<password>"
    And I click the login button
    Then I should see error message containing "<error>"

    Examples:
      | username        | password     | error               |
      |                 |              | Username is required |
      | standard_user   |              | Password is required |
      |                 | secret_sauce | Username is required |
      | wrong           | wrong        | do not match         |
      | locked_out_user | secret_sauce | locked out           |

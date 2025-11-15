Feature: Verify the login functionality

  Scenario: Positive flow
    Given the application is launched successfully
    When the user enters the email address as "rajkumar@testleaf.com"
    And the user enters the password as "Leaf@123"
    And the user clicks on the login button
    Then Verify Home page is displayed

  Scenario: Negative flow
    Given the application is launched successfully
    When the user enters the email address as "lokesh@testleaf.com"
    And the user enters the password as "Leaf@123"
    And the user clicks on the login button
    But Error message is displayed

Feature: Login feature

  Scenario: Successful login with valid credentials
    Given User is on login page
    When User enters valid username and password
    Then User should be redirected to dashboard

  Scenario: Successful logout and verify login page after logout
    Given User is already logged in
    When User clicks on logout icon
    Then User should be logged out and redirected to login page

  Scenario: Test login failure if wrong username or password enters
    Given User is on login page
    When User try to enter wrong username or password
    Then User should get invalid username or password error message


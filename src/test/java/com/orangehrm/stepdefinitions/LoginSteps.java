package com.orangehrm.stepdefinitions;

import io.cucumber.java.en.*;

import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utils.ConfigReader;
import org.testng.Assert;

public class LoginSteps {
    LoginPage loginPage = new LoginPage(Hooks.driver); // We'll add Hooks class for setup
    HomePage homePage = new HomePage(Hooks.driver);

    @Given("User is on login page")
    public void user_is_on_login_page() {
        loginPage.isLoginPageDisplayed();
    }

    @When("User enters valid username and password")
    public void user_enters_valid_username_and_password() {
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
    }

    @Then("User should be redirected to dashboard")
    public void user_should_be_redirected_to_dashboard() {
        Assert.assertTrue(homePage.isUserLoggedIn());
    }

    @Given("User is already logged in")
    public void user_is_already_logged_in() {
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        Assert.assertTrue(homePage.isUserLoggedIn());
    }

    @When("User clicks on logout icon")
    public void user_clicks_on_logout_icon() {
        homePage.logout();
    }

    @Then("User should be logged out and redirected to login page")
    public void user_should_be_logged_out_and_redirected_to_login_page() {
        Assert.assertTrue(loginPage.isLoginPageDisplayed());
    }

    @When("User try to enter wrong username or password")
    public void user_try_to_enter_wrong_username_or_password() {
        loginPage.login("testuser", "test123");
    }

    @Then("User should get invalid username or password error message")
    public void user_should_get_invalid_username_or_password_error_message() {
        Assert.assertTrue(loginPage.isInvalidCredentials());
    }
}

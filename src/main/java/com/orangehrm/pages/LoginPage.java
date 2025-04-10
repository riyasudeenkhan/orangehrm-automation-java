package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.utils.WaitHelper;

public class LoginPage {
    private WebDriver driver;
    private WaitHelper waitObj;

    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.cssSelector("button[type='submit']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.waitObj = new WaitHelper(driver);
    }

    public void login(String username, String password) {
        waitObj.waitForElementVisible(usernameField, 60);
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public Boolean isLoginPageDisplayed() {
        waitObj.waitForElementVisible(loginButton, 60);
        return driver.findElement(loginButton).isDisplayed();
    }
}
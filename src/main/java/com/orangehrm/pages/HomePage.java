package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import com.orangehrm.utils.Log;
import com.orangehrm.utils.WaitHelper;

public class HomePage {
    private static final Logger logger = Log.getLogger(AdminPage.class);
    WebDriver driver;
    private WaitHelper waitObj;

    private By profileIcon = By.className("oxd-userdropdown-tab");
    private By logoutBtn = By.xpath("//a[text()='Logout']");
    private By header = By.tagName("h6");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.waitObj = new WaitHelper(driver);
    }

    public String getHeaderText() {
        return driver.findElement(header).getText();
    }

    public void logout() {
        waitObj.waitForElementVisible(profileIcon, 60);
        driver.findElement(profileIcon).click();
        driver.findElement(logoutBtn).click();
    }

    public Boolean isUserLoggedIn() {
        waitObj.waitForElementVisible(profileIcon, 60);
        return driver.findElement(profileIcon).isDisplayed();
    }
}

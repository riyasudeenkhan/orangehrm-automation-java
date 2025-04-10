package com.orangehrm.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class WaitHelper {
    private WebDriver driver;

    public WaitHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForElementVisible(By element, int timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public WebElement getElement(By element) {
        return driver.findElement(element);
    }

    public void clickWhenReady(By element, int timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public String getTextWhenVisible(By element, int timeoutInSeconds) {
        waitForElementVisible(element, timeoutInSeconds);
        return getElement(element).getText();
    }

    public void customHardWait(int millis) {
        // Wait for suggestion to appear
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

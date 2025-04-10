package com.orangehrm.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.orangehrm.utils.WaitHelper;

public class LeavePage {
    private WebDriver driver;
    private WaitHelper waitObj;

    private By leave = By.xpath("//span[text()='Leave']");
    private By pageTitle = By.tagName("h6");
    private By tabTitle = By.tagName("h5");
    private By leaveTypeDropdown = By.xpath("//label[text()='Leave Type']/following::div[@class='oxd-select-text-input']");
    private By tableRows = By.cssSelector(".oxd-table-body .oxd-table-row");

    public LeavePage(WebDriver driver) {
        this.driver = driver;
        this.waitObj = new WaitHelper(driver);
    }

    public void gotoLeavePage() {
        waitObj.waitForElementVisible(leave, 30);
        driver.findElement(leave).click();
    }

    public String getPageTitle() {
        waitObj.waitForElementVisible(pageTitle, 30);
        return driver.findElement(pageTitle).getText();
    }

    public void selectLeaveTab(String tabName) {
        By myLeaveTab = By.xpath("//a[text()='" + tabName + "']");
        waitObj.waitForElementVisible(myLeaveTab, 30);
        driver.findElement(myLeaveTab).click();
        waitObj.waitForElementVisible(tabTitle, 30);
    }

    public Boolean isMyLeaveTabSelected() {
        return driver.findElement(tabTitle).isDisplayed();
    }

    public void selectLeaveType(String type) {
        waitObj.waitForElementVisible(leaveTypeDropdown, 30);
        driver.findElement(leaveTypeDropdown).click();
        By option = By.xpath("//div[@role='option']/span[text()='" + type + "']");
        waitObj.waitForElementVisible(option, 30);
        driver.findElement(option).click();
    }

    public List<String> getLeaveTableData() {
        List<WebElement> rows = driver.findElements(tableRows);
        List<String> data = new ArrayList<>();
        for (WebElement row : rows) {
            data.add(row.getText());
        }
        return data;
    }

}

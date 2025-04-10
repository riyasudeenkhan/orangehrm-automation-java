package com.orangehrm.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.orangehrm.utils.WaitHelper;

public class AdminPage {
    private WebDriver driver;
    private WaitHelper waitHelper;

    private By adminLink = By.xpath("//span[text()='Admin']");
    private By pageHeader = By.tagName("h5");
    private By userRole = By.xpath("//label[text()='User Role']/following::div[@class='oxd-select-text-input'][1]");
    private By status = By.xpath("//label[text()='Status']/following::div[@class='oxd-select-text-input']");
    private By submit = By.xpath("//button[@type='submit']");
    private By reset = By.cssSelector("button.oxd-button.oxd-button--medium.oxd-button--ghost");
    private By tableRows = By.cssSelector(".oxd-table-body .oxd-table-row");
    private By addButton = By.cssSelector("button > i.oxd-icon.bi-plus.oxd-button-icon");
    private By employeeName = By.xpath("//label[text()='Employee Name']/following::input[1]");
    private By userName = By.xpath("//label[text()='Username']/following::input[1]");
    private By password = By.xpath("//label[text()='Password']/following::input[1]");
    private By confirmPassword = By.xpath("//label[text()='Confirm Password']/following::input");
    By noRecordsMsg = By.xpath("//span[text()='No Records Found']");

    public AdminPage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
    }

    public void gotoAdminPage() {
        waitHelper.waitForElementVisible(adminLink, 30);
        driver.findElement(adminLink).click();
        waitHelper.waitForElementVisible(pageHeader, 20);
    }

    public String getPageTitle() {
        return driver.findElement(pageHeader).getText();
    }

    public void selectUserRoleFilter(String option) {
        driver.findElement(userRole).click();
        By optionLocator = By.xpath("//div[@role='listbox']//span[text()='" + option + "']");
        waitHelper.waitForElementVisible(optionLocator, 10);
        driver.findElement(optionLocator).click();
    }

    public void selectStatusFilter(String option) {
        driver.findElement(status).click();
        By optionLocator = By.xpath("//div[@role='listbox']//span[text()='" + option + "']");
        waitHelper.waitForElementVisible(optionLocator, 10);
        driver.findElement(optionLocator).click();
    }

    public void clickResetButton() {
        driver.findElement(reset).click();
    }

    public void clickSubmitButton() {
        driver.findElement(submit).click();
    }

    public List<String> getTableData() {
        List<String> data = new ArrayList<>();
        int retries = 2;

        while (retries-- > 0) {
            try {
                // Check if "No Records Found" is displayed
                List<WebElement> noDataElements = driver.findElements(noRecordsMsg);
                if (!noDataElements.isEmpty() && noDataElements.get(0).isDisplayed()) {
                    return data; // Return empty list
                }

                waitHelper.waitForElementVisible(tableRows, 10);
                List<WebElement> rows = driver.findElements(tableRows);
                for (WebElement row : rows) {
                    data.add(row.getText());
                }
                return data;

            } catch (StaleElementReferenceException e) {
                System.out.println("Retrying due to stale element...");
            } catch (Exception e) {
                System.out.println("Exception while fetching table data: " + e.getMessage());
            }
        }

        return data; // Return empty list as fallback
    }

    public void enterEmployeeName(String nameToType, String fullNameToSelect) {
        waitHelper.waitForElementVisible(employeeName, 10);

        WebElement inputBox = driver.findElement(employeeName);
        inputBox.clear();
        inputBox.sendKeys(nameToType);

        Actions actions = new Actions(driver);
        // Wait for suggestion to appear
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
    }

    public void addUser(String userNameInput) {
        waitHelper.waitForElementVisible(addButton, 10);
        driver.findElement(addButton).click();
        waitHelper.waitForElementVisible(userName, 20);
        selectUserRoleFilter("Admin");
        enterEmployeeName("Orange", "Orange Test");
        selectStatusFilter("Enabled");
        driver.findElement(userName).sendKeys(userNameInput);
        driver.findElement(password).sendKeys("maven123");
        driver.findElement(confirmPassword).sendKeys("maven123");
        driver.findElement(submit).click();
    }

    public void deleteUserByUsername(String username) {
        // 1. Search user
        searchUser(username);
        waitHelper.waitForElementVisible(By.cssSelector(".oxd-table-body"), 10);

        List<WebElement> rows = driver.findElements(By.cssSelector(".oxd-table-body .oxd-table-row"));
        if (rows.isEmpty()) {
            System.out.println("No user found to delete.");
            return;
        }

        boolean userFound = false;
        for (WebElement row : rows) {
            String rowText = row.getText();
            if (rowText.contains(username)) {
                System.out.println("Found matching user row: " + rowText);
                userFound = true;

                // ✅ 2. Find and click the checkbox within this row
                WebElement checkbox = row.findElement(By.cssSelector("div.oxd-table-cell input[type='checkbox']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);
                checkbox.click();

                // ✅ 3. Click the delete icon (within the same row or globally)
                WebElement deleteIcon = row.findElement(By.cssSelector("button > i.bi-trash"));
                deleteIcon.click();

                // ✅ 4. Confirm deletion
                By confirmDelete = By.xpath("//button[normalize-space()='Yes, Delete']");
                waitHelper.waitForElementVisible(confirmDelete, 10);
                driver.findElement(confirmDelete).click();

                System.out.println("Deleted user: " + username);
                break;
            }
        }

        if (!userFound) {
            System.out.println("User row with matching username not found.");
        }
    }

    public void searchUser(String user) {
        waitHelper.waitForElementVisible(userName, 0);
        driver.findElement(userName).sendKeys(user);
        driver.findElement(submit).click();
    }

    public Boolean isUserExist(String user) {
        Boolean flag = true; // assume user is exist
        searchUser(user);
        By tableHeader = By.xpath("//div[@role='columnheader'][1]");
        waitHelper.waitForElementVisible(tableHeader, 10);
        // Check if "No Records Found" is displayed
        List<WebElement> noDataElements = driver.findElements(noRecordsMsg);
        if (!noDataElements.isEmpty() && noDataElements.get(0).isDisplayed()) {
            flag = false; // false means user not exist
        }
        // reset search result
        driver.findElement(reset).click();
        return flag;
    }
}

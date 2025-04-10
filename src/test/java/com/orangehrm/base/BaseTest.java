package com.orangehrm.base;

import com.orangehrm.utils.ConfigReader;
import com.orangehrm.utils.WaitHelper;
import com.orangehrm.pages.AdminPage;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LeavePage;
import com.orangehrm.pages.LoginPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    protected WebDriver driver;
    protected LoginPage loginPage;
    protected LeavePage leavePage;
    protected HomePage homePage;
    protected AdminPage adminPage;
    protected WaitHelper waitHelper;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(ConfigReader.get("baseUrl"));

        // Instantiate all commonly used classes
        loginPage = new LoginPage(driver);
        leavePage = new LeavePage(driver);
        homePage = new HomePage(driver);
        adminPage = new AdminPage(driver);
        waitHelper = new WaitHelper(driver);

        // Login once before running all test methods
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
    }

    @AfterClass
    public void tearDown() {
        // Logout and quit once after all test methods are done
        homePage.logout();
        if (driver != null) {
            driver.quit();
        }
    }
}
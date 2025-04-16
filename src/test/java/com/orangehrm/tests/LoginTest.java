package com.orangehrm.tests;

import com.orangehrm.pages.LoginPage;
import com.orangehrm.listeners.RetryAnalyzer;
import com.orangehrm.pages.HomePage;
import com.orangehrm.utils.ConfigReader;
import com.orangehrm.utils.Log;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;

public class LoginTest {
    private static final Logger logger = Log.getLogger(LoginTest.class);

    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;
    
    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(ConfigReader.get("baseUrl"));

        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
    }

    @Test
    public void testLoginSuccess() {
        String user = ConfigReader.get("username");
        String password = ConfigReader.get("password");
        logger.info("Logging in with user: {}", user);
        loginPage.login(user, password);
        Assert.assertTrue(homePage.isUserLoggedIn());
    }

    @Test
    public void testLoginAndLogout() {
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        homePage.logout();
        Assert.assertTrue(loginPage.isLoginPageDisplayed());
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testLoginFailure() {
        //flaky test
        loginPage.login("test", "test123");
        Assert.assertTrue(homePage.isUserLoggedIn());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
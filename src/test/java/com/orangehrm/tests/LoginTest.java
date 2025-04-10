package com.orangehrm.tests;

import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.HomePage;
import com.orangehrm.utils.ConfigReader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.testng.AllureTestNg;

@Listeners({AllureTestNg.class})
public class LoginTest {

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
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        Assert.assertTrue(homePage.isUserLoggedIn()); 
    }

    @Test
    public void testLoginAndLogout() {
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        homePage.logout();
        Assert.assertTrue(loginPage.isLoginPageDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
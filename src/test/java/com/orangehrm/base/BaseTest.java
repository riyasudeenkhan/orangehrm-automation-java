package com.orangehrm.base;

import com.orangehrm.utils.ConfigReader;
import com.orangehrm.utils.WaitHelper;
import com.orangehrm.pages.AdminPage;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LeavePage;
import com.orangehrm.pages.LoginPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    // Thread-safe WebDriver
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected LoginPage loginPage;
    protected LeavePage leavePage;
    protected HomePage homePage;
    protected AdminPage adminPage;
    protected WaitHelper waitHelper;

    @BeforeClass
    public void setUp() throws Exception {
        WebDriverManager.chromedriver().setup();

        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        ChromeOptions options = new ChromeOptions();
        if (isHeadless) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--remote-allow-origins=*");
        }
        System.out.println("Chrome options: " + options.asMap());
        driver.set(new ChromeDriver(options));
        driver.get().manage().window().maximize();
        driver.get().get(ConfigReader.get("baseUrl"));
        // Instantiate all commonly used classes
        loginPage = new LoginPage(driver.get());
        leavePage = new LeavePage(driver.get());
        homePage = new HomePage(driver.get());
        adminPage = new AdminPage(driver.get());
        waitHelper = new WaitHelper(driver.get());

        // Login once before running all test methods
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    @AfterClass
    public void tearDown() {
        // Logout and quit once after all test methods are done
        homePage.logout();
        getDriver().quit();
        driver.remove(); // Clean up thread-local memory
    }
}
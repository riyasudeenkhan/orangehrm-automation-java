package com.orangehrm.listeners;

import java.io.FileInputStream;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.orangehrm.base.BaseTest;
import com.orangehrm.utils.ScreenshotUtil;

import io.qameta.allure.Allure;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("[START] " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("[PASS] " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("[FAIL] " + result.getMethod().getMethodName());

        Object currentClass = result.getInstance();
        WebDriver driver = ((BaseTest) currentClass).getDriver(); // Assumes all tests extend BaseTest

        String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getMethod().getMethodName());

        System.out.println("Screenshot saved at: " + screenshotPath);

        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.getLifecycle().addAttachment("Screenshot", "image/png", "png", screenshot);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (result.getMethod().getCurrentInvocationCount() > 1) {
            // This was retried, don't log as skipped
            return;
        }
        System.out.println("[SKIPPED] " + result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("===== Test Suite Started =====");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("===== Test Suite Finished =====");
    }
}

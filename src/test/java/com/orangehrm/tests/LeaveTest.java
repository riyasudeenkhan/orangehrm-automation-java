package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.utils.Log;

import io.qameta.allure.Allure;

import java.util.List;

import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LeaveTest extends BaseTest {
    private static final Logger logger = Log.getLogger(LeaveTest.class);

    @BeforeMethod
    public void navigateToLeave() {
        leavePage.gotoLeavePage();
    }

    @Test
    public void testLeavePageLoads() {
        logger.info("🟡 This should appear in Allure via logger");
        Allure.step("🟢 Direct Allure step for verification");
        leavePage.gotoLeavePage();
        String actualTitle = leavePage.getPageTitle();
        logger.info("Actual Title = {}", actualTitle);
        Assert.assertEquals(actualTitle, "Leave", "Leave page title mismatch!");
    }

    @Test
    public void testMyLeaveTabDisplaysData() {
        leavePage.selectLeaveTab("My Leave");
        List<String> data = leavePage.getLeaveTableData();

        Assert.assertNotNull(data, "Data list should not be null.");
        Assert.assertFalse(data.isEmpty(), "No leave records found in 'My Leave' tab.");
    }

    @Test
    public void testLeaveTypeFilter() {
        leavePage.selectLeaveType("US - Vacation");
        List<String> filteredData = leavePage.getLeaveTableData();

        Assert.assertNotNull(filteredData, "Leave data should not be null after filtering.");
        Assert.assertFalse(filteredData.isEmpty(), "Filtered leave data is empty for 'US - Vacation'.");
    }

}

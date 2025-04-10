package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.qameta.allure.testng.AllureTestNg;

@Listeners({ AllureTestNg.class })
public class LeaveTest extends BaseTest {

    @BeforeMethod
    public void navigateToLeave() {
        leavePage.gotoLeavePage();
    }

    @Test
    public void testLeavePageLoads() {
        leavePage.gotoLeavePage();
        String actualTitle = leavePage.getPageTitle();
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

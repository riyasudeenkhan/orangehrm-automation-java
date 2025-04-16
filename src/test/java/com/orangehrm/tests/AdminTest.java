package com.orangehrm.tests;

import java.util.List;

import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;
import com.orangehrm.utils.Log;

public class AdminTest extends BaseTest {
    private static final Logger logger = Log.getLogger(AdminTest.class);

    @DataProvider(name = "filters")
    public Object[][] filters() {
        return new Object[][] {
                { "UserRole", "Admin" },
                { "UserRole", "ESS" },
                { "Status", "Enabled" },
                { "Status", "Disabled" }
        };
    }

    @BeforeMethod
    public void navigateToLeave() {
        adminPage.gotoAdminPage();
    }

    @Test
    public void testSystemUsersTableLoads() {
        String actualTableName = adminPage.getPageTitle();
        String expectedTableName = "System Users";

        Assert.assertEquals(actualTableName, expectedTableName, "Expected table has not loaded..");
    }

    @Test(dataProvider = "filters")
    public void testUserFilters(String filterType, String value) {
        if (filterType.equals("UserRole")) {
            adminPage.selectUserRoleFilter(value);
        } else {
            adminPage.selectStatusFilter(value);
        }
        adminPage.clickSubmitButton();
        List<String> filteredData = adminPage.getTableData();

        Assert.assertNotNull(filteredData, "Filtered data is null.");
        Assert.assertFalse(filteredData.isEmpty(), "Filtered data is empty for: " + value);
    }

    @Test
    public void testDeleteUser() {
        String username = "testingMavens123"; // Use a real/existing user
        if (!adminPage.isUserExist(username)) {
            logger.info("User doesn't exist. Creating: " + username);
            adminPage.addUser(username);
            logger.info("User is created successfully");
        } else {
            logger.info("User already exists: " + username);
        }
        adminPage.deleteUserByUsername(username);
        adminPage.searchUser(username);
        List<String> searchData = adminPage.getTableData();
        logger.info("Search result data: " + searchData);
        Assert.assertTrue(searchData.isEmpty(), "Searched data should be empty for: " + username);
    }

}

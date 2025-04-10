package com.orangehrm.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseTest;

public class AdminTest extends BaseTest {

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
            System.out.println("User doesn't exist. Creating: " + username);
            adminPage.addUser(username);
            System.out.println("User is created successfully");
        } else {
            System.out.println("User already exists: " + username);
        }
        adminPage.deleteUserByUsername(username);
        adminPage.searchUser(username);
        List<String> searchData = adminPage.getTableData();
        System.out.println("Search result data: " + searchData);
        Assert.assertTrue(searchData.isEmpty(), "Searched data should be empty for: " + username);
    }

}

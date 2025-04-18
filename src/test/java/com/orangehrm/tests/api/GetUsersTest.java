package com.orangehrm.tests.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.testng.annotations.Test;

import com.orangehrm.utils.Log;
import static org.testng.Assert.*;

import org.slf4j.Logger;
import java.util.logging.LogManager;
import org.slf4j.bridge.SLF4JBridgeHandler;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetUsersTest {
    private static final Logger logger = Log.getLogger(GetUsersTest.class);

    static {
        // Redirect JUL (java.util.logging) to SLF4J
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @Test
    public void testGetAllUsers() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given()
                .when()
                .get("/users")
                .then()
                .extract().response();

        logger.info("Response: " + response.asPrettyString());

        // Basic assertions
        assertEquals(response.getStatusCode(), 200);
        assertTrue(response.getBody().asString().contains("Leanne Graham"));
    }

    @Test
    public void testValidateFirstUserDetails() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract().response();

        String name = response.jsonPath().getString("[1].name");
        String email = response.jsonPath().getString("[1].email");

        logger.info("User: " + name + " | Email: " + email);

        assertEquals(name, "Leanne Graham");
        assertTrue(email.contains("@"));
    }

    @Test
    public void validateUserDetails() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given()
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .body("username", equalTo("Bret"))
                .body("address.city", equalTo("Gwenborough"))
                .body("company.name", containsString("Romaguera"))
                .log().all(); // Optional: log full response
    }

}

package com.orangehrm.tests.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeletePostTest {

    @Test
    public void testDeletePost() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given()
                .when()
                .delete("/posts/1");

        System.out.println("DELETE Response:\n" + response.asPrettyString());

        // Verify response status
        Assert.assertEquals(response.statusCode(), 200);

        // For mock API, no body is returned — but in real-world you'd validate JSON or message
    }
}

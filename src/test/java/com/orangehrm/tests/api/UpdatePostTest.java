package com.orangehrm.tests.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UpdatePostTest {

    @Test(priority = 1)
    public void testUpdatePost() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        String updatedBody = """
                    {
                      "id": 1,
                      "title": "Updated Title",
                      "body": "Updated body using PUT",
                      "userId": 1
                    }
                """;

        Response response = given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("GET Response:\n" + response.asPrettyString());

        response = given()
                .contentType(ContentType.JSON)
                .body(updatedBody)
                .when()
                .put("/posts/1");

        // Logging
        System.out.println("PUT Response:\n" + response.asPrettyString());

        // Validations
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("title"), "Updated Title");
    }

    @Test(priority = 2)
    public void testPatchPostTitle() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        String patchBody = """
                    {
                      "title": "Patched Title"
                    }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(patchBody)
                .when()
                .patch("/posts/1");

        System.out.println("PATCH Response:\n" + response.asPrettyString());

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("title"), "Patched Title");
    }

}
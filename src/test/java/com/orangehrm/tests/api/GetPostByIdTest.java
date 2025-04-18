package com.orangehrm.tests.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetPostByIdTest {

    @Test(enabled = false)
    public void testGetPostById() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given()
                .pathParam("postId", 10)
                .when()
                .get("/posts/{postId}");

        System.out.println("Response: " + response.asPrettyString());

        // Assertions
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getInt("id"), 10);
        Assert.assertEquals(response.jsonPath().getString("title").length() > 0, true);
    }

    // Query Param
    @Test(enabled = false)
    public void testGetByQueryParam() {
        System.out.println("Test Query Parameter");
        given()
                .queryParam("page", 1)
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200)
                .body("total", is(equalTo(12)))
                .log().body();

        // System.out.println("Multiple Query Parameter");
        // given()
        // .queryParam("q", "test")
        // .queryParam("sort", "desc")
        // .when()
        // .get("https://api.example.com/search")
        // .then()
        // .statusCode(200)
        // .log().body();
    }

    // Path Param
    @Test
    public void testGetByPathParam() {
        System.out.println("Test Path Parameter");
        given()
                .pathParam("id", 3)
                .when()
                .get("https://reqres.in/api/users/{id}")
                .then()
                .statusCode(200)
                .body("data.first_name", equalTo("Emma"))
                .log().all();
    }
}

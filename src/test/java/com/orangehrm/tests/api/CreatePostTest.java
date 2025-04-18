package com.orangehrm.tests.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.orangehrm.tests.api.pojos.CreateUserData;
import com.orangehrm.tests.api.pojos.CreateUserRequest;
import com.orangehrm.tests.api.pojos.GetUserResponse;
import com.orangehrm.tests.api.pojos.Support;
import com.orangehrm.tests.api.pojos.UserData;

import static io.restassured.RestAssured.given;

public class CreatePostTest {

    @Test(enabled = false)
    public void testCreatePost() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        String requestBody = """
                    {
                      "title": "Automation API Test 2",
                      "body": "This is a post created using Rest Assured 2",
                      "userId": 11
                    }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/posts");

        // Print response
        System.out.println("Response: " + response.asPrettyString());
        System.out.println("Id: " + response.jsonPath().getInt("id"));

        // Assert status code and response content
        Assert.assertEquals(response.statusCode(), 201);
        Assert.assertEquals(response.jsonPath().getString("title"), "Automation API Test 2");
        Assert.assertEquals(response.jsonPath().getInt("userId"), 11);
    }

    @Test(enabled = false)
    public void testPostWithPojo() {
        CreateUserRequest payload = new CreateUserRequest("morpheus", "leader");

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .log().body();
    }

    @Test
    public void testGetAndDeserialize() {
        GetUserResponse response = given()
                .when()
                .get("https://reqres.in/api/users/147")
                .then()
                .statusCode(200)
                .extract().as(GetUserResponse.class);

        System.out.println("User email = " + response.getData().getEmail());
        System.out.println("Name = " + response.getData().getFirst_name() + " " + response.getData().getLast_name());
        System.out.println("Support Text = " + response.getSupport().getText());

    }

    @Test(enabled = false)
    public void testCreateUserViaSerialization() {
        // Prepare UserData
        UserData user = new UserData();
        user.setId(101);  // Normally backend sets this, but included here for demo
        user.setEmail("test.user@example.com");
        user.setFirst_name("Test");
        user.setLast_name("User");
        user.setAvatar("https://reqres.in/img/faces/1-image.jpg");

        // Prepare Support
        Support support = new Support();
        support.setUrl("https://reqres.in/#support-heading");
        support.setText("This is a test user created via serialization");

        // Combine into request object
        CreateUserData request = new CreateUserData();
        request.setData(user);
        request.setSupport(support);

        // POST the data
        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("https://reqres.in/api/users")
        .then()
            .statusCode(201) // Created
            .log().body();
    }

}
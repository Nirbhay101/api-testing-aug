package com.epam;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class HomeTask3 {


  /*  @DataProvider (name = "resources")
    public Object[][] resources(){
        return new Object[][] {{"/posts"}, {"/comments"},{"/albums"},{"/photos"},{"/todos"},{"/users"}};
    }*/

    @BeforeClass
    public void setup() {
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setBaseUri("http://petstore.swagger.io/#").setBasePath("/v2").
                build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().
                expectStatusCode(200).expectContentType("application/json").
                expectResponseTime(lessThan(3000L)).
                build();
    }

    @Test
    public void validatePetDetails() {
        RestAssured.defaultParser = Parser.JSON;

                when().get("/pet/12345").
                then().
                body("category.name", equalTo("dog")).
                body("name", equalTo("snoopie")).
                body("status", equalTo("pending"));
    }

}
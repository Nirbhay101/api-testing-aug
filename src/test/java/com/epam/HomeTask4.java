package com.epam;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.parsing.Parser;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;

public class HomeTask4 {

    @BeforeClass
    public void setup(){
        RestAssured.requestSpecification=new RequestSpecBuilder().
                setBaseUri("https://jsonplaceholder.typicode.com").
                build();
        RestAssured.responseSpecification=new ResponseSpecBuilder().
                expectStatusCode(200).expectContentType("application/json").
                expectResponseTime(lessThan(2000L)).
                build();
    }
    @Test
    public void getUsersCount(){
        RestAssured.defaultParser= Parser.JSON;
        boolean verifyCountmorethan3=false;
        int size=
                when().
                 get("/users").
                then().
                    extract().response().jsonPath().getList("$").size();
        if (size>3)
        verifyCountmorethan3=true;
        Assert.assertEquals(true,verifyCountmorethan3);
    }

    @Test
    public void verifyNameinUsers(){
        RestAssured.defaultParser= Parser.JSON;

            when().
                get("/users").
            then().
                body("name", hasItem("Ervin Howell"));
    }


}

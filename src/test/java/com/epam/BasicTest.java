package com.epam;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class BasicTest {

    private RequestSpecification reqSpec=new RequestSpecBuilder().setBaseUri("https://jsonplaceholder.typicode.com").build();
    private ResponseSpecification respSpec=new ResponseSpecBuilder().
            expectStatusCode(200).expectContentType("application/json").
            expectResponseTime(lessThan(2000L)).
            build();
    @DataProvider (name = "resources")
    public Object[][] resources(){
        return new Object[][] {{"/posts"}, {"/comments"},{"/albums"},{"/photos"},{"/todos"},{"/users"}};
    }
    @Test(dataProvider = "resources")
    public void getResourceCount(String res){
        RestAssured.defaultParser= Parser.JSON;
        System.out.println("Resources available in"+res+": "+given().spec(reqSpec).
                when().
                 get(res).
                then().
                    spec(respSpec).extract().response().jsonPath().getList("$").size());

    }

    @Test
    public void verifyTitleinPosts(){
        RestAssured.defaultParser= Parser.JSON;
            given().
               spec(reqSpec).
            when().
                get("/posts/6").
            then().
                spec(respSpec).body("title", equalTo("dolorem eum magni eos aperiam quia"));
    }

    @Test
    public void verifyNameinComments(){
        RestAssured.defaultParser= Parser.JSON;
        given().
                spec(reqSpec).
                when().
                get("/comments/6").
                then().
                assertThat().statusCode(200).body("name", equalTo("et fugit eligendi deleniti quidem qui sint nihil autem"));
    }
    @Test
    public void verifyTitleinAlbums(){
        RestAssured.defaultParser= Parser.JSON;
        given().
                spec(reqSpec).
                when().
                get("/albums/6").
                then().
                assertThat().statusCode(200).body("title", equalTo("natus impedit quibusdam illo est"));
    }

    @Test
    public void verifyCompletedStatusinTodos(){
        RestAssured.defaultParser= Parser.JSON;
        given().
                spec(reqSpec).
                when().
                get("/todos/6").
                then().
                assertThat().statusCode(200).body("completed", equalTo(false));
    }
    @Test
    public void verifyUsernameinUsers(){
        RestAssured.defaultParser= Parser.JSON;
        given().
                spec(reqSpec).
                when().
                get("/users/6").
                then().
                assertThat().statusCode(200).body("username", equalTo("Leopoldo_Corkery"));
    }

    @Test
    public void UpdateCompletedStatusinTodos(){
        RestAssured.defaultParser= Parser.JSON;
        given().
                spec(reqSpec).
                when().
                put("/todos/6").
                then().
                assertThat().statusCode(200).body("completed", equalTo(true));
    }
    @Test
    public void verifyStatusCodewithCoreJava() throws IOException {
        URL restURI=new URL("https://jsonplaceholder.typicode.com/posts");
        HttpURLConnection urlConnection=(HttpURLConnection) restURI.openConnection();
        urlConnection.setRequestMethod("GET");
        int statusCode=urlConnection.getResponseCode();
        Assert.assertEquals(statusCode,200);
    }
}

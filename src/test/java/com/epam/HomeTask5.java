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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class HomeTask5 {


    @DataProvider (name = "resources")
    public Object[][] resources(){
        return new Object[][] {{"/api/v1/employees"}};
    }

    @BeforeClass
    public void setup(){
        RestAssured.requestSpecification=new RequestSpecBuilder().
                setBaseUri("http://dummy.restapiexample.com").
                build();
        RestAssured.responseSpecification=new ResponseSpecBuilder().
                expectStatusCode(200).expectContentType("application/json").
                expectResponseTime(lessThan(3000L)).
                build();
    }
    @Test(dataProvider = "resources")
    public int getResourceCount(String resGet){
        RestAssured.defaultParser= Parser.JSON;
        List<String> EmployeeDetails=when().
                 get(resGet).
                then().extract().response().jsonPath().getList("data");
        System.out.println("Response Employee details: "+EmployeeDetails);
        System.out.println("Employees count: "+EmployeeDetails.size());
        return EmployeeDetails.size();
    }

    @Test(dataProvider = "resources")
    public void createEmployee(String resGet) throws InterruptedException{
        RestAssured.defaultParser= Parser.JSON;

            //int initialCount=getResourceCount(resGet);
            given().body("{\"name\":\"testEmp\",\"salary\":\"1234\",\"age\":\"23\"}").when().post("/api/v1/create").path("data").toString();
            int updatedCount=getResourceCount(resGet);
            //Assert.assertEquals(initialCount+1,updatedCount);
    }

    @Test
    public void verifyNameinComments() throws InterruptedException{
        RestAssured.defaultParser= Parser.JSON;
            when().
                get("/comments/6").
            then().
                assertThat().statusCode(200).body("name", equalTo("et fugit eligendi deleniti quidem qui sint nihil autem"));
    }
    @Test
    public void verifyTitleinAlbums(){
        RestAssured.defaultParser= Parser.JSON;
                when().
                get("/albums/6").
                then().
                assertThat().statusCode(200).body("title", equalTo("natus impedit quibusdam illo est"));
    }

    @Test
    public void verifyCompletedStatusinTodos(){
        RestAssured.defaultParser= Parser.JSON;
                when().
                get("/todos/6").
                then().
                assertThat().statusCode(200).body("completed", equalTo(false));
    }
    @Test
    public void verifyUsernameinUsers(){
        RestAssured.defaultParser= Parser.JSON;
            when().
                get("/users/6").
            then().
                assertThat().statusCode(200).body("username", equalTo("Leopoldo_Corkery"));
    }

    @Test
    public void UpdateCompletedStatusinTodos(){
        RestAssured.defaultParser= Parser.JSON;
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

package com.epam;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class HomeTask6 {


  /*  @DataProvider (name = "resources")
    public Object[][] resources(){
        return new Object[][] {{"/posts"}, {"/comments"},{"/albums"},{"/photos"},{"/todos"},{"/users"}};
    }*/

    @BeforeClass
    public void setup() {
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setBaseUri("https://events.epam.com/api").setBasePath("/v2").
                build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().
                expectStatusCode(200).expectContentType("application/json").
                expectResponseTime(lessThan(5000L)).
                build();
    }

    @Test
    public void validateEnglishEvents() {
        RestAssured.defaultParser = Parser.JSON;

                Response response=get("/events");
                List<String> eventName=response.path("events.title");
                int size=eventName.size();
                System.out.println("Total Number of Events: "+size);
                for (int i=0;i<size;i++){
                    String language=response.path("events["+i+"].language");

                if(language.equalsIgnoreCase("En")){
                System.out.println("Event Name: "+response.path("events["+i+"].title") +" is in English");}}
    }

}
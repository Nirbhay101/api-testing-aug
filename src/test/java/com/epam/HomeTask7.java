package com.epam;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.lessThan;

public class HomeTask7 {


  /*  @DataProvider (name = "resources")
    public Object[][] resources(){
        return new Object[][] {{"/posts"}, {"/comments"},{"/albums"},{"/photos"},{"/todos"},{"/users"}};
    }*/
    public static Map<Object,Object> coordinates= new HashMap<Object,Object>();



    String ApiKey="d39395139d40cec549e82863b647cdfe";
    String city="hyderabad";
    @BeforeClass
    public void setup() {

        RestAssured.requestSpecification = new RequestSpecBuilder().
                setBaseUri("http://api.openweathermap.org").setBasePath("/data/2.5").
                build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().
                expectStatusCode(200).expectContentType("application/json").
                expectResponseTime(lessThan(5000L)).
                build();
    }

    @Test
    public void getHyderabadWeather() {
        RestAssured.defaultParser = Parser.JSON;

                Response response=get("weather?q="+city+"&appid="+ApiKey);
                System.out.println("Weather details: "+response.body().asString());
                coordinates.put("Longitude",response.path("coord.lon"));
                coordinates.put("Latitude",response.path("coord.lat"));
                System.out.println (coordinates);

    }
    @Test
    public void verifyHyderabadWeather() {
        getHyderabadWeather();
        boolean verifyMinTemp=false,verifyTemp=false;
        RestAssured.defaultParser = Parser.JSON;
        Response response=get("weather?lat="+coordinates.get("Latitude")+"&lon="+coordinates.get("Longitude")+"&appid="+ApiKey);
        Assert.assertEquals(response.path("name"),"Hyderabad");
        Assert.assertEquals(response.path("sys.country"),"IN");
        if (Double.parseDouble(response.path("main.temp_min").toString())>0)
            verifyMinTemp=true;
        if (Double.parseDouble(response.path("main.temp").toString())>0)
            verifyTemp=true;
        Assert.assertEquals(verifyMinTemp,true);
        Assert.assertEquals(verifyTemp,true);

    }

}
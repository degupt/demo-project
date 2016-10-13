package com.qa.demo.restassured;

import static io.restassured.RestAssured.given;

import com.qa.demo.utils.config.Config;

import io.restassured.RestAssured;

/**
 * This Class will be extended in each of the test class to initialize webDriver and open the URL The TestNG annotation
 * is before and after methods.
 * 
 * @author Deenesh
 */
public class FuelStationManager extends BaseTestApi {
    /**
     * This method is invoked at the beginning of each test and sets up the browser instance.
     * 
     * @param context
     */
    public void getStationDetails() {
        RestAssured.basePath = Config.getConfigProperty("base.path");
        RestAssured.baseURI = Config.getConfigProperty("host");
        given().
            log().all().
            contentType("application/json").
            formParam("api_key","GNMVTYOYe6CaqnioSATXZPYd6t2nT7riRpE6ypwu").
            formParam("ev_network","ChargePoint Network").
            formParam("location","Austin, TX").
        when().
            get("/nearest.json").
        then().
            statusCode(200);
  }

}

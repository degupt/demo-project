package com.qa.demo.api;

import org.testng.annotations.Test;

import com.qa.demo.restassured.FuelStationManager;

/**
 * Rest assured basic functionality tests. WIP
 * 
 */
public class RestAssuredTest {

    /**
     * Test To get the rest response using jersey and check the status.
     * 
     */
    @Test
    public void nearestStationTest() {
        FuelStationManager stationManager = new FuelStationManager();
        stationManager.getStationDetails();
    }

}

package com.qa.demo.api;

import java.util.List;

import org.testng.annotations.Test;

import com.qa.demo.utils.config.Config;
import com.qa.demo.utils.reporting.Log;
import com.qa.demo.utils.reporting.SoftAssert;

/**
 * Tests to validate the nearest station address is correct or not.
 * 
 */
public class RestAssuredDemo {
    private SoftAssert softAssert = new SoftAssert();
    private static String stationId = "";
    private final String STATIONLOCATOR = "HYATT AUSTIN";
    private final String VALIDATEMESSAGE = "Validate response code; Valid response code is 200";

    /**
     * Test To validate if the HYATT station exists near the station in the request.
     * 
     */
    @Test
    public void nearestStationTest() {
        boolean isStationExists = false;
        String endPoint = Config.getConfigProperty("nearest.station.endpoint");
        JsonResponseMapper js = new JsonResponseMapper(endPoint);
        List<StationDetails> stationsDetails = js.getNearestStations();
        Log.debug("Status " + Integer.toString(js.getResonse().getStatus()));

        for (StationDetails details : stationsDetails) {
            Log.debug(details.toString());
            if (details.getStationName().equals(STATIONLOCATOR)) {
                isStationExists = true;
                stationId = details.getId();
                break;
            }
        }
        softAssert.assertEquals(js.getResonse().getStatus(), 200, VALIDATEMESSAGE);
        softAssert.assertTrue(isStationExists,
                "Station Exists Test;The Station Name should exist in the response with stationId: " + stationId);
        softAssert.assertAll();
    }

 

}

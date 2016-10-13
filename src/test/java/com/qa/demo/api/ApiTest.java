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
public class ApiTest {
    private SoftAssert softAssert = new SoftAssert();
    private static String stationId;
    private final String STATIONLOCATOR = "HYATT AUSTIN";
    private final String STATIONADDRESS = "208 Barton Springs Rd, Austin, TX, 78704";
    private final String VALIDATEMESSAGE = "Validate response code; Valid response code is 200";

    /**
     * Test To validate if the HYATT station exists near the station in the request. Rest APi Request is made to get the
     * list of nearest station to a city. Validation is made to check if "HYATT AUSTIN" exists in the response.
     * 
     */
    @Test(priority = 1)
    public void nearestStationTest() {
        boolean isStationExists = false;
        String endPoint = Config.getConfigProperty("nearest.station.endpoint");
        JsonResponseMapper js = new JsonResponseMapper(endPoint);
        List<StationDetailsBean> stationsDetails = js.getNearestStationsList();
        Log.debug("Status " + Integer.toString(js.getResonse().getStatus()));

        for (StationDetailsBean details : stationsDetails) {
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

    /**
     * Test to validate if the Address of the station id from nearestStationTest is correct or not. API request is made
     * to get the details of the station id passed in the request body. Validation is made to check if the address is
     * correct or not.
     * 
     */
    @Test(dependsOnMethods = "nearestStationTest")
    public void validatestationAddressTest() {
        String endPoint = Config.getConfigProperty("station.endpoint");
        String endP = endPoint.replace("STATION_ID", stationId);
        JsonResponseMapper js = new JsonResponseMapper(endP);
        softAssert.assertEquals(js.getResonse().getStatus(), 200, VALIDATEMESSAGE);
        StationDetailsBean stationsDetails = js.getStationDetails();
        StringBuffer sb = new StringBuffer();
        Log.info("station address: " + sb.append(stationsDetails.getStreetAddress()).append(", ")
                .append(stationsDetails.getCity()).append(", ").append(stationsDetails.getState()).append(", ")
                .append(stationsDetails.getZip()).toString());

        softAssert.assertEquals(sb.toString(), STATIONADDRESS,
                "Station Address Test;The Station Address should be correct EXPECTED: " + STATIONADDRESS + " Actual: "
                        + sb.toString());
        softAssert.assertAll();
    }

}

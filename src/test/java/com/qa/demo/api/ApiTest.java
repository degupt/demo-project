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
    private SoftAssert sa = new SoftAssert();
    private String stationId = "";
    private final String STATIONLOCATOR = "HYATT AUSTIN";
    private final String STATIONADDRESS = "208 Barton Springs Rd, Austin, TX, 78704";

    /**
     * Test To validate if the HYATT station exists near the station in the request.
     * 
     */
    @Test(priority = 1)
    public void nearestStationTest() {
        boolean isStationExists = false;
        String endPoint = Config.getProperty("nearest.station.endpoint");
        JsonResponseMapper js = new JsonResponseMapper(endPoint);
        List<StationDetails> stationsDetails = js.getNearestStations();

        Log.debug("Status" + Integer.toString(js.getResonse().getStatus()));

        for (StationDetails details : stationsDetails) {
            Log.debug(details.toString());
            if (details.getStationName().equals(STATIONLOCATOR)) {
                isStationExists = true;
                stationId = details.getId();
                break;
            }
        }
        sa.assertEquals(js.getResonse().getStatus(), 200, "Validate response code; Valid response code is 200");
        sa.assertTrue(isStationExists,
                "Station Exists Test;The Station Name should exist in the response with stationId: " + stationId);
        sa.assertAll();
    }

    /**
     * Test to validate if the Address of the station id from nearestStationTest is correct or not.
     * 
     */
    @Test(dependsOnMethods = "nearestStationTest")
    public void stationAddressTest() {
        String endPoint = Config.getProperty("station.endpoint");
        String endP = endPoint.replace("STATION_ID", stationId);
        JsonResponseMapper js = new JsonResponseMapper(endP);
        sa.assertEquals(js.getResonse().getStatus(), 200, "Validate response code; Valid response code is 200");
        StationDetails stationsDetails = js.getStationDetails();
        StringBuffer sb = new StringBuffer();
        Log.info("station address " + sb.append(stationsDetails.getStreetAddress()).append(", ")
                .append(stationsDetails.getCity()).append(", ").append(stationsDetails.getState()).append(", ")
                .append(stationsDetails.getZip()).toString());

        sa.assertEquals(sb.toString(), STATIONADDRESS,
                "Station Address Test;The Station Address should be correct EXPECTED: " + STATIONADDRESS + " Actual: "
                        + sb.toString());
        sa.assertAll();
    }

}

package com.qa.demo.practice;

import java.util.Map;

import org.testng.annotations.Test;

import com.qa.demo.utils.common.Data;
import com.qa.demo.utils.common.TestNGUtilities;

/**
 * Tests to validate the nearest station address is correct or not.
 * 
 */
public class DataProviderTest {

    private static final String fileName = "src/test/java/com/qa/demo/practice/data/testData.csv";
    private static final int startRow = 0;
    private static final int endRow = 1; 

    /**
     * Test To validate if the HYATT station exists near the station in the request.
     * 
     */
    @Data(fileName = fileName, startRow = startRow, endRow = endRow)
    @Test(enabled = true, dataProvider = "csvAnnotatedData", dataProviderClass = TestNGUtilities.class)
    public void dataProviderTesting(Map<String, String> map) {
        System.out.println("values" + map.get("value1"));
    }
    
    @Test(priority = 1, dataProvider = "csvData", dataProviderClass = TestNGUtilities.class)
    public void dataProviderTest(Map<String, String> map) {
        System.out.println("values" + map.get("value1"));
    }
    
}

package com.qa.demo.restassured;

import java.util.Enumeration;
import java.util.Properties;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.demo.utils.reporting.Log;
import com.qa.demo.utils.reporting.SoftAssert;

import io.restassured.RestAssured;

/**
 * This Class will be extended in each of the test manage class to initialize api configurations
 * 
 * @author Deenesh
 */
public class BaseTestApi {
    protected ITestContext context;
    protected SoftAssert softAssert;
    private String host;
    private String port;
    private String basePath;

    /**
     * Constructor to initialize the rest endpoint details
     * 
     * @param host
     * @param port
     * @param basePath
     */
    public BaseTestApi(String host, String port, String basePath) {
        this.host = host;
        this.port = port;
        this.basePath = basePath;
        initializeApiConfigs();
    }

    public BaseTestApi() {
        // no-op
    }

    /**
     * This method is invoked at the beginning of each test and sets up the browser instance.
     * 
     * @param context
     */
    public void initializeApiConfigs() {
        Log.info("Setting up initial configurations");

        if (port != null) {
            RestAssured.port = Integer.valueOf(port);
        }

        if (basePath != null) {
            RestAssured.basePath = basePath;
        }

        if (host != null) {
            RestAssured.baseURI = host;
        }

    }

    /**
     * After every Test method the tear down method is called to close the session.
     * 
     * @param result
     * @param context
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        Properties p = System.getProperties();
        Enumeration<Object> keys = p.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = (String) p.get(key);
            System.out.println(key + ": " + value);
        }
    }

    @Test
    public void run() {
        System.out.println("print");
    }

}

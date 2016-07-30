package com.qa.demo.utils.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.testng.Reporter;
import com.qa.demo.utils.reporting.Log;

/**
 * This class is used to load common configuration properties. The properties are set to defaults in the class.
 * 
 * @author Deenesh
 */
public class Config {
    private static Properties prop;
    private static final String DEFAULTFILE = "test.properties";

    private Config() {
        // no-op constructor
    }

    /**
     * Initialize all property values.
     * 
     */
    private static synchronized void initConfig() {
        prop = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String fileName = classLoader.getResource(DEFAULTFILE).getPath();
        try (InputStream input = new FileInputStream(fileName)) {
            prop.load(input);
        } catch (Exception ex) {
            Reporter.log("unable to find the property file");
            Log.error("unable to load properties", ex);
        }
    }

    /**
     * Returns a configuration property value based off the input key
     * 
     * @param key
     * @return property value
     */
    public static String getProperty(String key) {
        if (prop == null) {
            initConfig();
        }
        return prop.getProperty(key);
    }

}
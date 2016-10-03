package com.qa.demo.utils.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.testng.Reporter;

import com.qa.demo.utils.reporting.Log;

/**
 * Class to initialize the properties. Default properties are defined in the {enum} which can be overwritten via system
 * properties or test.propetries file
 * 
 * @author deenesh
 */
public class Config {

    private static Properties configProperties;
    private static final String OVERRIDE_PREFIX = "API_TEST_";
    private static final String DEFAULTFILE = "test.properties";

    private static Properties getConfig() {
        if (configProperties == null) {
            initConfig();
        }
        return configProperties;
    }

    private static Properties getAllProperties() {
        Properties prop = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String fileName = classLoader.getResource(DEFAULTFILE).getPath();
        Log.info("file"+ fileName);
        try (InputStream input = new FileInputStream(fileName)) {
            prop.load(input);
        } catch (IOException ex) {
            Reporter.log("unable to find the property file");
            Log.error("unable to find the property file" + ex);
        }
        return prop;
    }

    /**
     * method to initialize properties
     */
    private static synchronized void initConfig() {
        configProperties = new Properties();

        // Use defaults
        ConfigProperty[] configProps = ConfigProperty.values();
        for (int i = 0; i < configProps.length; i++) {
            configProperties.put(configProps[i].getPropName(), configProps[i].getPropDefaultValue());
        }

        // Read the test properties file.
        Map<Object, Object> userConfig = new HashMap<>();
        try {
            userConfig = getAllProperties();
        } catch (Exception e) {
            Log.warn("We could not read the config file test.properties. Only default properties are available", e);
        }

        // Load the test properties values if found.
        if (!userConfig.isEmpty()) {
            for (Object key : userConfig.keySet()) {
                configProperties.put(key, userConfig.get(key));
            }
        }

        // Load in environment variables (if defined)
        for (int i = 0; i < configProps.length; i++) {
            String value = System.getenv(OVERRIDE_PREFIX + configProps[i].getPropName());
            if (value != null) {
                configProperties.put(configProps[i].getPropName(), value);
            }
        }

        // Load in System properties variables (if defined)
        for (int i = 0; i < configProps.length; i++) {
            String value = System.getProperty(OVERRIDE_PREFIX + configProps[i].getPropName());
            if (value != null) {
                configProperties.put(configProps[i].getPropName(), value);
            }
        }
    }

    /**
     * Method to return the default property value for the enum defined properties
     * 
     * @param property
     * @return
     */
    public static String getConfigProperty(ConfigProperty property) {
        if (configProperties == null && property != null) {
            getConfig();
        }
        return (String) Config.configProperties.get(property.getPropName());
    }

    /**
     * return the property values as defined in the properties file
     * 
     * @param key
     * @return
     */
    public static String getConfigProperty(String key) {
        if (configProperties == null && key != null) {
            getConfig();
        }
        return (String) configProperties.get(key);
    }

    /**
     * set enum properties
     *
     * @param property
     * @param value
     */
    public static void setConfigProperty(ConfigProperty property, String value) {
        if (null != value) {
            Config.getConfig().put(property.getPropName(), value);
        }
    }

    /**
     * default properties read first during property initialization
     * 
     */
    public enum ConfigProperty {

        USER_NAME("user.name", "deenesh.gupta"),
        PASSWORD("password", "testAssess12!"),
        NEAREST_STATION_API("nearest.station.endpoint",
                "https://developer.nrel.gov/api/alt-fuel-stations/v1/nearest.json?api_key=GNMVTYOYe6CaqnioSATXZPYd6t2nT7riRpE6ypwu&ev_network=ChargePoint%20Network&location=Austin,%20TX"),
        STATION_API("station.endpoint",
                "https://developer.nrel.gov/api/alt-fuel-stations/v1/STATION_ID.json?api_key=GNMVTYOYe6CaqnioSATXZPYd6t2nT7riRpE6ypwu"),;

        private String propName = null;
        private String propDefaultValue = null;

        /**
         * Constructor for this enum class.
         * 
         */
        private ConfigProperty(String prop, String defaultValue) {
            this.propName = prop;
            this.propDefaultValue = defaultValue;
        }

        /**
         * Returns the name of this configuration property
         * 
         * @return The name of this configuration property
         */
        public String getPropName() {
            return this.propName;
        }

        /**
         * Returns the default value for this configuration property
         * 
         * @return property value
         */
        public String getPropDefaultValue() {
            return this.propDefaultValue;
        }
    }
}
package com.qa.demo.utils.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.qa.demo.utils.reporting.Log;

/**
 * Class to read the properties file and create Properties object. We can read list of properties file or one file.
 * 
 * @author deenesh
 */
public class PropertiesReader {

    private Properties configProperties;
    private String fileName;
    private List<String> filesList;
    private static final String ERROR_MESSAGE = "Unable to load the property file: ";

    /**
     * Constructor to initialize the list of fileNames to be loaded in the properties
     * 
     * @param files
     */
    public PropertiesReader(List<String> files) {
        this.filesList = files;
    }

    /**
     * Constructor to initialize the fileName variable to be loaded in the properties
     * 
     * @param fileName
     */
    public PropertiesReader(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Initialize properties object
     * 
     */
    public void setProperties() {
        if (!StringUtils.isBlank(this.fileName)) {
            this.configProperties = loadPropertiesFile(fileName);
        } else {
            this.configProperties = loadPropertiesFiles(filesList);
        }
    }

    /**
     * Getter to return the properties object
     * 
     * @return {@link Properties}
     */
    public Properties getProperties() {
        if (configProperties == null) {
            setProperties();
        }
        return configProperties;
    }

    private Properties loadPropertiesFile(String fileName) {
        Properties prop = new Properties();
        if (StringUtils.isBlank(fileName)) {
            throw new PropertyConfigurationException("FileName cannot be null or empty");
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String filePath = classLoader.getResource(fileName).getPath();
        Log.info("File is found at location: " + filePath);
        if (StringUtils.isBlank(filePath)) {
            throw new PropertyConfigurationException("Not able to locate the given property file: " + fileName);
        }
        try (InputStream input = new FileInputStream(filePath)) {
            prop.load(input);
        } catch (IOException ex) {
            Log.error(ERROR_MESSAGE + filePath + ex);
            throw new PropertyConfigurationException(ERROR_MESSAGE + fileName, ex);
        }
        return prop;
    }

    private Properties loadPropertiesFiles(List<String> filesList) {
        Properties prop = new Properties();
        if (filesList != null && !filesList.isEmpty()) {
            throw new PropertyConfigurationException("Files List cannot be null or empty");
        }

        for (String file : filesList) {
            Map<Object, Object> userConfig = new HashMap<>();
            try {
                userConfig = loadPropertiesFile(file);
            } catch (Exception e) {
                Log.warn("We could not read the config file " + fileName, e);
            }

            // Load the test properties values if found.
            if (!userConfig.isEmpty()) {
                for (Map.Entry<Object, Object> entry : userConfig.entrySet()) {
                    configProperties.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return prop;
    }

    /**
     * return the property values as defined in the properties file
     * 
     * @param key
     * @return value for the input property
     */
    public String getConfigProperty(String key) {
        String propertyValue = null;
        if (configProperties == null && key != null) {
            getProperties();
        }
        if (configProperties != null) {
            propertyValue = (String) configProperties.get(key);
        }
        return propertyValue;
    }

}
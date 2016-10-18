package com.qa.demo.utils.db;

/**
 * Custom Exception Class to throw exceptions in the Browser instances.
 * 
 * @author deenesh
 *
 */
@SuppressWarnings("serial")
public class DBConfigurationException extends RuntimeException {

    public DBConfigurationException() {
        // no op default constructor
    }

    /**
     * Constructor thats accepts string message.
     * 
     * @param message
     */
    public DBConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructor thats accepts throwable exception
     * 
     * @param exception
     *            to be thrown.
     */
    public DBConfigurationException(Throwable e) {
        super(e);
    }

    /**
     * Constructor thats accepts throwable exception
     * 
     * @param message
     *            to be shown on the report
     * @param exception
     *            message to be thrown
     */
    public DBConfigurationException(String message, Throwable e) {
        super(message, e);
    }
}

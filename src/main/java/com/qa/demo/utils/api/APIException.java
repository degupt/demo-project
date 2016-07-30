package com.qa.demo.utils.api;

/**
 * Custom Exception Class to throw exceptions in the API modules.
 * 
 * @author deeneshgupta
 *
 */
@SuppressWarnings("serial")
public class APIException extends RuntimeException {

    public APIException() {
        // no op default constructor
    }

    /**
     * Constructor thats accepts string message.
     * 
     * @param message
     */
    public APIException(String message) {
        super(message);
    }

    /**
     * Constructor thats accepts throwable exception
     * 
     * @param exception
     *            to be thrown.
     */
    public APIException(Throwable e) {
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
    public APIException(String message, Throwable e) {
        super(message, e);
    }
}

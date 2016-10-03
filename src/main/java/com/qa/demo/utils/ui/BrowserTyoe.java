package com.qa.demo.utils.ui;

/**
 * BrowserType enum to restrict the usage of browsers to limited few within the test framework, to avoid user errors.
 * 
 */
public enum BrowserTyoe {

    IE("iexplorer"),
    FIREFOX("firefox"),
    CHROME("chrome");

    private final String browser;

    /**
     * Constructor for this enum class.
     * 
     * @param String
     *            browser initialize the browser string
     */
    BrowserTyoe(String browser) {
        this.browser = browser;
    }

    /**
     * Returns the name of this configuration property
     * 
     * @return The name of the browser
     */
    public String getBrowserString() {
        return this.browser;
    }

}

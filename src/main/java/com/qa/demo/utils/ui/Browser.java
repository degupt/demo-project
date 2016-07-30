package com.qa.demo.utils.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qa.demo.utils.reporting.Log;

/**
 * Implementation of the WebDriver related functions and actions to be performed on browser.
 * 
 * @author Deenesh
 */
public class Browser {
    protected WebDriver driver;
    private String bType;

    /**
     * Creates the Browser constructor, which will set the webDriver. we have to pass the browser type in the
     * constructor.
     *
     * @param browserType
     *            pass the browser type to the Browser instance.
     */
    public Browser(String browserType) {
        this.bType = browserType;
        initializeDriver(bType);
    }

    public Browser() {
        // no-op
    }

    private void initializeDriver(String bType) {
        Log.debug("Creating an instance of a SeleniumBrowser." + bType);
        switch (bType) {
        case "firefox":
            this.driver = new FirefoxDriver();
            break;
        case "chrome":
            this.driver = new ChromeDriver();
            break;
        default:
            this.driver = new FirefoxDriver();
        }
    }

    /**
     * Returns WebDriver it to the user
     * 
     * @return driver instance
     */
    public WebDriver getWebDriver() {
        return driver;
    }

    /**
     * Closes the browser in use
     */
    public void closeBrowser() {
        getWebDriver().close();
    }

    /**
     * quit the browser in use
     */
    public void quitBrowser() {
        getWebDriver().quit();
    }

    /**
     * maximize the browser in use
     */
    public void maximize() {
        getWebDriver().manage().window().maximize();
    }

    /**
     * get title of the browser page
     */
    public String getTitle() {
        return getWebDriver().getTitle();
    }

}

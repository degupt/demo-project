package com.qa.demo.utils.ui;

import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qa.demo.utils.config.Config;
import com.qa.demo.utils.reporting.Log;

/**
 * Implementation of the WebDriver related functions and actions to be performed on browser.
 * 
 */
public class Browser {
    private WebDriver webDriver;
    private BrowserTyoe bType;
    private String browserVersion;

    /**
     * Creates the Driver constructor
     * 
     * @param browserType
     * @param browserVersion
     */
    public Browser(String browserType, String browserVersion) {
        this.bType = getBrowserType(browserType);
        this.browserVersion = browserVersion;
    }

    public Browser() {
        // no-op
    }

    /**
     * Returns WebDriver it to the user
     * 
     * @return {WebDriver}
     */
    public WebDriver getWebDriver() {
        if (webDriver == null) {
            initializeDriver(bType, browserVersion);
        }
        return webDriver;
    }

    /***
     * Creates the browser object.
     *
     * @param browserType
     *            BrowserVersion BrowserType object specifying what type of browser to open
     * 
     */
    private void initializeDriver(BrowserTyoe bType, String bVersion) {
        Log.debug("Creating an instance of a SeleniumBrowser." + bType.toString());
        DesiredCapabilities capabilities = setCapabilities(bType, bVersion);
        webDriver = createDriver(bType, capabilities);
    }

    /***
     * Set any Internet Explorer specific capabilities
     * 
     * @return IE specific capabilities
     */
    private DesiredCapabilities setIECapabilities() {
        DesiredCapabilities capabilities;
        capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        return capabilities;
    }

    /***
     * Set any Firefox specific capabilities
     * 
     * @return Firefox specific capabilities
     */
    private DesiredCapabilities setFirefoxCapabilities() {
        DesiredCapabilities capabilities;
        capabilities = DesiredCapabilities.firefox();
        try {
            FirefoxProfile profile = new FirefoxProfile();
            capabilities.setCapability(FirefoxDriver.PROFILE, profile);
        } catch (Exception e) {
            Log.warn("Property 'firefox Profile' is not available in framework.properties files." + e);
        }
        return capabilities;
    }

    /***
     * Sets the capabilities that will be used when creating the WebDriver instance.
     * 
     * @param {BrowserTyoe}
     *            Type of browser to create.
     * @param {String}
     *            browserVersion - Type of browser to create.
     * @return An instance of DesiredCapabilities populated based on browser type and framework.properties
     */
    private DesiredCapabilities setCapabilities(BrowserTyoe browserType, String browserVersion) {
        DesiredCapabilities capabilities;

        // Set capabilities to browser type and browser specific capabilities
        switch (browserType) {
        case IE:
            capabilities = setIECapabilities();
            break;
        case FIREFOX:
            Log.debug("setting firefox caps");
            capabilities = setFirefoxCapabilities();
            break;
        case CHROME:
            Log.debug("setting chrome caps");
            capabilities = DesiredCapabilities.chrome();
            break;
        default:
            capabilities = setFirefoxCapabilities();
        }

        // Set Browser Version
        if ((browserVersion != null) && !(browserVersion.isEmpty())) {
            Log.info("Setting browser version:" + browserVersion);
            capabilities.setVersion(browserVersion);
        }

        // Set Platform if grid is enabled
        if (("true").equalsIgnoreCase(Config.getConfigProperty("grid.enabled"))) {
            if (Config.getConfigProperty("grid.platform").equalsIgnoreCase(PlatformType.WINDOWS.getPlatformString())) {
                capabilities.setPlatform(Platform.WINDOWS);
            } else if (Config.getConfigProperty("grid.platform")
                    .equalsIgnoreCase(PlatformType.LINUX.getPlatformString())) {
                capabilities.setPlatform(Platform.LINUX);
            }
        }
        return capabilities;
    }

    /***
     * Creates an instance of WebDriver to pass to Selenium. Will create a RemoteWebDriver if the grid.enabled property
     * is set to true, otherwise it will create a local instance of the WebDriver corresponding with the browserType.
     * 
     * @param {BrowserTyoe}
     *            Type of browser to create
     * @param {DesiredCapabilities}
     *            DesiredCapabilities of the browser
     * @return {WebDriver} instance of the browser to pass to Selenium
     */
    private WebDriver createDriver(BrowserTyoe browserType, DesiredCapabilities capabilities) {
        if ("true".equalsIgnoreCase(Config.getConfigProperty("grid.enabled"))) {
            try {
                this.webDriver = new RemoteWebDriver(new URL(Config.getConfigProperty("grid.url")), capabilities);
                Log.info(((RemoteWebDriver) webDriver).getSessionId() + " running on Thread:  "
                        + Thread.currentThread().getId());
            } catch (Exception ex) {
                String errorMessage = "Unable to initialize remote webdriver";
                Log.error(errorMessage + ex);
                throw new WebDriverException(errorMessage, ex);
            }
        } else {
            Log.info("Using local WebDriver instance: " + browserType.getBrowserString());
            switch (browserType) {
            case IE:
                webDriver = new InternetExplorerDriver(capabilities);
                break;
            case FIREFOX:
                webDriver = new FirefoxDriver(capabilities);
                break;
            case CHROME:
                webDriver = new ChromeDriver(capabilities);
                break;
            default:
                webDriver = new FirefoxDriver(capabilities);
            }
        }
        return webDriver;
    }

    /**
     * Converts the sting browserType to enum type
     * 
     * @param bType
     * @return {BrowserType}
     */
    public static BrowserTyoe getBrowserType(String bType) {
        BrowserTyoe browserType;
        switch (bType) {
        case "iexplorer":
            browserType = BrowserTyoe.IE;
            break;
        case "firefox":
            browserType = BrowserTyoe.FIREFOX;
            break;
        case "chrome":
            browserType = BrowserTyoe.CHROME;
            break;
        default:
            browserType = BrowserTyoe.FIREFOX;
        }
        return browserType;
    }

    /***
     * Closes the browser in use
     */
    public void closeBrowser() {
        webDriver.close();
    }

    /***
     * quit the browser in use
     */
    public void quitBrowser() {
        webDriver.quit();
    }

    /***
     * maximize the browser instance
     */
    public void maximize() {
        webDriver.manage().window().maximize();
    }

    /***
     * get page source of the browser instance
     */
    public void pageSource() {
        webDriver.getPageSource();
    }

    public String getTitle() {
        return webDriver.getTitle();
    }

    public void getHandle() {
        webDriver.getWindowHandle();
    }

    /**
     * enum to restrict the usage of platforms in test automation framework.
     * 
     * @author deenesh
     */
    public enum PlatformType {
        LINUX("linux"),
        WINDOWS("windows");

        private final String platformValue;

        PlatformType(String platformString) {
            platformValue = platformString;
        }

        public String getPlatformString() {
            return platformValue;
        }
    }

}

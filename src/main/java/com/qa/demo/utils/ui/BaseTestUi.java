package com.qa.demo.utils.ui;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.qa.demo.ui.HomePage;
import com.qa.demo.utils.config.Config;
import com.qa.demo.utils.reporting.Log;
import com.qa.demo.utils.reporting.SoftAssert;

/**
 * This Class will be extended in each of the test class to initialize webDriver and open the URL The TestNG annotation
 * is before and after methods.
 * 
 * @author Deenesh
 */
public class BaseTestUi {
    protected ITestContext context;
    protected SoftAssert softAssert;
    private Browser browser;
    protected HomePage homePage;

    /**
     * This method is invoked at the beginning of each test and sets up the browser instance.
     * 
     * @param context
     */
    @BeforeMethod(alwaysRun = true)
    public void initBrowser(ITestContext context) {
        Log.info("inside before method");
        String url = Config.getProperty("url");
        String browserType = Config.getProperty("browser.type");
        softAssert = new SoftAssert();
        context.setAttribute("url", url);
        context.setAttribute("BrowserType", browserType);
        browser = new Browser(browserType);
        homePage = new HomePage(browser.getWebDriver()).openURL(url);
    }

    /**
     * After every Test method the tear down method is called to close the session.
     * 
     * @param result
     * @param context
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        browser.quitBrowser();
    }

}

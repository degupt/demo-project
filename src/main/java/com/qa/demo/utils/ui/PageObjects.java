package com.qa.demo.utils.ui;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import com.qa.demo.utils.config.Config;
import com.qa.demo.utils.reporting.Log;

/**
 * Implementation of the WebDriver related functions and actions to be performed on a WebElement. This class is to be
 * extended in all the page objects
 * 
 * @author deenesh
 */
public class PageObjects {
    protected WebDriver driver;
    private long explicitWaitTime = Long.parseLong(Config.getProperty("explicit.wait.time"));
    private FluentWait<WebDriver> wait;

    /**
     * 
     * @param driver
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public PageObjects(WebDriver driver) {
        this.driver = driver;
        if (this.wait == null) {
            this.wait = new FluentWait(driver).withTimeout(explicitWaitTime, TimeUnit.SECONDS)
                    .pollingEvery(1, TimeUnit.SECONDS).ignoring(StaleElementReferenceException.class);
        }
    }

    /**
     * Get the title of the web page.
     * 
     * @return The title of the page, if found. If not found, an empty string
     */
    public String getTitle() {
        String value = driver.getTitle();
        return (null == value) ? "" : value;
    }

    /**
     * @param webElement
     *            The {@link WebElement} element is present and found.
     * @return A {@link Boolean} value
     * @throws Exception
     */
    public boolean isElementPresent(WebElement webElement) {
        Boolean isAvailable = false;
        try {
            if (wait.until(ExpectedConditions.visibilityOf(webElement)).isDisplayed()
                    && wait.until(ExpectedConditions.visibilityOf(webElement)).isEnabled()) {
                isAvailable = true;
            }
        } catch (Exception e) {
            Log.error("element not available ", e);
            // Throw exception as there is no point of further execution
            throw new NoSuchElementException("Unable to find element", e);
        }
        return isAvailable;
    }

    /**
     * Click the webElement once it is visible
     * 
     * @param element
     *            The {@link WebElement} element to click.
     */
    public void click(WebElement element) {
        if (isElementPresent(element)) {
            element.click();
            waitForPageToLoad(20);
        }
    }

    /**
     * Input the text when the element is visible
     * 
     * @param element
     *            The {@link WebElement} element to use.
     * @param text
     *            The text to type.
     */
    public void inputText(WebElement element, String text) {
        if (isElementPresent(element)) {
            if (text == null || text.isEmpty())
                Log.error("the search input is null");
            else {
                clearText(element);
                element.sendKeys(text);
            }
        }
    }

    /**
     * clear the text of the element of input type
     * 
     * @param element
     *            The {@link WebElement} element to use.
     */
    public void clearText(WebElement element) {
        if (isElementPresent(element)) {
            element.clear();
        }
    }

    /**
     * Return the text of the element
     * 
     * @param element
     *            The {@link WebElement} element to use.
     * @return The text to retrieve.
     */
    public String getText(WebElement element) {
        String value = null;
        if (isElementPresent(element)) {
            value = element.getText();
        }
        return (value == null) ? "" : value;
    }

    /**
     * Return the text of the element
     * 
     * @param element
     *            The {@link WebElement} element to use.
     * @return The text to retrieve.
     */
    public String getInputText(WebElement element) {
        String value = null;
        if (isElementPresent(element)) {
            value = element.getAttribute("value");
        }
        return (value == null) ? "" : value;
    }

    /**
     * method to hove the mouse to the element passes as parameter
     * 
     * @param element
     *            to which the mouse needs to be hovered
     */
    public void mouseOver(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
    }

    /**
     * method to select the element in a drop down based on visible text
     * 
     * @param webElement
     * @param text
     */
    public void selectByText(WebElement webElement, String text) {
        Select oSelect = new Select(webElement);
        oSelect.selectByVisibleText(text);
    }

    /**
     * Wait for the web page to load by checking the page ready state. Sub classes can override this to verify that a
     * page has completed loading based on other means, such as a loading icon is not present.
     */
    public void waitForPageToLoad() {
        waitForPageToLoad(25);
    }

    /**
     * Wait for the Page to load by looping x times and checking after every 1 second if the page is ready.
     * 
     * @param numLoops
     *            the number of times loop.
     */
    public void waitForPageToLoad(int numLoops) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 0; i < numLoops; i++) {
            Log.info("Waiting for Page to Load");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                Log.error("unable to load the page" + e);
            }

            // To check page ready state.
            if (("complete").equals(js.executeScript("return document.readyState").toString())) {
                break;
            }
        }
    }

}
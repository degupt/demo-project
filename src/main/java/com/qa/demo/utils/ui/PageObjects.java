package com.qa.demo.utils.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
 *
 */
public class PageObjects {
    private WebDriver driver;
    private long explicitWaitTime = Long.parseLong(Config.getConfigProperty("explicit.wait.time"));
    private FluentWait<WebDriver> wait;
    private static final String ERRORMESSAGE = "Web Element not available ";

    /**
     * Constructor to initialize WebDriver
     * 
     * @param driver
     *            The {@link WebDriver} element to use.
     */
    public PageObjects(WebDriver driver) {
        this.driver = driver;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void initializeWait() {
        if (this.wait == null) {
            Log.info("initialising wait");
            this.wait = new FluentWait(driver).withTimeout(explicitWaitTime, TimeUnit.SECONDS)
                    .pollingEvery(1, TimeUnit.SECONDS).ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchElementException.class);
        }
    }

    /**
     * return the webDriver.
     * 
     * @return WebDriver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Get the title of the web page.
     * 
     * @return The title of the page, if found. If not found, an empty string
     */
    public String getTitle() {
        String value = driver.getTitle();
        return (value == null) ? "" : value; // empty value instead of null
    }

    /**
     * @param webElement
     *            The {@link WebElement} element is present and found.
     * @return A {@link Boolean} value
     * @throws Exception
     */
    public boolean isElementPresent(WebElement webElement) {
        Boolean isAvailable = false;
        if (this.wait == null) {
            initializeWait();
        }
        try {
            if (wait.until(ExpectedConditions.visibilityOf(webElement)).isDisplayed()
                    && wait.until(ExpectedConditions.visibilityOf(webElement)).isEnabled()) {
                isAvailable = true;
            }
        } catch (Exception e) {
            Log.error(ERRORMESSAGE + e);
            throw new NoSuchElementException(ERRORMESSAGE + e);
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
     * @throws Exception
     */
    public void inputText(WebElement element, String text) {
        if (isElementPresent(element)) {
            if (text == null || text.isEmpty())
                Log.error("the search input is null");
            else {
                element.clear();
                element.sendKeys(text);
            }
        }
    }

    /**
     * Input non-printable keys to a visible element. Ex: Keys.BACKS_PACE or Keys.ENTER
     * 
     * @param element
     *            The {@link WebElement} element to use.
     * @param k
     *            the key to input
     */
    public void inputKey(WebElement element, Keys k) {
        if (isElementPresent(element)) {
            element.sendKeys(k);
        }
    }

    /**
     * @param element
     *            The {@link WebElement} element to use.
     */
    public void hitEnter(WebElement element) {
        if (isElementPresent(element)) {
            element.sendKeys(Keys.ENTER);
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
     * Return the desired attribute of the element
     * 
     * @param element
     *            The {@link WebElement} element to use.
     * @param attribute
     *            value of attribute to be returned
     * @return The value for the attribute passed as arg2.
     */
    public String getAttribute(WebElement element, String attribute) {
        String value = null;
        if (isElementPresent(element)) {
            value = element.getAttribute(attribute);
        }
        return (value == null) ? "" : value;
    }

    /**
     * Return the desired CSS value of the element
     * 
     * @param element
     *            The {@link WebElement} element to use.
     * @param attribute
     *            value of attribute to be returned
     * @return The value for the css value for param passed as arg2
     */
    public String getCSSValues(WebElement element, String attribute) {

        String value = null;
        if (isElementPresent(element)) {
            value = element.getCssValue(attribute);
        }
        return (value == null) ? "" : value;

    }

    /**
     * Return the tag of the element
     * 
     * @param element
     *            The {@link WebElement} element to use.
     * @return The value for the tag
     */
    public String getTagName(WebElement element) {
        String value = null;
        if (isElementPresent(element)) {
            value = element.getTagName();
        }
        return (value == null) ? "" : value;

    }

    /**
     * Return the boolean value for the element to be enabled or not.
     * 
     * @param element
     *            The {@link WebElement} element to use.
     * @return boolean value for the element to be eanbled or not
     */
    public Boolean isEnabled(WebElement element) {
        if (isElementPresent(element)) {
            return element.isEnabled();
        }
        return false;
    }

    /**
     * Return the boolean value for the element to be displayed or not.
     * 
     * @param element
     *            The {@link WebElement} element to use.
     * @return boolean value for the element to be displayed or not
     */
    public Boolean isDisplayed(WebElement element) {
        if (isElementPresent(element)) {
            return element.isDisplayed();
        }
        return false;
    }

    /**
     * Return the boolean value for the element to be selected or not.
     * 
     * @param element
     *            The {@link WebElement} element to use.
     * @return boolean value for the element to be selected or not
     */
    public Boolean isSelected(WebElement element) {
        if (isElementPresent(element)) {
            return element.isSelected();
        }
        return false;
    }

    /**
     * Return the String value for the selected element in list of radio button or checkboxes.
     * 
     * @param listElements
     *            The {@link WebElement} element to use. List of String values for the selected elements in drop down
     * @return
     */
    public List<String> getSelectedValues(List<WebElement> listElements) {
        boolean selected;
        List<String> selectedValues = new ArrayList<>();
        if (listElements != null) {
            for (int i = 0; i < listElements.size(); i++) {
                selected = listElements.get(i).isSelected();
                if (selected) {
                    selectedValues.add(listElements.get(i).getAttribute("value"));
                }
            }
        }
        return selectedValues;
    }

    /**
     * Select a single radio button or checkboxes provided in the input String param.
     * 
     * @param listElements
     * @param sValue
     */
    public void selectBox(List<WebElement> listElements, String sValue) {
        if (listElements != null && sValue != null) {
            for (int i = 0; i < listElements.size(); i++) {
                String labelValue = listElements.get(i).getAttribute("value");
                if (labelValue.equalsIgnoreCase(sValue)) {
                    listElements.get(i).click();
                    break;
                }
            }
        }
    }

    /**
     * Select the radio button or checkboxes provided in the list param.
     * 
     * @param listElements
     *            The {@link WebElement} element to use.
     * @param sValues
     */
    public void selectBoxes(List<WebElement> listElements, List<String> sValues) {
        if (listElements != null && sValues != null) {
            for (int i = 0; i < listElements.size(); i++) {
                String labelValue = listElements.get(i).getAttribute("value");
                for (int j = 0; j < sValues.size(); j++) {
                    if (labelValue.equalsIgnoreCase(sValues.get(j))) {
                        listElements.get(i).click();
                        break;
                    }
                }
            }
        } else {
            Log.error("Unable to select values from down");
        }
    }

    /**
     * Select the value from a drop down based on the visible text.
     * 
     * @param webElement
     *            The {@link WebElement} element to use.
     * @param text
     *            The Visible Text to be selected
     */
    public void selectByText(WebElement webElement, String text) {
        if (isElementPresent(webElement) && text != null) {
            Select oSelect = new Select(webElement);
            oSelect.selectByVisibleText(text);
        }
    }

    /**
     * Method to return the text of first selected value from the drop down
     * 
     * @param webElement
     *            WebElement to select the drop down
     * @return value of the first option from drop down
     */
    public String getSelectedText(WebElement webElement) {
        String value = null;
        if (isElementPresent(webElement)) {
            Select oSelect = new Select(webElement);
            value = oSelect.getFirstSelectedOption().getText();
        }
        return (value == null) ? "" : value;
    }

    /**
     * Select the value from a drop down based on the visible text.
     * 
     * @param webElement
     *            The {@link WebElement} element to use.
     * @param index
     *            The index to be selected
     */
    public void selectByIndex(WebElement webElement, int index) {
        if (isElementPresent(webElement)) {
            Select oSelect = new Select(webElement);
            oSelect.selectByIndex(index);
        }
    }

    /**
     * Select the value from a drop down based on the visible text.
     * 
     * @param webElement
     *            The {@link WebElement} element to use.
     * @param value
     *            The Value to be selected
     */
    public void selectByValue(WebElement webElement, String value) {
        if (isElementPresent(webElement)) {
            Select oSelect = new Select(webElement);
            oSelect.selectByValue(value);
        } else
            Log.error("The WebElement to select is not available");
    }

    /**
     * Select the value from a drop down based on the visible text.
     * 
     * @param webElement
     *            The {@link WebElement} element to use.
     * @return List<WebElement> The list of selected web elements
     */
    public List<WebElement> getDropDownList(WebElement webElement) {
        List<WebElement> dropDownList = new ArrayList<>();
        if (isElementPresent(webElement)) {
            Select oSelect = new Select(webElement);
            dropDownList = oSelect.getOptions();
        }
        return dropDownList;
    }

    /**
     * Select the value from a drop down based on the visible text.
     * 
     * @param webElement
     *            The {@link WebElement} element to use.
     * @return List of values of selected webelements.
     */
    public List<String> printDropDownList(WebElement webElement) {
        List<String> values = new ArrayList<>();
        List<WebElement> dropDownList = new ArrayList<>();
        if (isElementPresent(webElement)) {
            Select oSelect = new Select(webElement);
            dropDownList = oSelect.getOptions();
        }
        for (WebElement element : dropDownList) {
            values.add(element.getText());
        }
        return values;
    }

    /**
     * De-Select the value from a multi-select box based on the visible text.
     * 
     * @param webElement
     *            The {@link WebElement} element to use.
     * @param text
     *            The Visible Text to be selected
     */

    public void deselectByText(WebElement webElement, String text) {
        if (isElementPresent(webElement) || text == null) {
            Select oSelect = new Select(webElement);
            oSelect.deselectByVisibleText(text);
        } else
            Log.info("the check box to de-select by text is not available");
    }

    /**
     * De-Select the value from a multi-select box based on the index.not applicable for dropdowns
     * 
     * @param webElement
     *            The {@link WebElement} element to use.
     * @param index
     *            The index to be selected
     */
    public void deselectByIndex(WebElement webElement, int index) {
        if (isElementPresent(webElement)) {
            Select oSelect = new Select(webElement);
            oSelect.deselectByIndex(index);
        } else
            Log.info("the check box to de-select by index is not available");

    }

    /**
     * De-Select the value from a multi-select box based on the value. not applicable for dropdowns
     * 
     * @param webElement
     *            The {@link WebElement} element to use.
     * @param value
     *            The Value to be selected
     */
    public void deselectByValue(WebElement webElement, String value) {
        if (isElementPresent(webElement) && value != null) {
            Select oSelect = new Select(webElement);
            oSelect.deselectByValue(value);
        }
    }

    /**
     * De-Select the value from a multi-select box based on the value. Not applicable for dropdowns
     * 
     * @param webElement
     *            The {@link WebElement} element to use.
     */
    public void deselectAll(WebElement webElement) {
        if (isElementPresent(webElement)) {
            Select oSelect = new Select(webElement);
            oSelect.deselectAll();
        }
    }

    /**
     * Method to hover the mouse to the element passes as parameter
     * 
     * @param element
     *            to which the mouse needs to be hovered
     */
    public void hoverMouseOver(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
    }

    /**
     * Method to accept the alert
     * 
     */
    public void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    /**
     * Method to dismiss the alert
     * 
     */
    public void dismissAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
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

        // This loop will rotate for 25 times to check If page Is ready after
        // every 1 second.
        // You can replace your value If you wants to Increase or decrease wait
        // time.
        for (int i = 0; i < numLoops; i++) {
            try {
                Log.info("Waiting for Page to Load");
                Thread.sleep(1000);
            } catch (Exception e) {
                Log.error("Waiting for page to load failed", e);
            }
            // To check page ready state.
            if ("complete".equals(js.executeScript("return document.readyState").toString())) {
                break;
            }
        }
    }

}
package com.qa.demo.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.demo.utils.reporting.Log;
import com.qa.demo.utils.ui.PageObjects;

/**
 * This class initialize the objects on the Confirmation page. 
 *      used different locators for demo purposes
 *      
 * @author deenesh
 */
public class ConfirmationPage extends PageObjects {

    @FindBy(css = ".wpsc-purchase-log-transaction-results td:nth-child(4)")
    private WebElement itemTotalText;

    @FindBy(xpath = ".//*[contains(text(), 'Total Shipping')]")
    private WebElement totalText;

    @FindBy(className = "entry-title")
    private WebElement resultText;

    /**
     * This class initialize the confirmation page object
     * 
     * @param driver
     */
    public ConfirmationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * This method gets the total amount of the processed order and then compare it with the sum of shipping cost and
     * item cost
     * 
     * @return boolean true if total amount of the order processed is equal to sum shipping cost and item cost
     */
    public boolean isTotalCorrect() {
        boolean isTotalEqual = false;
        String item = getText(itemTotalText);
        String totalString = getText(totalText);
        Log.debug(totalString.split(":")[1].replace(" $", ""));
        double shipCost = Double.parseDouble(totalString.split(":")[1].replace(" $", "").substring(0, 4));
        double totalCost = Double.parseDouble(totalString.split(":")[2].replace(" $", ""));
        Log.debug("itemCost: " + item.replace("$", ""));
        double itemCost = Double.parseDouble(item.replace("$", ""));
        if ((itemCost + shipCost) == totalCost) {
            isTotalEqual = true;
        }
        return isTotalEqual;
    }

    /**
     * validate if the order is processed.
     * 
     * @return boolean true if order is processed
     */
    public boolean isOrderProcessed() {
        Boolean isProcessed = false;
        Log.info(getTitle());
        if (isElementPresent(resultText) && "Transaction Results | ONLINE STORE".equals(getTitle())) {
            isProcessed = true;
        }
        return isProcessed;
    }

}
package com.qa.demo.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.demo.utils.reporting.Log;
import com.qa.demo.utils.ui.PageObjects;

/**
 * This page class initialize the web elements that are available on the Account Page.
 * 
 * @author deenesh
 *
 */
public class AccountPage extends PageObjects {

    @FindBy(css = "a[href*='edit_profile']")
    private WebElement yourAccountDetailLink;

    @FindBy(id = "wpsc_checkout_form_2")
    private WebElement firstNameInputBox;

    @FindBy(id = "wpsc_checkout_form_3")
    private WebElement lastNameInputBox;

    @FindBy(id = "wpsc_checkout_form_4")
    private WebElement addressInputBox;

    @FindBy(id = "shippingSameBilling")
    private WebElement shippingInfoCheckBox;

    @FindBy(css = ".myaccount input[type='submit']")
    private WebElement saveChangesButton;

    @FindBy(css = ".widget-container a[href*='logout']")
    private WebElement logOutLink;

    /**
     * Constructor to initialize the Account page elements
     * 
     * @param driver
     */
    public AccountPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * This method is to save the updated contact details on the account page.
     * 
     * @param firstname
     *            firstname to be updated
     * @param lastname
     *            lastname to be updated
     * @param addressValue
     *            address to be updated
     * @return AccountPage
     */
    public AccountPage updateContactDetails(String firstname, String lastname, String addressValue) {
        click(yourAccountDetailLink);
        inputText(firstNameInputBox, firstname);
        inputText(lastNameInputBox, lastname);
        inputText(addressInputBox, addressValue);
        click(saveChangesButton);
        return this;
    }

    /**
     * method to navigate to the account page
     * 
     * @return AccountPage
     */
    public AccountPage openContactInfo() {
        Log.info("Clicking Contact details");
        click(yourAccountDetailLink);
        return new AccountPage(getDriver());
    }

    public String getFirstName() {
        return getText(firstNameInputBox);
    }

    public String getLastName() {
        return getText(lastNameInputBox);
    }

    public String getAddress() {
        return getText(addressInputBox);
    }

    /**
     * Method to log out of the account page
     * 
     * @return login page
     */
    public LoginPage logout() {
        click(logOutLink);
        return new LoginPage(getDriver());
    }

}
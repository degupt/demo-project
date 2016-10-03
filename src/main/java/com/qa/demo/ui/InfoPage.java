package com.qa.demo.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.demo.utils.reporting.Log;
import com.qa.demo.utils.ui.PageObjects;

/**
 * This page class initialize the web elements that are available on the buyer Info Page.
 * 
 * @author deenesh
 *
 */
public class InfoPage extends PageObjects {

    @FindBy(id = "current_country")
    private WebElement shipCountryDropdown;

    @FindBy(xpath = ".//*[@id='change_country']/input[4]")
    private WebElement calculateShippingCostButton;

    @FindBy(id = "wpsc_checkout_form_9")
    private WebElement emailInputBox;

    @FindBy(id = "wpsc_checkout_form_2")
    private WebElement firstNameInputBox;

    @FindBy(id = "wpsc_checkout_form_3")
    private WebElement lastNameInputBox;

    @FindBy(id = "wpsc_checkout_form_4")
    private WebElement addressInputBox;

    @FindBy(id = "wpsc_checkout_form_5")
    private WebElement cityInputBox;

    @FindBy(id = "wpsc_checkout_form_6")
    private WebElement stateInputBox;

    @FindBy(id = "wpsc_checkout_form_7")
    private WebElement countryDropDown;

    @FindBy(id = "wpsc_checkout_form_8")
    private WebElement zipInputBox;

    @FindBy(id = "wpsc_checkout_form_18")
    private WebElement phoneInputBox;

    @FindBy(id = "shippingSameBilling")
    private WebElement shipAddressInputBox;

    @FindBy(css = ".total_shipping .checkout-shipping")
    private WebElement shipCostText;

    @FindBy(css = ".total_tax .checkout-shipping")
    private WebElement taxAmountText;

    @FindBy(css = ".total_item .pricedisplay.checkout-shipping")
    private WebElement itemCostText;

    @FindBy(xpath = ".//*[@id='checkout_total']/span")
    private WebElement totalCostText;

    @FindBy(css = ".input-button-buy")
    private WebElement submitOrderButton;

    /**
     * Constructor to initialize the page objects
     *  
     * @param driver  
     */
    public InfoPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * submit order method
     *  
     * @return Confirmation Page object   
     */
    public ConfirmationPage submitOrder() {
        Log.info("Title" + getDriver().getTitle());
        selectByText(shipCountryDropdown, "USA");
        click(calculateShippingCostButton);
        inputText(emailInputBox, "gupta.deenesh@gmail.com");
        inputText(firstNameInputBox, "Deenesh");
        inputText(lastNameInputBox, "Gupta");
        inputText(addressInputBox, "House 123");
        inputText(cityInputBox, "DreamCity");
        inputText(stateInputBox, "CA");
        selectByText(countryDropDown, "USA");
        inputText(zipInputBox, "99999");
        inputText(phoneInputBox, "8133333333");
        click(shipAddressInputBox);
        click(submitOrderButton);
        return new ConfirmationPage(getDriver());
    }

}
package com.qa.demo.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.demo.utils.reporting.Log;
import com.qa.demo.utils.ui.PageObjects;

/**
 * This class initialize the web elements in the cart page object.
 * 
 */
public class CartPage extends PageObjects {
    private static final String EMPTYCARTMESSAGE = "Oops, there is nothing in your cart.";

    @FindBy(css = ".adjustform.remove>input[type=submit]")
    private WebElement removeButton;

    @FindBy(css = ".step2")
    private WebElement continueCheckoutButton;

    @FindBy(css = ".entry-content")
    private WebElement cartMessage;

    /**
     * Constructor to initialize the cart page elements.
     * 
     * @param driver
     */
    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * submit the orders added in the cart
     * 
     * @return InfoPage object
     */
    public InfoPage checkoutCart() {
        click(continueCheckoutButton);
        return new InfoPage(getDriver());
    }

    /**
     * remove the contents from the cart.
     * 
     * @return CartPage
     */
    public CartPage removeItemFromCart() {
        click(removeButton);
        return new CartPage(getDriver());
    }

    /**
     * Method to check if the cart is empty.
     * 
     * @return true if the cart is empty.
     */
    public Boolean iscartEmpty() {
        click(removeButton);
        boolean isEmpty = false;
        Log.info("empty message" + getText(cartMessage));
        if (getText(cartMessage).equals(EMPTYCARTMESSAGE)) {
            isEmpty = true;
        }
        return isEmpty;
    }

}
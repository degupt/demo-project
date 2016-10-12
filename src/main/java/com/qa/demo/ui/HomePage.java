package com.qa.demo.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.demo.utils.ui.PageObjects;

/**
 * This class initialize the objects on the Home page.
 * 
 */
public class HomePage extends PageObjects {

    @FindBy(id = "menu-item-37")
    private WebElement iphoneCategoryButton;

    @FindBy(id = "menu-item-33")
    private WebElement productCategoryButton;
   
    @FindBy(css = "a[href*='sim-free-black']")
    private WebElement blackIphoneLink;

    @FindBy(css = "[action*='free-black'] .wpsc_buy_button")
    private WebElement addBlackIphoneButton;

    @FindBy(css = ".go_to_checkout")
    private WebElement checkoutButton;

    @FindBy(css = ".account_icon")
    private WebElement myAccountButton;

    /**
     * Constructor to initialize the page elements.
     * 
     * @param driver
     */
    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * method to open the url
     * 
     * @param url
     * @return
     */
    public HomePage openURL(String url) {
        getDriver().navigate().to(url);
        return this;
    }

    /**
     * add items in the cart
     * 
     * @return
     * @throws InterruptedException
     */
    public CartPage addToCart() throws InterruptedException {
        hoverMouseOver(productCategoryButton);
        click(iphoneCategoryButton);
        click(addBlackIphoneButton);
        waitForPageToLoad(3);
        click(checkoutButton);
        return new CartPage(getDriver());
    }

    /**
     * method to navigate to the login page
     * 
     * @return login Page
     */
    public LoginPage goToMyAccount() {
        click(myAccountButton);
        return new LoginPage(getDriver());
    }

}
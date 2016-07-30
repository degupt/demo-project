package com.qa.demo.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.demo.utils.ui.PageObjects;

/**
 * This page class initialize the web elements that are available on the Login Page.
 * 
 * @author deenesh
 *
 */
public class LoginPage extends PageObjects {

    @FindBy(id = "log")
    private WebElement usernameInputBox;

    @FindBy(id = "pwd")
    private WebElement passwordInputBox;

    @FindBy(id = "login")
    private WebElement loginButton;

    /**
     * Constructor to initialize the page elements
     * 
     * @param driver
     */
    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * login method to login to the account
     * 
     * @param username
     * @param password
     * @return
     */
    public AccountPage login(String username, String password) {
        inputText(usernameInputBox, username);
        inputText(passwordInputBox, password);
        click(loginButton);
        return new AccountPage(driver);
    }

}
package com.qa.demo.ui;

import org.testng.annotations.Test;

import com.qa.demo.ui.CartPage;
import com.qa.demo.utils.reporting.Log;
import com.qa.demo.utils.ui.BaseTestUi;

/**
 * This class has the test method to validate if the cart gets empty or not once the items in the cart are deleted.
 */
public class EmptyCartTest extends BaseTestUi {

    /**
     * Test method to validate if the cart is empty after adding and deleting items in the cart
     * 
     * @throws InterruptedException
     */
    @Test()
    public void emptyCartTest() throws InterruptedException {
        Log.debug("start Test");
        CartPage cart = homePage.addToCart();
        softAssert.assertTrue(cart.iscartEmpty(), "Is Cart Empty;The empty cart message should be correct");
        softAssert.assertAll();
    }
}

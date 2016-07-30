package com.qa.demo.ui;

import org.testng.annotations.Test;

import com.qa.demo.ui.CartPage;
import com.qa.demo.ui.ConfirmationPage;
import com.qa.demo.ui.InfoPage;
import com.qa.demo.utils.ui.BaseTestUi;

/**
 * Submit Order test to validate if the order is submitted and total amount is correct
 */
public class SubmitOrderTest extends BaseTestUi {

    /**
     * Test to validate if the order sum is correct
     * 
     * @throws InterruptedException
     */
    @Test
    public void orderTest() throws InterruptedException {
        CartPage cart = homePage.addToCart();
        InfoPage info = cart.checkoutCart();
        ConfirmationPage confirm = info.submitOrder();
        softAssert.assertTrue(confirm.isOrderProcessed(), "Validate if order processed;Confirmation Page should be displayed");
        softAssert.assertTrue(confirm.isTotalCorrect(),
                "Validate if total is correct; Ship and Item Cost sum should be equal to Total Cost");
        softAssert.assertAll();
    }
}

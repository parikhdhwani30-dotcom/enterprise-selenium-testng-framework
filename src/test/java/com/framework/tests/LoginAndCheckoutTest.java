package com.framework.tests;

import com.framework.pages.CartPage;
import com.framework.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginAndCheckoutTest extends BaseTest {

    @Test(description = "Verify successful user login with valid credentials")
    public void testValidLogin() {
        ProductsPage productsPage = loginPage.loginAsValidUser("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isLoaded(), "Products page should load upon successful login");
        Assert.assertEquals(productsPage.getTitleText(), "Products");
    }

    @Test(description = "Verify error message is displayed for locked-out user")
    public void testLockedOutUser() {
        loginPage.loginExpectingFailure("locked_out_user", "secret_sauce");
        String error = loginPage.getErrorMessageText();
        Assert.assertTrue(error.contains("Sorry, this user has been locked out."), "Locked out message should display");
    }

    @Test(description = "Verify product can be added to cart and cart counter increments")
    public void testAddProductToCart() {
        ProductsPage productsPage = loginPage.loginAsValidUser("standard_user", "secret_sauce");
        productsPage.addBackpackToCart();
        Assert.assertEquals(productsPage.getCartCount(), "1", "Cart count should be 1");

        CartPage cartPage = productsPage.goToCart();
        Assert.assertEquals(cartPage.getFirstItemName(), "Sauce Labs Backpack");
    }

    @DataProvider(name = "invalidLoginData", parallel = true)
    public Object[][] getInvalidLoginData() {
        return new Object[][]{
                {"invalid_user", "secret_sauce", "Username and password do not match"},
                {"standard_user", "wrong_password", "Username and password do not match"},
                {"", "secret_sauce", "Username is required"},
                {"standard_user", "", "Password is required"}
        };
    }

    @Test(dataProvider = "invalidLoginData", description = "Data-driven test for multiple invalid login attempts")
    public void testInvalidLoginScenarios(String username, String password, String expectedErrorSubstr) {
        loginPage.loginExpectingFailure(username, password);
        String actualError = loginPage.getErrorMessageText();
        Assert.assertTrue(actualError.contains(expectedErrorSubstr), 
                "Expected error substring '" + expectedErrorSubstr + "' but got '" + actualError + "'");
    }
}

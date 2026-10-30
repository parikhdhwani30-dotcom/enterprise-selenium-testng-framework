package com.framework.tests;

import com.framework.pages.CartPage;
import com.framework.pages.ProductsPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Epic("E-Commerce Portal")
@Feature("Authentication & Cart Management")
public class LoginAndCheckoutTest extends BaseTest {

    @Test(description = "Verify successful user login with valid credentials")
    @Severity(SeverityLevel.BLOCKER)
    @Story("User Login")
    public void testValidLogin() {
        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isLoaded(), "Products page should load upon successful login");
        Assert.assertEquals(productsPage.getTitle(), "Products");
    }

    @Test(description = "Verify error message is displayed for locked-out user")
    @Severity(SeverityLevel.NORMAL)
    @Story("User Login - Negative")
    public void testLockedOutUser() {
        loginPage.enterUsername("locked_out_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Sorry, this user has been locked out."), "Locked out message should display");
    }

    @Test(description = "Verify product can be added to cart and cart counter increments")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Cart Checkout Flow")
    public void testAddProductToCart() {
        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");
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
    @Severity(SeverityLevel.MINOR)
    @Story("User Login - Data Driven Validations")
    public void testInvalidLoginScenarios(String username, String password, String expectedErrorSubstr) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        String actualError = loginPage.getErrorMessage();
        Assert.assertTrue(actualError.contains(expectedErrorSubstr),
                "Expected error substring '" + expectedErrorSubstr + "' but got '" + actualError + "'");
    }
}

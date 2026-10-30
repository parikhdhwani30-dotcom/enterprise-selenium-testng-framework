package com.framework.pages;

import com.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private final By cartItemName = By.cssSelector(".inventory_item_name");
    private final By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public String getFirstItemName() {
        return getText(cartItemName);
    }

    public void proceedToCheckout() {
        click(checkoutButton);
    }
}

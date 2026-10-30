package com.framework.pages;

import com.framework.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * CartPage implemented using Selenium PageFactory pattern.
 */
public class CartPage extends BasePage {

    @FindBy(css = ".inventory_item_name")
    private WebElement cartItemName;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public String getFirstItemName() {
        return getText(cartItemName);
    }

    public void clickCheckout() {
        click(checkoutButton);
    }
}

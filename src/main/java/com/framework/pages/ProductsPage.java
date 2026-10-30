package com.framework.pages;

import com.framework.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * ProductsPage implemented using Selenium PageFactory pattern.
 */
public class ProductsPage extends BasePage {

    @FindBy(css = ".title")
    private WebElement titleHeading;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElement addToCartBackpackBtn;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = ".shopping_cart_link")
    private WebElement shoppingCartLink;

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isDisplayed(titleHeading);
    }

    public String getTitle() {
        return getText(titleHeading);
    }

    public void addBackpackToCart() {
        click(addToCartBackpackBtn);
    }

    public String getCartCount() {
        return getText(cartBadge);
    }

    public CartPage goToCart() {
        click(shoppingCartLink);
        return new CartPage(driver);
    }
}

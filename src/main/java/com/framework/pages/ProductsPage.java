package com.framework.pages;

import com.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage extends BasePage {

    // 1. Locators
    private final By titleHeading = By.cssSelector(".title");
    private final By addToCartSauceLabsBackpack = By.id("add-to-cart-sauce-labs-backpack");
    private final By shoppingCartBadge = By.cssSelector(".shopping_cart_badge");
    private final By shoppingCartLink = By.cssSelector(".shopping_cart_link");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    // 2. Actions
    public boolean isLoaded() {
        return isDisplayed(titleHeading);
    }

    public String getTitleText() {
        return getText(titleHeading);
    }

    public void addBackpackToCart() {
        click(addToCartSauceLabsBackpack);
    }

    public String getCartCount() {
        return getText(shoppingCartBadge);
    }

    public CartPage goToCart() {
        click(shoppingCartLink);
        return new CartPage(driver);
    }
}

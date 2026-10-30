package com.framework.pages;

import com.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    // 1. Locators
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // 2. Actions
    public ProductsPage loginAsValidUser(String username, String password) {
        sendKeys(usernameInput, username);
        sendKeys(passwordInput, password);
        click(loginButton);
        return new ProductsPage(driver);
    }

    public void loginExpectingFailure(String username, String password) {
        sendKeys(usernameInput, username);
        sendKeys(passwordInput, password);
        click(loginButton);
    }

    public String getErrorMessageText() {
        return getText(errorMessage);
    }
}

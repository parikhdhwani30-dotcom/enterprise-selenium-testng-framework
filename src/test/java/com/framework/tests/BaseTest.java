package com.framework.tests;

import com.framework.base.DriverFactory;
import com.framework.pages.LoginPage;
import com.framework.utils.SessionStorageUtil;
import com.framework.utils.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.util.Properties;

@Listeners(TestListener.class)
public class BaseTest {

    protected WebDriver driver;
    protected Properties prop;
    protected LoginPage loginPage;

    /**
     * Suite-level setup to perform login once and save session storage,
     * demonstrating enterprise session reuse.
     */
    @BeforeSuite
    public void setupSuiteAuth() {
        Properties config = DriverFactory.initProp();
        WebDriver setupDriver = DriverFactory.initDriver("chrome");
        try {
            setupDriver.get(config.getProperty("url", "https://www.saucedemo.com/"));
            LoginPage setupLogin = new LoginPage(setupDriver);
            setupLogin.loginAs("standard_user", "secret_sauce");
            // Save browser session storage to file
            SessionStorageUtil.saveSessionStorage(setupDriver);
        } finally {
            DriverFactory.quitDriver();
        }
    }

    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        prop = DriverFactory.initProp();
        driver = DriverFactory.initDriver(browser);
        String baseUrl = prop.getProperty("url", "https://www.saucedemo.com/");
        driver.get(baseUrl);
        
        // Restore session storage for instant authentication if needed
        SessionStorageUtil.restoreSessionStorage(driver);
        
        loginPage = new LoginPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}

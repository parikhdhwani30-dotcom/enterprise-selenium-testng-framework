package com.framework.tests;

import com.framework.base.DriverFactory;
import com.framework.pages.LoginPage;
import com.framework.utils.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.util.Properties;

@Listeners(TestListener.class)
public class BaseTest {

    protected WebDriver driver;
    protected Properties prop;
    protected LoginPage loginPage;

    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        prop = DriverFactory.initProp();
        driver = DriverFactory.initDriver(browser);
        String baseUrl = prop.getProperty("url", "https://www.saucedemo.com/");
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}

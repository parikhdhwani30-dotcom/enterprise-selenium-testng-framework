package com.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.framework.base.DriverFactory;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Thread-safe TestNG Listener supporting both Allure and ExtentReports,
 * with automated screenshot capture on pass and fail, and email notifications on suite completion.
 */
public class TestListener implements ITestListener {

    private static final ExtentReports extent = ExtentManager.getInstance();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    private int passCount = 0;
    private int failCount = 0;
    private int skipCount = 0;

    @Attachment(value = "Failure Screenshot", type = "image/png")
    public byte[] saveAllureScreenshot() {
        return ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Passed Screenshot", type = "image/png")
    public byte[] saveAllurePassScreenshot() {
        return ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        passCount++;
        test.get().log(Status.PASS, "Test Passed Successfully");
        try {
            saveAllurePassScreenshot();
        } catch (Exception ignored) {}
    }

    @Override
    public void onTestFailure(ITestResult result) {
        failCount++;
        test.get().log(Status.FAIL, "Test Failed: " + result.getThrowable());
        try {
            saveAllureScreenshot();
            TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();
            String base64Screenshot = ts.getScreenshotAs(OutputType.BASE64);
            test.get().addScreenCaptureFromBase64String("data:image/png;base64," + base64Screenshot, "Failure Screenshot");
        } catch (Exception e) {
            test.get().log(Status.WARNING, "Unable to capture screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        skipCount++;
        test.get().log(Status.SKIP, "Test Skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
        // Send email summary after suite finishes
        EmailUtil.sendExecutionReport(passCount, failCount, skipCount);
    }
}

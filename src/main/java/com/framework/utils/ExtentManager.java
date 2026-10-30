package com.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public class ExtentManager {

    private static ExtentReports extent;

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport.html";
            File reportDir = new File(System.getProperty("user.dir") + "/reports");
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("Enterprise Automation Test Results");
            sparkReporter.config().setReportName("Selenium TestNG Automation Suite");
            sparkReporter.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Framework", "Selenium WebDriver 4 + TestNG");
            extent.setSystemInfo("Execution", "Parallel Execution via ThreadLocal");
            extent.setSystemInfo("Author", "Dhwani Parikh");
        }
        return extent;
    }
}

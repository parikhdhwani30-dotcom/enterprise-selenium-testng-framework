# Enterprise Selenium TestNG Automation Framework
**Author:** Dhwani Parikh  
**Technology Stack:** Java 11+, Selenium WebDriver 4, TestNG, Maven, Page Object Model (POM), ExtentReports 5, Log4j2

---

## 1. Overview
This repository contains a modular, production-ready **UI Test Automation Framework** built using **Selenium WebDriver 4** and **TestNG**. It implements the **Page Object Model (POM)** architecture and leverages **ThreadLocal** for seamless, thread-safe **parallel test execution**.

Target Application under test: [SauceDemo E-Commerce](https://www.saucedemo.com/)

---

## 2. Key Architectural Highlights
- **Thread-Safe Parallel Execution:** Implements `ThreadLocal<WebDriver>` within `DriverFactory` to ensure zero session collision during multi-threaded test runs.
- **Page Object Model (POM):** Clean decoupling of page locators (`By`) from reusable action methods.
- **Data-Driven Testing (DDT):** TestNG `@DataProvider(parallel = true)` for executing data sets concurrently across isolated browser sessions.
- **Centralized Explicit Waits:** `BasePage` encapsulates `WebDriverWait` conditions to eliminate flaky arbitrary sleeps.
- **Interactive ExtentReports 5:** Automated TestNG listener (`TestListener`) that captures and attaches Base64 screenshots directly to HTML reports upon test failure.
- **Cross-Browser Configuration:** Driven via Maven parameters or `testng.xml` parameters (`chrome`, `firefox`, `edge`).

---

## 3. Project Structure
```text
enterprise-selenium-testng-framework/
├── src/
│   ├── main/
│   │   ├── java/com/framework/
│   │   │   ├── base/           # DriverFactory (ThreadLocal) & BasePage (Explicit waits)
│   │   │   ├── pages/          # Page Object classes (LoginPage, ProductsPage, CartPage)
│   │   │   └── utils/          # ExtentManager & TestListener
│   │   └── resources/          # config.properties
│   └── test/
│       ├── java/com/framework/
│       │   └── tests/          # BaseTest & Functional test cases
│       └── resources/
│           └── testng-parallel.xml # TestNG suite with parallel="methods"
├── pom.xml                     # Maven project configuration & dependencies
└── README.md
```

---

## 4. Execution Commands

### Run Parallel Test Suite via Maven
```bash
mvn clean test
```

### Run Cross-Browser via Command Line
```bash
mvn test -Dbrowser=firefox
```

---

## 5. Test Reports
After test execution, open the generated HTML report:
- **Extent Report:** `reports/ExtentReport.html`
- **Surefire Reports:** `target/surefire-reports/index.html`

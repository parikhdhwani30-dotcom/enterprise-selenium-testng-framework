package com.framework.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility to save and re-inject browser SessionStorage and LocalStorage.
 * Enables single-login authentication reuse across tests.
 */
public class SessionStorageUtil {

    private static final String SESSION_FILE = "target/session_auth.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Extracts sessionStorage keys/values from the active browser and saves them to a file.
     */
    public static void saveSessionStorage(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, Object> sessionMap = (Map<String, Object>) js.executeScript(
                "var items = {}; " +
                "for (var i = 0; i < sessionStorage.length; i++) { " +
                "    var key = sessionStorage.key(i); " +
                "    items[key] = sessionStorage.getItem(key); " +
                "} " +
                "return items;"
        );

        if (sessionMap != null) {
            try {
                File file = new File(SESSION_FILE);
                file.getParentFile().mkdirs();
                mapper.writeValue(file, sessionMap);
                System.out.println("SessionStorage saved successfully: " + sessionMap.size() + " entries.");
            } catch (IOException e) {
                System.err.println("Failed to write sessionStorage to file: " + e.getMessage());
            }
        }
    }

    /**
     * Reads saved session data and injects it into the browser's sessionStorage.
     */
    public static void restoreSessionStorage(WebDriver driver) {
        File file = new File(SESSION_FILE);
        if (!file.exists()) {
            return;
        }

        try {
            Map<String, String> sessionMap = mapper.readValue(file, new TypeReference<HashMap<String, String>>() {});
            JavascriptExecutor js = (JavascriptExecutor) driver;
            for (Map.Entry<String, String> entry : sessionMap.entrySet()) {
                js.executeScript("sessionStorage.setItem(arguments[0], arguments[1]);", entry.getKey(), entry.getValue());
            }
            System.out.println("SessionStorage restored successfully.");
        } catch (IOException e) {
            System.err.println("Failed to restore sessionStorage: " + e.getMessage());
        }
    }
}


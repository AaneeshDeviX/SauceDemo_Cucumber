package com.saucedemo.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage());
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) throw new RuntimeException("Property '" + key + "' not found");
        return value.trim();
    }

    public static String getBaseUrl()        { return get("base.url"); }
    public static String getBrowser()        { return get("browser"); }
    public static boolean isHeadless()       { return Boolean.parseBoolean(get("headless")); }
    public static int getImplicitWait()      { return Integer.parseInt(get("implicit.wait")); }
    public static int getExplicitWait()      { return Integer.parseInt(get("explicit.wait")); }
    public static int getPageLoadTimeout()   { return Integer.parseInt(get("page.load.timeout")); }
}

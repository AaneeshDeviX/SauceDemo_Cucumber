package com.saucedemo.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 * Screenshot utility - captures as bytes, Base64, or saves to file.
 */
public class ScreenshotUtil {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOTS_DIR = "screenshots/failures/";

    /**
     * Capture as byte array (for Cucumber scenario.attach).
     */
    public static byte[] captureAsBytes(WebDriver driver) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot bytes: {}", e.getMessage());
            return new byte[0];
        }
    }

    /**
     * Capture as Base64 string (for Extent Report embedding).
     */
    public static String captureAsBase64(WebDriver driver) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            logger.error("Failed to capture Base64 screenshot: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Capture and save to file. Returns the file path.
     */
    public static String captureAndSave(WebDriver driver, String testName) {
        try {
            Files.createDirectories(Paths.get(SCREENSHOTS_DIR));
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String cleanName = testName.replaceAll("[^a-zA-Z0-9_-]", "_");
            String fileName = cleanName + "_" + timestamp + ".png";
            String filePath = SCREENSHOTS_DIR + fileName;

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(srcFile.toPath(), Path.of(filePath));

            logger.info("Screenshot saved: {}", filePath);
            return filePath;
        } catch (IOException e) {
            logger.error("Failed to save screenshot: {}", e.getMessage());
            return null;
        }
    }
}

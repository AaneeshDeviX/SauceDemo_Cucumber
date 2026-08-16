package com.saucedemo.hooks;

import com.saucedemo.config.ConfigReader;
import com.saucedemo.utils.DriverFactory;
import com.saucedemo.utils.ScreenRecorderUtil;
import com.saucedemo.utils.ScreenshotUtil;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Cucumber Hooks for:
 * - Browser setup/teardown
 * - Screen recording (start on each scenario, keep on failure, delete on pass)
 * - Screenshot on every failed step
 * - Screenshot on every step for defect-tagged scenarios
 */
public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);
    private static int stepCounter = 0;

    @Before(order = 0)
    public void startRecording(Scenario scenario) {
        logger.info("══════════════════════════════════════════════");
        logger.info("▶ SCENARIO: {}", scenario.getName());
        logger.info("  Tags: {}", scenario.getSourceTagNames());
        logger.info("══════════════════════════════════════════════");

        // Start screen recording for every scenario
        try {
            ScreenRecorderUtil.startRecording(scenario.getName());
        } catch (Exception e) {
            logger.warn("Screen recording unavailable: {}", e.getMessage());
        }

        stepCounter = 0;
    }

    @Before(order = 1)
    public void setUp(Scenario scenario) {
        DriverFactory.initDriver();
        DriverFactory.getDriver().get(ConfigReader.getBaseUrl());
        logger.info("Browser opened: {}", ConfigReader.getBaseUrl());
    }

    /**
     * After EVERY step: capture screenshot if scenario is tagged @defect or if step failed.
     * This ensures we have visual evidence for every bug.
     */
    @AfterStep
    public void afterStep(Scenario scenario) {
        stepCounter++;

        if (DriverFactory.getDriver() == null) return;

        // Always capture screenshot if scenario is tagged @defect
        boolean isDefectScenario = scenario.getSourceTagNames().stream()
                .anyMatch(tag -> tag.equalsIgnoreCase("@defect"));

        if (isDefectScenario) {
            try {
                byte[] screenshot = ScreenshotUtil.captureAsBytes(DriverFactory.getDriver());
                scenario.attach(screenshot, "image/png",
                        "Step_" + stepCounter + "_" + scenario.getName());
                logger.info("  📸 Step {} screenshot captured (defect scenario)", stepCounter);
            } catch (Exception e) {
                logger.warn("Could not capture step screenshot: {}", e.getMessage());
            }
        }

        // If the scenario has already failed at this step, capture immediately
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ScreenshotUtil.captureAsBytes(DriverFactory.getDriver());
                scenario.attach(screenshot, "image/png",
                        "FAILED_Step_" + stepCounter + "_" + scenario.getName());

                // Also save to file
                ScreenshotUtil.captureAndSave(DriverFactory.getDriver(),
                        "FAILED_" + scenario.getName() + "_step" + stepCounter);

                logger.error("  📸 Failure screenshot captured at step {}", stepCounter);
            } catch (Exception e) {
                logger.warn("Could not capture failure screenshot: {}", e.getMessage());
            }
        }
    }

    @After(order = 1)
    public void captureFailureEvidence(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.error("✘ FAILED: {}", scenario.getName());

            try {
                // Final failure screenshot embedded in report
                byte[] screenshot = ScreenshotUtil.captureAsBytes(DriverFactory.getDriver());
                scenario.attach(screenshot, "image/png",
                        "FINAL_FAILURE_" + scenario.getName());

                // Save to file system
                String path = ScreenshotUtil.captureAndSave(DriverFactory.getDriver(),
                        scenario.getName() + "_FINAL");
                logger.error("  📸 Final failure screenshot: {}", path);

            } catch (Exception e) {
                logger.error("Could not capture final screenshot: {}", e.getMessage());
            }
        } else {
            logger.info("✔ PASSED: {}", scenario.getName());
        }
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        // Stop screen recording
        try {
            if (scenario.isFailed()) {
                // Keep the recording for failed tests
                String recordingPath = ScreenRecorderUtil.stopRecording();
                if (recordingPath != null) {
                    logger.error("  🎥 Screen recording saved: {}", recordingPath);
                    scenario.log("Screen recording saved: " + recordingPath);
                }
            } else {
                // Delete recording for passed tests to save disk space
                ScreenRecorderUtil.stopAndDeleteRecording();
            }
        } catch (Exception e) {
            logger.warn("Screen recording cleanup error: {}", e.getMessage());
        }

        // Close browser
        DriverFactory.quitDriver();

        logger.info("══════════════════════════════════════════════");
        logger.info("  Result: {} | Scenario: {}",
                scenario.isFailed() ? "FAILED ✘" : "PASSED ✔",
                scenario.getName());
        logger.info("══════════════════════════════════════════════\n");
    }
}

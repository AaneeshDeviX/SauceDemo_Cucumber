package com.saucedemo.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

/**
 * Screen Recorder utility using Monte Media Library.
 * Records the entire screen during test execution.
 */
public class ScreenRecorderUtil {

    private static final Logger logger = LogManager.getLogger(ScreenRecorderUtil.class);
    private static final String RECORDINGS_DIR = "recordings";
    private static ScreenRecorder screenRecorder;
    private static String currentRecordingName;

    /**
     * Start recording the screen.
     */
    public static void startRecording(String testName) {
        try {
            Files.createDirectories(Paths.get(RECORDINGS_DIR));

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            currentRecordingName = testName.replaceAll("[^a-zA-Z0-9_-]", "_") + "_" + timestamp;

            File movieFolder = new File(RECORDINGS_DIR);
            GraphicsConfiguration gc = GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration();

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            screenRecorder = new CustomScreenRecorder(
                    gc,
                    new Rectangle(0, 0, screenSize.width, screenSize.height),
                    new Format(MediaTypeKey, FormatKeys.MediaType.FILE, MimeTypeKey, MIME_AVI),
                    new Format(MediaTypeKey, FormatKeys.MediaType.VIDEO,
                            EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            DepthKey, 24,
                            FrameRateKey, Rational.valueOf(15),
                            QualityKey, 0.5f,
                            KeyFrameIntervalKey, 15 * 60),
                    new Format(MediaTypeKey, FormatKeys.MediaType.VIDEO,
                            EncodingKey, "black",
                            FrameRateKey, Rational.valueOf(30)),
                    null,
                    movieFolder,
                    currentRecordingName
            );

            screenRecorder.start();
            logger.info("Screen recording started: {}", currentRecordingName);
        } catch (Exception e) {
            logger.warn("Could not start screen recording: {}", e.getMessage());
        }
    }

    /**
     * Stop recording and return the file path.
     */
    public static String stopRecording() {
        if (screenRecorder == null) return null;

        try {
            screenRecorder.stop();
            List<File> createdFiles = screenRecorder.getCreatedMovieFiles();
            screenRecorder = null;

            if (!createdFiles.isEmpty()) {
                String filePath = createdFiles.get(0).getAbsolutePath();
                logger.info("Screen recording saved: {}", filePath);
                return filePath;
            }
        } catch (Exception e) {
            logger.error("Error stopping screen recording: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Stop and delete recording (for passed tests to save space).
     */
    public static void stopAndDeleteRecording() {
        if (screenRecorder == null) return;

        try {
            screenRecorder.stop();
            List<File> createdFiles = screenRecorder.getCreatedMovieFiles();
            screenRecorder = null;

            for (File file : createdFiles) {
                if (file.exists()) {
                    file.delete();
                    logger.info("Deleted recording for passed test: {}", file.getName());
                }
            }
        } catch (Exception e) {
            logger.error("Error stopping screen recording: {}", e.getMessage());
        }
    }

    /**
     * Custom ScreenRecorder that allows naming the output file.
     */
    private static class CustomScreenRecorder extends ScreenRecorder {

        private final String fileName;

        public CustomScreenRecorder(GraphicsConfiguration cfg, Rectangle captureArea,
                                    Format fileFormat, Format screenFormat,
                                    Format mouseFormat, Format audioFormat,
                                    File movieFolder, String fileName) throws IOException, AWTException {
            super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
            this.fileName = fileName;
        }

        // Constructor matching our usage
        public CustomScreenRecorder(GraphicsConfiguration cfg, Rectangle captureArea,
                                    Format fileFormat, Format screenFormat,
                                    Format mouseFormat,
                                    File movieFolder, String fileName) throws IOException, AWTException {
            super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, null, movieFolder);
            this.fileName = fileName;
        }

        @Override
        protected File createMovieFile(Format fileFormat) throws IOException {
            if (!movieFolder.exists()) {
                movieFolder.mkdirs();
            }
            return new File(movieFolder, fileName + ".avi");
        }
    }
}

package com.bdd_db.libs.utils;

import org.testng.Assert;
import org.testng.Reporter;

import java.util.logging.Logger;

/**
 * <p>
 * A library containing all reporting utilities and methods.
 * </p>
 */
public class ReportLogger {

    // Initialize logger.
    private static final Logger LOGGER = Logger.getLogger(ReportLogger.class.getName());

    /**
     * <p>
     * A constructor to prevent instantiation.
     * </p>
     */
    private ReportLogger() {}

    /**
     * <p>
     * Log a message in the report and console.
     * </p>
     *
     * @param message {@link String} - The message to log.
     */
    public static void logMessage(String message) {
        Reporter.log(message);
        System.out.println(message);
    }

    /**
     * <p>
     * Log a message in the report and console after a new line.
     * </p>
     *
     * @param message {@link String} - The message to log.
     */
    public static void logMessageWithIndent(String message) {
        logMessage("");
        logMessage(message);
    }

    /**
     * <p>
     * Writes a message to the console and the report, and then fails the test.
     * </p>
     *
     * @param message {@link String} - The message to write.
     */
    public static void logSevereMessageThenFail(String message) {
        LOGGER.severe(message);
        logMessage(message);
        Assert.fail(message);
    }
}

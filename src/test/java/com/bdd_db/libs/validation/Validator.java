package com.bdd_db.libs.validation;

import com.bdd_db.libs.utils.ReportLogger;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.EmailValidator;

/**
 * <p>
 * A library containing all methods and utilities that validate or verify something.
 * </p>
 */
public class Validator {

    /**
     * <p>
     * A constructor to prevent instantiation.
     * </p>
     */
    private Validator() {}

    /**
     * <p>
     * Checks to see whether a value is null or empty or blank.
     * </p>
     *
     * @param string {@link String} - The value to check.
     */
    public static void verifyIfStringIsNotEmpty(String string) {
        ReportLogger.logMessageWithIndent("Verifying whether the value, '" + string
                + "' is not blank or empty or null...");
        // Verify whether the string is empty or null or blank.
        if (StringUtils.isNotBlank(string)) {
            ReportLogger.logMessage("<b>...PASS. The value, '" + string + "' is not empty or blank or null as expected.</b>" );
        }
        else {
            ReportLogger.logSevereMessageThenFail("<b>...FAIL. The value, '" + string + "' is empty or blank or null.</b>");
        }
    }

    /**
     * <p>
     * Verifies whether a string is a valid email address.
     * </p>
     *
     * @param string {@link String} - The value to check.
     */
    public static void verifyIfStringIsValidEmail(String string) {
        ReportLogger.logMessageWithIndent("Verifying whether the value, '" + string + "' is a valid email address...");
        // Verify whether a string is a valid email, and store the result in a boolean variable.
        boolean isEmail = EmailValidator.getInstance().isValid(string);
        if(isEmail) {
            ReportLogger.logMessage("<b>...PASS. The value, '" + string + "' is a valid email address.</b>");
        }
        else {
            ReportLogger.logSevereMessageThenFail("<b>...FAIL. The value, '" + string + "' is not a valid email address.</b>");
        }
    }
}

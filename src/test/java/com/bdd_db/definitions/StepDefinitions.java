package com.bdd_db.definitions;

import com.bdd_db.config.PropertiesConfig;
import com.bdd_db.constants.PropertyConstants;
import com.bdd_db.constants.SqlFileConstants;
import com.bdd_db.libs.database.DvdDb;
import com.bdd_db.libs.utils.ReportLogger;
import com.bdd_db.libs.validation.Validator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import java.sql.SQLException;

/**
 * <p>
 *  This class contains all step definitions.
 * </p>
 */
public class StepDefinitions {

    /**
     * Declare all framework variables.
     */
    private DvdDb db;
    private PropertiesConfig propertiesConfig = PropertiesConfig.getInstance();

    /**
     * Test level variables.
     */
    private String countryRevenueSqlQuery, userSegmentQuery = null;
    private JsonArray results = new JsonArray();

    /**
     * Constants.
     */
    private static final String UNITED_STATES = "United States";

    @Given("^a user is connected to the DVD rental PostgreSQL database$")
    public void getDbCredentials() {
        // Initialize the database object.
        this.db = new DvdDb(propertiesConfig.getDbHost(), propertiesConfig.getDbPort(), propertiesConfig.getDbName(),
                propertiesConfig.getDbUsername(), propertiesConfig.getDbPwd());
        try {
            // Connect to the database, and verify that the connection is established.
            this.db.connectToDb();
        }
        catch (SQLException e) {
            ReportLogger.logSevereMessageThenFail(e.getMessage());
        }
    }

    @When("^an SQL query has been developed to get the countries where most revenue is generated$")
    public void getCountryRevenueSqlQuery() {
        // Get the query from the .sql file.
        this.countryRevenueSqlQuery = this.db.getSqlFromSqlFile(SqlFileConstants.SQL_FILE_COUNTRY_REVENUE);
        // Verify that the query string is not empty.
        Assert.assertTrue(StringUtils.isNotEmpty(this.countryRevenueSqlQuery));
    }

    @Then("^execute the country by revenue query$")
    public void executeCountryRevenueQuery() {
        try {
            // Execute the query, and store the result.
            this.results = this.db.executeQuery(this.countryRevenueSqlQuery);
            // Assert that the result data set is not empty.
            Assert.assertTrue(this.results.size() > 0, "No results were returned after the country by "
                    + "revenue SQL query was executed.");
            // Close the database connection.
            this.db.closeDb();
        }
        catch (SQLException e) {
            ReportLogger.logSevereMessageThenFail(e.getMessage());
        }
    }

    @And("^verify that United States is one of the top 3 earners$")
    public void verifyUnitedStatesIsTop3Earner() {
        ReportLogger.logMessageWithIndent("Verifying that '" + UNITED_STATES + "' is one of the top 3 earners..." );
        // Create a flag to verify that the 'United States' country has been found.
        boolean usFlag = false;
        for (int i = 0; i <= 2; i++) {
            // Store the current JSON object.
            JsonObject x = this.results.get(i).getAsJsonObject();
            if (x.get(PropertyConstants.COUNTRY).getAsString().equals(UNITED_STATES)) {
                usFlag = true;
                break;
            }
        }
        if (usFlag) {
            ReportLogger.logMessageWithIndent("...SUCCESSFUL. The country, '" + UNITED_STATES
                    + "' is one of the top 3 earners.");
        }
        else {
            ReportLogger.logSevereMessageThenFail("...FAIL. The country, '" + UNITED_STATES
                    + "' is not on the top 3 earners.");
        }
    }

    @And("^print data pertaining to the top 3 countries that bring in the most revenue$")
    public void printTop3Earners() {
        ReportLogger.logMessageWithIndent("Printing the data pertaining to the top 3 earners by country...");
        for (int i = 0; i <= 2; i++) {
            // Store the current JSON object.
            JsonObject x = this.results.get(i).getAsJsonObject();
            // Store the rank for reporting purposes.
            int rank = i + 1;
            // Print the data.
            ReportLogger.logMessageWithIndent("<b>Rank " + rank + ": </b>" + x.toString());
        }
    }

    @When("^an SQL query has been developed to get all United States users who have paid less than the overall average amount$")
    public void getUserSegmentationSqlQuery() {
        // Get the query from the .sql file.
        this.userSegmentQuery = this.db.getSqlFromSqlFile(SqlFileConstants.SQL_FILE_CUSTOMER_SEGMENT);
        // Verify that the query string is not empty.
        Assert.assertTrue(StringUtils.isNotEmpty(this.userSegmentQuery));
    }

    @And("^execute the user segmentation query$")
    public void executeUserSegmentQuery() {
        try {
            // Execute the query, and store the result.
            this.results = this.db.executeQuery(this.userSegmentQuery);
            // Assert that the result data set is not empty.
            Assert.assertTrue(this.results.size() > 0, "No results were returned after the user segmentation "
                    + "SQL query was executed.");
            // Close the database connection.
            this.db.closeDb();
        }
        catch (SQLException e) {
            ReportLogger.logSevereMessageThenFail(e.getMessage());
        }
    }

    @And("^verify that all user email addresses are not blank or empty$")
    public void verifyResultantEmailAreNotBlankOrEmpty() {
        ReportLogger.logMessageWithIndent("Verifying that all the resultant emails are not empty or blank or null...");
        this.results.forEach(x -> Validator.verifyIfStringIsNotEmpty(x.getAsJsonObject()
                .get(PropertyConstants.EMAIL).getAsString()));
    }

    @And("^verify that all user email addresses are valid$")
    public void verifyResultantEmailAreValidEmails() {
        ReportLogger.logMessageWithIndent("Verifying that all the resultant emails are valid emails "
                + "and are not malformed in any way...");
        this.results.forEach(x -> Validator.verifyIfStringIsValidEmail(x.getAsJsonObject()
                .get(PropertyConstants.EMAIL).getAsString()));
    }
}

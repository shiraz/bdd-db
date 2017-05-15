package com.bdd_db.definitions;

import com.bdd_db.config.PropertiesConfig;
import com.bdd_db.libs.database.DvdDb;
import com.bdd_db.libs.utils.ReportLogger;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

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

    @Given("^that the user can connect to the Postgres database$")
    public void accessDb() {
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
}

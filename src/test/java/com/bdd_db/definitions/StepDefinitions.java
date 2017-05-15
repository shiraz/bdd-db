package com.bdd_db.definitions;

import com.bdd_db.libs.database.DvdDb;
import com.bdd_db.libs.utils.ReportLogger;
import cucumber.api.java.en.Given;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p>
 *  This class contains all step definitions.
 * </p>
 */
public class StepDefinitions {

    /**
     * Declare all test variables.
     */
    private DvdDb db;

    @Given("^that the user has access to the Postgres database$")
    public void accessDb() {
        Properties mavenProps = new Properties();
        InputStream in = StepDefinitions.class.getResourceAsStream("C:\\Workspace\\BDD\\bdd-db\\src\\test\\resources\\maven.properties");
        try {
            mavenProps.load(in);
            ReportLogger.logMessage("host --> " + mavenProps.getProperty("host"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

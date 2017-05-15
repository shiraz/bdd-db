package com.bdd_db.config;

import com.bdd_db.libs.utils.ReportLogger;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesConfig {

    /**
     * Class level variables.
     */
    private Properties mavenProps;

    /**
     * Constants
     */
    private static final String PROPERTIES_FILE_PATH = "/database.properties";
    private static final String PROPERTY_DB_HOST = "host";
    private static final String PROPERTY_DB_NAME = "dbName";
    private static final String PROPERTY_DB_PORT = "dbPort";
    private static final String PROPERTY_DB_PWD = "dbPwd";
    private static final String PROPERTY_DB_USERNAME = "dbUsername";

    /**
     * <p>
     * Constructor to prevent instantiation from other classes.
     * </p>
     */
    private PropertiesConfig() {}

    /**
     * <p>
     * An object to hold the PropertiesConfigHolder instance.
     * </p>
     */
    private static final class PropertiesConfigHolder {
        // A variable to hold the singleton instance of the PropertiesConfig class.
        private static final PropertiesConfig INSTANCE = new PropertiesConfig();
    }

    /**
     * <p>
     * Returns the singleton instance of the PropertiesConfig object.
     * </p>
     *
     * @return {@link PropertiesConfig} - The PropertiesConfig singleton.
     */
    public static PropertiesConfig getInstance() {
        return PropertiesConfigHolder.INSTANCE;
    }

    /**
     * <p>
     * Returns the database host.
     * </p>
     *
     * @return {@link String} - The database host.
     */
    public String getDbHost() {
        return getProperty(PROPERTY_DB_HOST);
    }

    /**
     * <p>
     * Returns the database name.
     * </p>
     *
     * @return {@link String} - The database name.
     */
    public String getDbName() {
        return getProperty(PROPERTY_DB_NAME);
    }

    /**
     * <p>
     * Returns the database port.
     * </p>
     *
     * @return {@link String} - The database port.
     */
    public String getDbPort() {
        return getProperty(PROPERTY_DB_PORT);
    }

    /**
     * <p>
     * Returns the database password.
     * </p>
     *
     * @return {@link String} - The database password.
     */
    public String getDbPwd() {
        return getProperty(PROPERTY_DB_PWD);
    }

    /**
     * <p>
     * Returns the database username.
     * </p>
     *
     * @return {@link String} - The database username.
     */
    public String getDbUsername() {
        return getProperty(PROPERTY_DB_USERNAME);
    }

    /**
     * <p>
     * Returns a property value from the database.properties file.
     * </p>
     *
     * @param propertyName {@link String} - The property name.
     * @return {@link String} - The property value.
     */
    private String getProperty(String propertyName) {
        // Load the properties file.
        this.loadDatabaseProperties();
        // Get the property value.
        return this.mavenProps.getProperty(propertyName);
    }

    /**
     * <p>
     * Loads the "database.properties" file.
     * </p>
     */
    private void loadDatabaseProperties() {
        // Initialize a new properties object.
        this.mavenProps = new Properties();
        // Get the resource as a stream based on the properties file.
        InputStream in = this.getClass().getResourceAsStream(PROPERTIES_FILE_PATH);
        try {
            // Load the properties file.
            if(this.mavenProps.isEmpty()) {
                this.mavenProps.load(in);
            }
        } catch (IOException e) {
            ReportLogger.logSevereMessageThenFail(e.getMessage());
        }
    }

}

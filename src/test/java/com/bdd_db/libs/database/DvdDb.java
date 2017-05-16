package com.bdd_db.libs.database;

import com.bdd_db.libs.utils.JsonUtils;
import com.bdd_db.libs.utils.ReportLogger;
import com.google.gson.JsonArray;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * A class containing all utility methods pertaining to the DVD Rental database.
 * </p>
 */
public class DvdDb {

    /**
     * Instance variables.
     */
    private String host, dbName, dbUsername, dbPwd, port;
    private Connection conn;

    /**
     * Constants.
     */
    private static final String CLASS_NAME_POSTGRES_DRIVER = "org.postgresql.Driver";
    private static final String CONNECTION_STRING = "jdbc:postgresql://%s:%s/%s";
    private static final String INIT_SQL = "SELECT to_json(array_agg(t)) FROM (%s) t";

    /**
     * <p>
     * Constructor.
     * </p>
     *
     * @param host {@link String} - The database host.
     * @param port {@link String} - The database port.
     * @param dbName {@link String} - The database name.
     * @param dbUsername {@link String} - The database username.
     * @param dbPwd {@link String} - The database password.
     */
    public DvdDb(String host, String port, String dbName, String dbUsername, String dbPwd) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.dbUsername = dbUsername;
        this.dbPwd = dbPwd;
    }

    /**
     * <p>
     * Closes the connection to the database.
     * </p>
     *
     * @throws SQLException
     */
    public void closeDb() throws SQLException {
        ReportLogger.logMessageWithIndent("Closing the database connection...");
        this.conn.close();
        if (this.conn.isClosed()) {
            ReportLogger.logMessage("...SUCCESSFUL. The connection has been closed.");
        }
        else {
            ReportLogger.logSevereMessageThenFail("...FAIL. The connection is still open.");
        }
    }

    /**
     * <p>
     * Connects to the Postgres database.
     * </p>
     *
     * @return {@link Boolean} - Returns true if the connection is established. False otherwise.
     * @throws SQLException
     */
    public boolean connectToDb() throws SQLException {
        // Setup the connection string.
        String connectionString = String.format(CONNECTION_STRING, this.host, this.port, this.dbName);
        try {
            Class.forName(CLASS_NAME_POSTGRES_DRIVER);
        }
        catch (ClassNotFoundException e) {
            ReportLogger.logSevereMessageThenFail(e.getMessage());
        }
        ReportLogger.logMessageWithIndent("Connecting to the Postgres database, '" + connectionString + "'...");
        this.conn = DriverManager.getConnection(connectionString, this.dbUsername, this.dbPwd);
        // Check to see if the connection is open.
        boolean isClosed = this.conn.isClosed();
        if (!isClosed) {
            ReportLogger.logMessage("...SUCCESSFUL. The connection to the database has been established.");
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * <p>
     * Executes an SQL query, and returns the results in a JSON array format.
     * </p>
     *
     * @param query {@link String} - The SQL query.
     * @return {@link JsonArray} - The SQL query results.
     * @throws SQLException
     */
    public JsonArray executeQuery(String query) throws SQLException {
        ReportLogger.logMessageWithIndent("Executing the following SQL query in the PostgreSQL database:");
        ReportLogger.logMessage(query);
        // Initialize a new JsonArray.
        JsonArray results = new JsonArray();
        // Embed the query in an aggregate one to return values in JSON format.
        String finalQuery = String.format(INIT_SQL, query);
        // Generate a prepared statement object based on the above query.
        PreparedStatement preparedStatement = this.conn.prepareStatement(finalQuery);
        // Execute the query.
        ResultSet resultSet = preparedStatement.executeQuery();
        // Store the result.
        if (resultSet.next()) {
            String resultString = resultSet.getString(1);
            if(resultString != null) {
                // Convert the string to a JsonArray, and then return it.
                results = JsonUtils.convertStringToJson(resultString).getAsJsonArray();
            }
        }
        ReportLogger.logMessageWithIndent("...SUCCESSFUL. The SQL query was executed.");
        // Return the processed JsonArray.
        return results;
    }

    /**
     * <p>
     * Returns the path containing all the .sql files.
     * </p>
     *
     * @return {@link String} - The path to all .sql files.
     */
    private String getSqlFilesPath() {
        String sqlFilesPath = null;
        // Get the SQL queries path.
        try {
            sqlFilesPath = new File("").getCanonicalPath() + File.separator + "src" + File.separator + "test"
                    + File.separator + "resources" + File.separator + "sql_queries" + File.separator;
        }
        catch (IOException e) {
            ReportLogger.logSevereMessageThenFail(e.getMessage());
        }
        // Return the path.
        return sqlFilesPath;
    }

    /**
     * <p>
     * Loads a .sql file, and returns the query from it.
     * </p>
     *
     * @param sqlFileName {@link String} - The name of the ".sql" file.
     * @return {@link String} - The query from the .sql file.
     */
    public String getSqlFromSqlFile(String sqlFileName) {
        // Initialize a string to store the final query.
        String finalQuery = null;
        try {
            // Get the .sql file path.
            String sqlFilePath = getSqlFilesPath() + sqlFileName + ".sql";
            // Load the file.
            BufferedReader reader = new BufferedReader(new FileReader(sqlFilePath));
            // Initialize a string builder to store the contents.
            StringBuilder sb = new StringBuilder();
            String line;
            // Load each line from the buffer and append it to the string builder instance.
            while ((line = reader.readLine()) != null) sb.append(" ".concat(line));
            // Get the completed query from the StringBuilder object.
            finalQuery = sb.toString();
        }
        catch (Exception e) {
            ReportLogger.logSevereMessageThenFail(e.getMessage());
        }
        // Return the processed query.
        return finalQuery;
    }

}

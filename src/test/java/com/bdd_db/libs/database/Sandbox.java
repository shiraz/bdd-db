package com.bdd_db.libs.database;


import com.bdd_db.libs.utils.ReportLogger;
import com.google.gson.JsonArray;

import java.sql.SQLException;

public class Sandbox {

    public static void main(String[] argv) {
        try {
            DvdDb db = new DvdDb("localhost", "5432", "dvdrental", "postgres", "admin");
            db.connectToDb();
            JsonArray results = db.executeQuery("SELECT * FROM customer WHERE email = 'FEF@gmail.com'");
            String test = db.loadSqlFile("customers_segment");
            results = db.executeQuery(test);
            db.closeDb();
        }
        catch (SQLException e) {
            ReportLogger.logSevereMessageThenFail(e.getMessage());
        }

    }
}

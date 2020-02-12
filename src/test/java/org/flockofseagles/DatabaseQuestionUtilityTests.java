package org.flockofseagles;

import org.junit.Test;

import java.sql.*;

public class DatabaseQuestionUtilityTests {
    @Test
    public void databaseQuestionUtility_createTables_createsTables() {
        DatabaseQuestionUtility db = new DatabaseQuestionUtility();

        db.createTables();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:questions.sqlite");

            ResultSet rs = connection.prepareStatement("SELECT * from question").executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();

            System.out.println(rsmd.getColumnName(1));
            System.out.println(rsmd.getColumnName(2));

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

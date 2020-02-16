package org.flockofseagles;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.*;
import java.util.Random;

public class DatabaseQuestionUtilityTests {

    private static Connection connection = null;

    @Before
    public void setup() {
        System.out.println("running before method");
        getConnection();
    }

    @After
    public void teardown() {
        try {
            DatabaseQuestionUtilityTests.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void databaseQuestionUtility_createTables_createsTables() {
        DatabaseQuestionUtility db = new DatabaseQuestionUtility();

        db.createTables();

        try {

            ResultSet rs = connection.prepareStatement("SELECT * from question").executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();

            assertEquals("question_id", rsmd.getColumnName(1));
            assertEquals("question_string", rsmd.getColumnName(2));
            assertEquals("question_category", rsmd.getColumnName(3));

            rs = connection.prepareStatement("SELECT * from answer").executeQuery();

            rsmd = rs.getMetaData();

            assertEquals("answer_id", rsmd.getColumnName(1));
            assertEquals("answer_string", rsmd.getColumnName(2));

        } catch(SQLException e) {
            System.out.println("error in databaseQuestionUtility_createTables_createsTables");
            e.printStackTrace();
        }
    }

    @Test
    public void databaseQuestionUtility_addQuestion_addsQuestion() {
        DatabaseQuestionUtility db = new DatabaseQuestionUtility();

        db.createTables();

        String randomNumString = Integer.toString(new Random().nextInt());
        int initialRowCount = 0;

        try {

            String sqlStatement = "SELECT COUNT(*) FROM question";

            ResultSet rs = connection.prepareStatement(sqlStatement).executeQuery();

            //Storing the number of results in the initialRowCount variable, for later comparison with the count after adding a new question
            while(rs.next())
                initialRowCount = rs.getInt(1);

            rs.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        try {
            db.addQuestion(randomNumString, new String[4]);

            String sqlStatement = String.format("SELECT * from question WHERE question_string = %s", randomNumString);

            ResultSet rs = connection.prepareStatement(sqlStatement).executeQuery();

            int count = 0;

            while(rs.next())
                count++;

            rs.close();

            assertNotEquals(initialRowCount, count);

        } catch(SQLException e) {
            System.out.println("error in databaseQuestionUtility_addQuestion_addsQuestion");
            e.printStackTrace();
        }
    }

    private void getConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:questions.sqlite");

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}

package org.flockofseagles;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.util.Random;

public class DatabaseQuestionUtilityTests {

    private static Connection connection = null;

    @BeforeEach
    public void setup() {
        getConnection();
        DatabaseQuestionUtility db = new DatabaseQuestionUtility();
        db.addInitialQuestionSets();
        db.createTables();
    }

    @AfterEach
    public void teardown() {
        try {
            DatabaseQuestionUtilityTests.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void cleanup() {

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:questions.sqlite");

        } catch(SQLException e) {
            e.printStackTrace();
        }

        try {
            DatabaseQuestionUtilityTests.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void databaseQuestionUtility_createTables_createsTables() {

        DatabaseQuestionUtility db = new DatabaseQuestionUtility();

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

        var randomAnswerArray = new String[4];

        var rand = new Random();

        for(int i = 0; i < randomAnswerArray.length; i++) {
            randomAnswerArray[i] = Integer.toString(rand.nextInt());
        }

        String randomNumString = Integer.toString(new Random().nextInt());
        int initialRowCount = 0;

        try {

            String sqlStatement = "SELECT COUNT(*) FROM question";

            ResultSet rs = connection.prepareStatement(sqlStatement).executeQuery();

            //Storing the number of results in the initialRowCount variable, for later comparison with the count after adding a new question
            while(rs.next())
                initialRowCount = rs.getInt(1);


        } catch(SQLException e) {
            e.printStackTrace();
        }

        try {
            db.addQuestion(randomNumString, randomAnswerArray);

            String sqlStatement = String.format("SELECT * from question WHERE question_string = %s", randomNumString);

            ResultSet rs = connection.prepareStatement(sqlStatement).executeQuery();

            int count = 0;

            while(rs.next())
                count++;


            assertNotEquals(initialRowCount, count);

        } catch(SQLException e) {
            System.out.println("error in databaseQuestionUtility_addQuestion_addsQuestion");
            e.printStackTrace();
        }
    }

    @Test
    public void databaseQuestionUtility_addQuestion_addsAnswers() {
        DatabaseQuestionUtility db = new DatabaseQuestionUtility();

        var rand = new Random();

        String randomNumString = Integer.toString(rand.nextInt());
        int initialRowCount = 0;

        var randomAnswerArray = new String[4];

        for(int i = 0; i < randomAnswerArray.length; i++) {
            randomAnswerArray[i] = Integer.toString(rand.nextInt());
        }

        try {

            String sqlStatement = "SELECT COUNT(*) FROM question";

            ResultSet rs = connection.prepareStatement(sqlStatement).executeQuery();

            //Storing the number of results in the initialRowCount variable, for later comparison with the count after adding a new question
            while(rs.next())
                initialRowCount = rs.getInt(1);


        } catch(SQLException e) {
            e.printStackTrace();
        }

        try {

            String sqlStatement = "SELECT COUNT(*) FROM answer";

            ResultSet rs = connection.prepareStatement(sqlStatement).executeQuery();

            //Storing the number of results in the initialRowCount variable, for later comparison with the count after adding a new question
            while(rs.next())
                initialRowCount = rs.getInt(1);

        } catch(SQLException e) {
            e.printStackTrace();
        }

        try {
            db.addQuestion(randomNumString, randomAnswerArray);

            String sqlStatement = String.format("SELECT * from answer", randomNumString);

            ResultSet rs = connection.prepareStatement(sqlStatement).executeQuery();

            int count = 0;

            while(rs.next())
                count++;

            assertEquals(initialRowCount + randomAnswerArray.length, count);

        } catch(SQLException e) {
            System.out.println("error in databaseQuestionUtility_addQuestion_addsQuestion");
            e.printStackTrace();
        }
    }

    @Test
    public void databaseQuestionUtility_loadQuestionSet_loadsQuestionsIntoArray() {
        DatabaseQuestionUtility db = new DatabaseQuestionUtility();

        var questionSet = db.loadQuestionSet();

        assertNotEquals(0, questionSet.length);

        for(int i = 0; i < questionSet.length; i++) {
            assertFalse(questionSet[i] == null && questionSet[i].getQuestion() == null && questionSet[i].getQuestion().isEmpty());

            var answersArr = questionSet[i].getPossibleAnswers();

            for(int j = 0; j < answersArr.length; j++) {
                assertFalse(answersArr[j] == null &&answersArr[j].isEmpty());
            }
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

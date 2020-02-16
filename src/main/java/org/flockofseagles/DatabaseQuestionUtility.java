package org.flockofseagles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseQuestionUtility implements QuestionUtility {

    private static Connection connection = null;

    @Override
    public Question[] loadQuestionSet() {
        return new Question[0];
    }

    @Override
    public void addQuestion(final String question, String[] answers) {
        Connection connection = getConnection();

        try {

            String sqlStatement = String.format("INSERT INTO question(question_string, question_category) values('%s', '%s')", question, "multiple choice");

            connection.prepareStatement(sqlStatement).execute();

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTables() {
        Connection connection = getConnection();

        try {
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS question (" +
                    "question_id INTEGER NOT NULL PRIMARY KEY," +
                    "question_string TEXT NOT NULL," +
                    "question_category TEXT NOT NULL)").execute();

            connection.prepareStatement("CREATE TABLE IF NOT EXISTS answer (" +
                    "answer_id INTEGER NOT NULL PRIMARY KEY," +
                    "answer_string TEXT NOT NULL," +
                    "question_id INTEGER NOT NULL," +
                    "FOREIGN KEY(question_id) REFERENCES question(question_id))").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private Connection getConnection() {
        try {
            if(connection == null || connection.isClosed())
                connection = DriverManager.getConnection("jdbc:sqlite:questions.sqlite");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}

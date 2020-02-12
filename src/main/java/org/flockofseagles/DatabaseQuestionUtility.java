package org.flockofseagles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseQuestionUtility implements QuestionUtility {

    @Override
    public Question[] loadQuestionSet() {
        return new Question[0];
    }

    @Override
    public void addQuestion() {

    }

    public void createTables() {
        Connection connection = getConnection();

        try {
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS question (" +
                    "question_id int NOT NULL PRIMARY KEY," +
                    "question_string String NOT NULL)").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:questions.sqlite");

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }
}

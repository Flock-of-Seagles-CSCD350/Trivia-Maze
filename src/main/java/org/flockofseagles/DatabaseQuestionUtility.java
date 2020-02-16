package org.flockofseagles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseQuestionUtility implements QuestionUtility {

    private static Connection connection = null;

    @Override
    public Question[] loadQuestionSet() {
        Connection connection = getConnection();
        var questionSet = new Question[10];

        try {

            String sqlStatement = "SELECT *" +
                                    "FROM question LIMIT 10";



            ResultSet rs = connection.prepareStatement(sqlStatement).executeQuery();

            for(int i = 0; i < questionSet.length && rs.next(); i++) {
                String  questionId = rs.getString(1);
                var answerArr = new String[4];

                sqlStatement = String.format("SELECT answer_string " +
                        "FROM answer WHERE question_id = %s LIMIT 4", questionId);

                ResultSet answerResultSet = connection.prepareStatement(sqlStatement).executeQuery();

                for(int j = 0; j < answerArr.length; j++) {
                    answerArr[j] = answerResultSet.getString(1);
                }

                String answerString = rs.getString(2);
                System.out.println(answerString);
                questionSet[i] = new Question(answerArr, 0, rs.getString(2));
            }



        } catch(SQLException e) {
            e.printStackTrace();
        }

        return questionSet;
    }

    @Override
    public void addQuestion(final String question, String[] answers) {
        Connection connection = getConnection();

        try {

            String sqlStatement = String.format("INSERT INTO question(question_string, question_category) values('%s', '%s')", question, "multiple choice");

            connection.prepareStatement(sqlStatement).execute();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        try {

            String sqlStatement = String.format("SELECT question_id from question WHERE question_string = %s", question);
            int questionId = 0;

            ResultSet rs = connection.prepareStatement(sqlStatement).executeQuery();

            while(rs.next())
                questionId = rs.getInt(1);


            for(int i = 0; i < answers.length; i++) {
                sqlStatement = String.format("INSERT INTO answer(answer_string, question_id) values('%s', '%s')", answers[i], questionId);
                connection.prepareStatement(sqlStatement).execute();
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTables() {
        Connection connection = getConnection();

        try {
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS question (" +
                    "question_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "question_string TEXT NOT NULL," +
                    "question_category TEXT NOT NULL)").execute();

            connection.prepareStatement("CREATE TABLE IF NOT EXISTS answer (" +
                    "answer_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
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

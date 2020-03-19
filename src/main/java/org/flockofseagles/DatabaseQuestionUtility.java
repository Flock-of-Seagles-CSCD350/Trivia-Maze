package org.flockofseagles;

import org.flockofseagles.util.APIQuestionLoader;
import org.sqlite.SQLiteConfig;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

public class DatabaseQuestionUtility implements QuestionUtility {

    private static Connection connection;
    private String dbName;

    public DatabaseQuestionUtility() {
        this.dbName = "questions";
        createTables();
        addInitialQuestionSets();
    }

    public DatabaseQuestionUtility(final String dbName) {
        this.dbName = dbName;
        createTables();
        addInitialQuestionSets();
    }

    @Override
    public List<Question> loadQuestionSet() {
        connection = getConnection();
        var questionSet = new ArrayList<Question>();

        try {

            String sqlStatement = "SELECT *" +
                                  "FROM question";

            ResultSet rs = connection.prepareStatement("SELECT count(*) FROM question").executeQuery();

            int rows = rs.getInt(1);

            rs = connection.prepareStatement(sqlStatement).executeQuery();

            System.out.println("Num rows: " + rows);


            for (int i = 0; rs.next() && i < rows; i++) {
                String questionId = rs.getString(1);
                var answerArr = new String[4];

                String answerSqlStatement = String.format("SELECT answer_string " +
                                                          "FROM answer WHERE question_id = %s LIMIT 4", questionId);

                ResultSet answerResultSet = connection.prepareStatement(answerSqlStatement).executeQuery();

                for (int j = 0; j < answerArr.length && answerResultSet.next(); j++) {
                    String answer = answerResultSet.getString(1);
                    answerArr[j] = answer;
                }

                String questionString = rs.getString(2);

                questionSet.add(new Question(answerArr, 0, questionString));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();

        return questionSet;
    }

    @Override
    public int getQuestionId(String question) throws NoSuchElementException {
        connection = getConnection();

        try {
            String sqlStatement = "SELECT question_id from question WHERE question_string = ?";

            PreparedStatement query = connection.prepareStatement(sqlStatement);
            query.setString(1, question);
            ResultSet rs = query.executeQuery();

            if (rs.next())
                return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();

        }
        throw new NoSuchElementException("question does not exist");
    }

    @Override
    public void addQuestion(final String question, String[] answers) {
        connection = getConnection();

        try {
            String sqlStatement = String.format("SELECT COUNT(*) FROM question WHERE question_string = ?",
                    question);

            PreparedStatement query = connection.prepareStatement(sqlStatement);

            query.setString(1, question);

            if (query.executeQuery().getInt(1) > 0)
                return;

            sqlStatement = String.format("INSERT INTO question(question_string, question_category) values(?, '%s')", "multiple choice");

            query = connection.prepareStatement(sqlStatement);

            query.setString(1, question);

            query.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {

            for (String answer : answers) {
                String sqlStatement = String.format("INSERT INTO answer(answer_string, question_id) values(?, '%s')", getQuestionId(question));
                connection = getConnection();
                PreparedStatement query = connection.prepareStatement(sqlStatement);
                query.setString(1, answer);
                query.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();
    }

    @Override
    public void removeQuestion(String question) {
        connection = getConnection();

        try {
            String sqlStatement = "DELETE FROM question WHERE question_string = ?";

            PreparedStatement query = connection.prepareStatement(sqlStatement);
            query.setString(1, question);
            query.executeUpdate();

            System.out.println("Question removed");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();
    }

    @Override
    public void editQuestion(String oldQuestion, String newQuestion) {
        connection = getConnection();

        try {
            String sqlStatement = "UPDATE question SET question_string = ? WHERE question_string = ?";

            PreparedStatement query = connection.prepareStatement(sqlStatement);
            query.setString(1, newQuestion);
            query.setString(2, oldQuestion);
            query.executeUpdate();

            System.out.println("question changed");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();
    }

    @Override
    public void addAnswer(String question, String answer) {
        connection = getConnection();

        try {
            String sqlStatement = String.format("INSERT INTO answer(answer_string, question_id) values(?, '%s')", getQuestionId(question));
            PreparedStatement query = connection.prepareStatement(sqlStatement);
            query.setString(1, answer);
            query.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();
    }

    @Override
    public void editAnswer(String question, String oldAnswer, String newAnswer) {
        connection = getConnection();

        try {
            String sqlStatement = String.format("UPDATE answer SET answer_string = ? WHERE question_id = '%s' AND answer_string = ?", getQuestionId(question));
            PreparedStatement query = connection.prepareStatement(sqlStatement);

            query.setString(1, newAnswer);
            query.setString(2, oldAnswer);

            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }

        closeConnection();
    }

    @Override
    public void removeAnswer(String question, String answer) {
        connection = getConnection();

        try {
            String sqlStatement = String.format("DELETE FROM answer WHERE question_id = '%s' AND answer_string = ?",
                    getQuestionId(question));
            PreparedStatement query = connection.prepareStatement(sqlStatement);
            query.setString(1, answer);
            query.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();
    }


    public void createTables() {
        Connection connection = getConnection();

        try {
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS question (" +
                                        "question_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                        "question_string TEXT NOT NULL," +
                                        "question_category TEXT NOT NULL)").execute();

            connection.prepareStatement("CREATE TABLE IF NOT EXISTS answer (" +
                                        "answer_string TEXT NOT NULL," +
                                        "question_id INTEGER REFERENCES question(question_id) ON DELETE CASCADE" +
                                        ")").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();
    }

    public void addInitialQuestionSets() {

        new APIQuestionLoader().writeQuestionsToFile();

        Path path = null;
        path = Paths.get("questions.txt");
        List<String> questionSetList = null;

        try {
            questionSetList = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < questionSetList.size() / 5; i++) {
            int questionIndex = i * 5;

            var answersArr = new String[4];

            int k = 0;

            for (int j = questionIndex + 1; j < questionIndex + 5; j++) {
                answersArr[k] = questionSetList.get(j);
                k++;
            }

            this.addQuestion(questionSetList.get(questionIndex), answersArr);
        }

        closeConnection();
    }

    private boolean dbIsEmpty() {
        connection = getConnection();
        try {
            boolean isClosed = connection.prepareStatement("select count(*) from question").executeQuery().getInt(1) <= 0;
            closeConnection();

            return isClosed;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();
        return false;
    }

    private Connection getConnection() {

        Properties connectionProperties;
        String connectionString = String.format("jdbc:sqlite:%s", this.dbName + ".sqlite");
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        connectionProperties = config.toProperties();

        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(connectionString, connectionProperties);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    private void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

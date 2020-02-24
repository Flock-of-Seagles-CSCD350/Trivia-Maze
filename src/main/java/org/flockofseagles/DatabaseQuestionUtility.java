package org.flockofseagles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQuestionUtility implements QuestionUtility {

    private static Connection connection = null;

    @Override
    public List<Question> loadQuestionSet() {
        Connection connection = getConnection();
        var questionSet = new ArrayList<Question>();

        try {

            String sqlStatement = "SELECT *" +
                                    "FROM question LIMIT 100";

            ResultSet rs = connection.prepareStatement(sqlStatement).executeQuery();

            for(int i = 0; rs.next(); i++) {
                String questionId = rs.getString(1);
                var answerArr = new String[4];

                String answerSqlStatement = String.format("SELECT answer_string " +
                        "FROM answer WHERE question_id = %s LIMIT 4", questionId);

                ResultSet answerResultSet = connection.prepareStatement(answerSqlStatement).executeQuery();

                for (int j = 0; j < answerArr.length; j++) {
                    answerResultSet.next();
                    String answer = answerResultSet.getString(1);
                    answerArr[j] = answer;
                }

                String questionString = rs.getString(2);

                questionSet.add( new Question(answerArr, 0, questionString) );
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        closeConnection();

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

            String sqlStatement = String.format("SELECT question_id from question WHERE question_string = '%s'", question);
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

        closeConnection();
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

        closeConnection();
    }

    public void addInitialQuestionSets() {
        ArrayList<String> questionSetList =  new ArrayList<String>();

        questionSetList.add("A carnivorous animal eats flesh, what does a nucivorous animal eat?");
        questionSetList.add("Nuts");
        questionSetList.add("Nothing");
        questionSetList.add("Fruit");
        questionSetList.add("Seaweed");
        questionSetList.add("Where is the train station?");
        questionSetList.add("Wales");
        questionSetList.add("Moldova");
        questionSetList.add("Czech Republic");
        questionSetList.add("Denmark");
        questionSetList.add("When was Adolf Hitler appointed as Chancellor of Germany?");
        questionSetList.add("January 30, 1933");
        questionSetList.add("September 1, 1939");
        questionSetList.add("February 27, 1933");
        questionSetList.add("October 6, 1939");
        questionSetList.add("The novel Jane Eyre was written by what author? ");
        questionSetList.add("Charlotte Bronte");
        questionSetList.add("Emily Bronte");
        questionSetList.add("Jane Austen");
        questionSetList.add("Louisa May Alcott");
        questionSetList.add("This album, now considered to be one of the greatest of all time, was a commercial failure when it was released.");
        questionSetList.add("The Velvet Underground and Nico");
        questionSetList.add("Abbey Road");
        questionSetList.add("Led Zeppelin IV");
        questionSetList.add("Pet Sounds");
        questionSetList.add("Which of these programming languages is a low-level language?");
        questionSetList.add("Assembly");
        questionSetList.add("Python");
        questionSetList.add("C#");
        questionSetList.add("Pascal");
        questionSetList.add("In Super Mario Bros., who informs Mario that the princess is in another castle?");
        questionSetList.add("Toad");
        questionSetList.add("Luigi");
        questionSetList.add("Yoshi");
        questionSetList.add("Bowser");
        questionSetList.add("Who wrote the song You Know You Like It?");
        questionSetList.add("AlunaGeorge");
        questionSetList.add("DJ Snake");
        questionSetList.add("Steve Aoki");
        questionSetList.add("Major Lazer");
        questionSetList.add("Which famous singer was portrayed by actor Kevin Spacey in the 2004 biographical film Beyond the Sea?");
        questionSetList.add("Bobby Darin");
        questionSetList.add("Louis Armstrong");
        questionSetList.add("Frank Sinatra");
        questionSetList.add("Dean Martin");
        questionSetList.add("Which character in the Animal Crossing series uses the phrase zip zoom when talking to the player?");
        questionSetList.add("Scoot");
        questionSetList.add("Drake");
        questionSetList.add("Bill");
        questionSetList.add("Mallary");

        for(int i = 0; i < 10; i++) {
            int questionIndex = i*5;

            var answersArr = new String[4];

            int k = 0;

            for(int j = questionIndex + 1; j < questionIndex + 5; j++) {
                answersArr[k] = questionSetList.get(j);
                k++;
            }

            this.addQuestion(questionSetList.get(questionIndex), answersArr);
        }

        closeConnection();
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

    private void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

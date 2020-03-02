package org.flockofseagles;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DatabaseQuestionUtilityTests {

    private static Connection connection = null;
    DatabaseQuestionUtility db;

    @BeforeAll
    public void init() {
        db = new DatabaseQuestionUtility();
    }

    @BeforeEach
    public void setup() {
        db.addInitialQuestionSets();
        getConnection();
    }

    @AfterEach
    public void teardown() {
        try {
            String sqlStatement = "DELETE FROM answer";

            connection.prepareStatement(sqlStatement).execute();

            sqlStatement = "DELETE FROM question";
            connection.prepareStatement(sqlStatement).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void cleanup() {

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:questions.sqlite");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String sqlStatement = "DELETE FROM answer";

            connection.prepareStatement(sqlStatement).execute();

            sqlStatement = "DELETE FROM question";
            connection.prepareStatement(sqlStatement).execute();

            DatabaseQuestionUtilityTests.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("getQuestionId throws NoSuchElementException on no element")
    public void databaseQuestionUtility_getQuestionId_notExists_throwsException() {
        assertThrows(NoSuchElementException.class, () -> db.getQuestionId("some question that doesnt exist"));
    }

    @Test
    @DisplayName("createTables correctly creates tables")
    public void databaseQuestionUtility_createTables_createsTables() {
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


        } catch (SQLException e) {
            System.out.println("error in databaseQuestionUtility_createTables_createsTables");
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("addQuestion correctly adds a new question")
    public void databaseQuestionUtility_addQuestion_addsQuestion() {

        var randomAnswerArray = new String[4];

        var rand = new Random();

        for (int i = 0; i < randomAnswerArray.length; i++) {
            randomAnswerArray[i] = Integer.toString(rand.nextInt());
        }

        String randomNumString = Integer.toString(new Random().nextInt());
        int initialRowCount = 0;

        try {

            String sqlStatement = "SELECT COUNT(*) FROM question";

            ResultSet rs = connection.prepareStatement(sqlStatement).executeQuery();

            //Storing the number of results in the initialRowCount variable, for later comparison with the count after adding a new
            // question
            while (rs.next()) {
                initialRowCount = rs.getInt(1);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            db.addQuestion(randomNumString, randomAnswerArray);

            String sqlStatement = String.format("SELECT * from question WHERE question_string = %s", randomNumString);

            ResultSet rs = connection.prepareStatement(sqlStatement).executeQuery();

            int count = 0;

            while (rs.next()) {
                count++;
            }


            assertNotEquals(initialRowCount, count);

        } catch (SQLException e) {
            System.out.println("error in databaseQuestionUtility_addQuestion_addsQuestion");
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("removeQuestion removes all answers and question")
    public void databaseQuestionUtility_removeQuestion_removesQuestion() {

        var randomAnswerArray = new String[4];

        var rand = new Random();

        for (int i = 0; i < randomAnswerArray.length; i++) {
            randomAnswerArray[i] = Integer.toString(rand.nextInt());
        }

        String randomNumString = Integer.toString(new Random().nextInt());

        db.addQuestion(randomNumString, randomAnswerArray);
        int id = db.getQuestionId(randomNumString);

        db.removeQuestion(randomNumString);

        assertThrows(NoSuchElementException.class, () -> db.getQuestionId(randomNumString));
        try {
            String sqlStatement = "SELECT COUNT(*) FROM answer WHERE question_id = " + id;
            ResultSet rs = connection.prepareStatement(sqlStatement).executeQuery();
            int count = rs.getInt(1);

            assertEquals(0, count);
        } catch (SQLException e) {
            System.out.println("error in databaseQuestionUtility_removeQuestion_removesQuestion");
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("editQuestion edits question text")
    public void databaseQuestionUtility_editQuestion_editsQuestionText() {

        var randomAnswerArray = new String[4];

        var rand = new Random();

        for (int i = 0; i < randomAnswerArray.length; i++) {
            randomAnswerArray[i] = Integer.toString(rand.nextInt());
        }

        String randomNumString = Integer.toString(new Random().nextInt());

        db.addQuestion(randomNumString, randomAnswerArray);
        db.editQuestion(randomNumString, "new question");

        try {
            String sqlStatement = "SELECT question_string FROM question WHERE question_id = " + db.getQuestionId("new question");
            ResultSet rs = connection.prepareStatement(sqlStatement).executeQuery();

            assertEquals("new question", rs.getString(1));
        } catch (SQLException e) {
            System.out.println("error in databaseQuestionUtility_editQuestion_editsQuestionText");
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("addQuestion adds all passed answers")
    public void databaseQuestionUtility_addQuestion_addsAnswers() {

        var rand = new Random();

        String randomNumString = Integer.toString(rand.nextInt());
        int initialRowCount = 0;

        var randomAnswerArray = new String[4];

        for (int i = 0; i < randomAnswerArray.length; i++) {
            randomAnswerArray[i] = Integer.toString(rand.nextInt());
        }

        try {
            db.addQuestion(randomNumString, randomAnswerArray);

            String sqlStatement = "SELECT COUNT(*) FROM answer WHERE question_id = " + db.getQuestionId(randomNumString);
            ResultSet rs = connection.prepareStatement(sqlStatement).executeQuery();

            int count = rs.getInt(1);

            assertEquals(initialRowCount + randomAnswerArray.length, count);

        } catch (SQLException e) {
            System.out.println("error in databaseQuestionUtility_addQuestion_addsQuestion");
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("addAnswer correctly adds new answer for question")
    public void databaseQuestionUtility_addAnswer_addsAnswerToQuestion() {
        var randomAnswerArray = new String[3];

        var rand = new Random();

        for (int i = 0; i < randomAnswerArray.length; i++) {
            randomAnswerArray[i] = Integer.toString(rand.nextInt());
        }

        String randomNumString = Integer.toString(new Random().nextInt());

        try {
            db.addQuestion(randomNumString, randomAnswerArray);
            db.addAnswer(randomNumString, "answer");

            String sqlStatement = String.format("SELECT * from answer WHERE question_id = '%s'", db.getQuestionId(randomNumString));

            ResultSet rs = connection.prepareStatement(sqlStatement).executeQuery();

            int count = 0;
            while (rs.next()) {
                count++;
            }

            assertEquals(4, count);
        } catch (SQLException e) {
            System.out.println("error in databaseQuestionUtility_addAnswer_addsAnswer");
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("removeAnswer removes specified answer")
    public void databaseQuestionUtility_removeAnswer_removesAnswerFromQuestion() {

        var randomAnswerArray = new String[4];

        var rand = new Random();

        for (int i = 0; i < randomAnswerArray.length; i++) {
            randomAnswerArray[i] = Integer.toString(rand.nextInt());
        }

        String randomNumString = Integer.toString(new Random().nextInt());

        db.addQuestion(randomNumString, randomAnswerArray);
        int id = db.getQuestionId(randomNumString);

        // remove last item in answers
        db.removeAnswer(randomNumString, randomAnswerArray[3]);

        try {
            String sqlStatement = "SELECT COUNT(*) FROM answer WHERE question_id = " + id;
            ResultSet rs = connection.prepareStatement(sqlStatement).executeQuery();
            int count = rs.getInt(1);

            assertEquals(3, count);
        } catch (SQLException e) {
            System.out.println("error in databaseQuestionUtility_removeQuestion_removesQuestion");
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("editAnswer edits answer text")
    public void databaseQuestionUtility_editAnswer_editsAnswerText() {

        var randomAnswerArray = new String[4];

        var rand = new Random();

        for (int i = 0; i < randomAnswerArray.length; i++) {
            randomAnswerArray[i] = Integer.toString(rand.nextInt());
        }

        String randomNumString = Integer.toString(new Random().nextInt());

        db.addQuestion(randomNumString, randomAnswerArray);

        // set 4th item in answers to "new answer"
        db.editAnswer(randomNumString, randomAnswerArray[3], "new answer");

        try {
            String sqlStatement =
                    String.format("SELECT COUNT(*) FROM answer WHERE question_id = '%s' AND answer_string = 'new answer'",
                            db.getQuestionId(randomNumString));
            ResultSet rs = connection.prepareStatement(sqlStatement).executeQuery();

            assertEquals(1, rs.getInt(1));
        } catch (SQLException e) {
            System.out.println("error in databaseQuestionUtility_editQuestion_editsQuestionText");
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("loadQuestionSet properly loads all questions")
    public void databaseQuestionUtility_loadQuestionSet_loadsQuestionsIntoArray() {

        List<Question> questionSet = db.loadQuestionSet();

        questionSet.forEach(question -> {
            assertFalse(question == null && question.getQuestion() == null && question.getQuestion().isEmpty());

            System.out.println(question.getQuestion());

            for (String a : question.getPossibleAnswers())
                assertNotNull(a);

        });
    }

    private void getConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:questions.sqlite");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

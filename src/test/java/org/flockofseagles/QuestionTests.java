package org.flockofseagles;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionTests {

    @Test
    public void question_constructor_setsProperties()
    {
        var answersArr = new String[]{"test", "question", "answers"};
        var question = "test question";
        int correctAnswerIndex = 0;

        var q = new Question(answersArr, correctAnswerIndex, question);

        assertArrayEquals( answersArr, q.getPossibleAnswers());
        assertEquals(question, q.getQuestion());
    }

}

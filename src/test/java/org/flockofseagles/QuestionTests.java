package org.flockofseagles;

import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionTests {

    @Test
    public void question_constructor_setsProperties()
    {
        var answersArr = new String[]{"test", "question", "answers"};
        var question = "test question";
        int correctAnswerIndex = 0;
        var q = new Question(answersArr, correctAnswerIndex, question);
        assertArrayEquals( answersArr, q.getQuestionList());
        assertEquals(correctAnswerIndex, q.getCorrectAnswerIndex());
        assertEquals(question, q.getQuestion());
    }
}

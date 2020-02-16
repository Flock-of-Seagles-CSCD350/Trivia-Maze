package org.flockofseagles;

public interface QuestionUtility {

    Question[] loadQuestionSet();

    void addQuestion(String question, String[] answers);
}

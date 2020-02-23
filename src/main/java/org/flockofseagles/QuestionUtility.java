package org.flockofseagles;

import java.util.List;

public interface QuestionUtility {

    List<Question> loadQuestionSet();

    void addQuestion(String question, String[] answers);
}

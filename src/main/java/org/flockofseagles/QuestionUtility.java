package org.flockofseagles;

import java.util.List;

public interface QuestionUtility {

    List<Question> loadQuestionSet();

	int getQuestionId(String question);

	void addQuestion(String question, String[] answers);

	void removeQuestion(String question);

	void editQuestion(String oldQuestion, String newQuestion);

	void addAnswer(String question, String answer);

	void editAnswer(String question, String oldAnswer, String newAnswer);

	void removeAnswer(String question, String answer);

}

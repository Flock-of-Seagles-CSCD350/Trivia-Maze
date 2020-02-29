package org.flockofseagles;

import lombok.Getter;
import lombok.Setter;

public class Question {

	@Getter
	private String[] possibleAnswers;
	@Getter
	@Setter
	private String   question;

	public Question(final String[] answers, final int correctAnswerIndex, final String question) {
		this.possibleAnswers = answers;
		this.question        = question;
	}

    public String[] getPossibleAnswers() {
        return this.possibleAnswers;
    }

    public String getQuestion() {
        return this.question;
    }
}

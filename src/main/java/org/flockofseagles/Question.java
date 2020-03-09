package org.flockofseagles;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Question implements Serializable {

	@Getter
	private String[] possibleAnswers;
	@Getter
	@Setter
	private String   question;

	public Question(final String[] answers, final int correctAnswerIndex, final String question) {
		this.possibleAnswers = answers;
		this.question        = question;
	}
}

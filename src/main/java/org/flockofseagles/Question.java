package org.flockofseagles;

public class Question {
    private String[] possibleAnswers;
    private int correctAnswerIndex;
    private String question;

    public Question(final String[] answers, final int correctAnswerIndex, final String question) {
        this.possibleAnswers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
        this.question = question;
    }
}

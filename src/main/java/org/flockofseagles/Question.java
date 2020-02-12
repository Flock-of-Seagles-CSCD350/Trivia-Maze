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

    public String[] getQuestionList() {
        return this.possibleAnswers;
    }

    public int getCorrectAnswerIndex() {
        return this.correctAnswerIndex;
    }

    public String getQuestion() {
        return this.question;
    }
}

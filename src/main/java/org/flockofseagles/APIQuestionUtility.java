package org.flockofseagles;

import edu.emory.mathcs.backport.java.util.Arrays;

import java.util.ArrayList;
import java.util.List;

public class APIQuestionUtility implements QuestionUtility {

    private List<Question> questionList;

    public APIQuestionUtility() {
        questionList = new ArrayList<Question>();
    }

    @Override
    public List<Question> loadQuestionSet() {
        return new ArrayList<Question>(questionList);
    }

    @Override
    public int getQuestionId(String question) {
        int id = -1;

        int counter = 0;

        for (Question q : questionList) {
            if (q.getQuestion().equals(question))
                id = counter;
            counter++;
        }

        return id;
    }

    @Override
    public void addQuestion(String question, String[] answers) {
        Question q = new Question(answers, 0, question);
        questionList.add(q);
    }

    @Override
    public void removeQuestion(String question) {
        int questionId = getQuestionId(question);

        if (questionId >= 0)
            questionList.remove(questionId);
        else
            throw new IllegalArgumentException("Invalid question string APIQuestionUtility removeQuestion");
    }

    @Override
    public void editQuestion(String oldQuestion, String newQuestion) {
        int questionId = getQuestionId(oldQuestion);

        if (questionId >= 0)
            questionList.get(questionId).setQuestion(newQuestion);
        else
            throw new IllegalArgumentException("Invalid question string APIQuestionUtility editQuestion");
    }

    @Override
    public void addAnswer(String question, String answer) {
        int questionId = getQuestionId(question);

        if (questionId >= 0) {
            Question q = questionList.get(questionId);
            List<String> answersList = Arrays.asList(q.getPossibleAnswers());
            answersList.add(answer);

            questionList.set(questionId, new Question(answersList.toArray(String[]::new), 0, q.getQuestion()));

        } else
            throw new IllegalArgumentException("Invalid question string APIQuestionUtility addAnswer");
    }

    @Override
    public void editAnswer(String question, String oldAnswer, String newAnswer) {
        int questionId = getQuestionId(question);

        if (questionId >= 0) {
            Question q = questionList.get(questionId);
            List<String> answersList = Arrays.asList(q.getPossibleAnswers());
            int answerIndex = answersList.indexOf(oldAnswer);

            answersList.set(answerIndex, newAnswer);

            questionList.set(questionId, new Question(answersList.toArray(String[]::new), 0, q.getQuestion()));

        } else
            throw new IllegalArgumentException("Invalid question string APIQuestionUtility editAnswer");
    }

    @Override
    public void removeAnswer(String question, String answer) {
        int questionId = getQuestionId(question);

        if (questionId >= 0) {
            Question q = questionList.get(questionId);
            List<String> answersList = Arrays.asList(q.getPossibleAnswers());
            int answerIndex = answersList.indexOf(answer);

            answersList.remove(answerIndex);

            questionList.set(questionId, new Question(answersList.toArray(String[]::new), 0, q.getQuestion()));

        } else
            throw new IllegalArgumentException("Invalid question string APIQuestionUtility editAnswer");
    }
}

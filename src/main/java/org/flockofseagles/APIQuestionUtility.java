package org.flockofseagles;

import edu.emory.mathcs.backport.java.util.Arrays;
import org.json.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class APIQuestionUtility implements QuestionUtility {

    private List<Question> questionList;

    public APIQuestionUtility() {
        questionList = new ArrayList<Question>();
    }

    @Override
    public List<Question> loadQuestionSet() {
        String uri = "https://opentdb.com/api.php?amount=50&category=21&type=multiple";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response =
                null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonObject = new JSONObject(response.body());
            JSONArray responseArray = (JSONArray) jsonObject.get("results");
            responseArray.forEach(item -> {
                JSONObject jsonItem = (JSONObject) item;
                String questionString = jsonItem.getString("question");

                List<String> possibleAnswers = new ArrayList<String>();

                possibleAnswers.add(jsonItem.getString("correct_answer"));

                JSONArray answersArray = jsonItem.getJSONArray("incorrect_answers");

                for (Object o : answersArray)
                    possibleAnswers.add((String) o);

                addQuestion(questionString, possibleAnswers.toArray(String[]::new));
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
            throw new IllegalArgumentException("Invalid question string APIQuestionUtility removeAnswer");
    }
}

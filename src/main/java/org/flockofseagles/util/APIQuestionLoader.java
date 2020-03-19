package org.flockofseagles.util;

import org.apache.commons.text.StringEscapeUtils;
import org.flockofseagles.Question;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class APIQuestionLoader {

    private List<Question> questionList;

    public void writeQuestionsToFile() {
        String uri = "https://opentdb.com/api.php?amount=50&category=21&type=multiple";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Accept", "application/json")
                .header("Content-Type", "text/plain;charset=UTF-8")
                .build();

        HttpResponse<String> response =
                null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonObject = new JSONObject(response.body());
            JSONArray responseArray = (JSONArray) jsonObject.get("results");

            responseArray.forEach(item -> {
                JSONObject jsonItem = (JSONObject) item;

                String questionString = StringEscapeUtils.unescapeHtml4(jsonItem.getString("question")) + "\n";

                List<String> possibleAnswers = new ArrayList<String>();

                possibleAnswers.add(StringEscapeUtils.unescapeHtml4(jsonItem.getString("correct_answer")) + "\n");

                JSONArray answersArray = jsonItem.getJSONArray("incorrect_answers");

                for (Object o : answersArray)
                    possibleAnswers.add(StringEscapeUtils.unescapeHtml4((String) o) + "\n");

                addQuestionToFile(questionString, possibleAnswers);

            });

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addQuestionToFile(String questionText, List<String> possibleAnswers) {

        try {
            FileOutputStream fos = new FileOutputStream("questions.txt", true);
            OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer.append(questionText);

            for (String s : possibleAnswers) {
                writer.append(s);
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package org.flockofseagles;

import org.apiguardian.api.API;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class APIQuestionUtilityTest {

    private APIQuestionUtility apiQuestionUtility;

    @BeforeEach
    void setup() {
        apiQuestionUtility = new APIQuestionUtility();
    }

    @Test
    void loadQuestionSet() {
        List<Question> questionList = apiQuestionUtility.loadQuestionSet();
    }

    @Test
    void getQuestionId() {
    }

    @Test
    void addQuestion() {
    }

    @Test
    void removeQuestion() {
    }

    @Test
    void editQuestion() {
    }

    @Test
    void addAnswer() {
    }

    @Test
    void editAnswer() {
    }

    @Test
    void removeAnswer() {
    }
}
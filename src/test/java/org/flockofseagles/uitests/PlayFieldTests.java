package org.flockofseagles.uitests;

import org.flockofseagles.DatabaseQuestionUtilityTests;
import org.flockofseagles.ui.PlayField;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import org.flockofseagles.DatabaseQuestionUtility;
import org.flockofseagles.Question;

import java.sql.*;
import java.util.Random;

public class PlayFieldTests {

    private Canvas testCanvas;
    private PlayField p;

    @BeforeEach
    public void setup() {
        testCanvas = new Canvas();
        p = new PlayField(testCanvas);
    }

    @Test
    public void playField_getQuestion_returnsQuestion() {
        assertNotNull(p.getQuestion());
        assertNotNull(p.getQuestion().getQuestion());
        assertNotNull(p.getQuestion().getPossibleAnswers());
    }

    @Test
    public void playField_getWall_returnsNull_onIncorrectCoordinates() {
        assertNull(p.getWall(1999, 14444));
    }

    @Test
    public void playField_Constructor_throwsException_onNullArgument() {
        assertThrows(IllegalArgumentException.class, () -> {
            PlayField testField = new PlayField(null);
        });
    }
}

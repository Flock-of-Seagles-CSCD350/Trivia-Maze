package org.flockofseagles.uitests;

import org.flockofseagles.DatabaseQuestionUtilityTests;
import org.flockofseagles.ui.PlayField;
import org.flockofseagles.ui.Wall;
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
        p.initializePlayField();
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

    @Test
    public void playField_mazeIsSolvable_returnsTrue_withTargetCoordinates() {
        assertTrue(p.mazeIsSolvable(8, 8, 8, 8));
    }

    @Test
    public void playField_mazeIsSolvable_returnsFalse_onOutOfBoundsCoordinates() {
        assertFalse(p.mazeIsSolvable(20, 20, 8, 8));
    }

    @Test
    public void playField_mazeIsSolvable_returnsTrue_withStartCoordinates() {
        assertTrue(p.mazeIsSolvable(0, 0, 8, 8));
    }

    @Test
    public void playField_mazeIsSolvable_returnsTrue_withStartCoordinates_blockedWalls() {
        p.getWall(0,1).setLocked(true);
        p.getWall(0,3).setLocked(true);
        p.getWall(1,5).setLocked(true);
        p.getWall(1,7).setLocked(true);
        p.getWall(3,3).setLocked(true);


        assertTrue(p.mazeIsSolvable(0, 0, 8, 8));
    }

    @Test
    public void playField_mazeIsSolvable_returnsFalse_withStartCoordinates_blockedStartingWallPath() {
        p.getWall(0,1).setLocked(true);
        p.getWall(0,3).setLocked(true);
        p.getWall(1,0).setLocked(true);
        p.getWall(1,7).setLocked(true);
        p.getWall(3,3).setLocked(true);


        assertFalse(p.mazeIsSolvable(0, 0, 8, 8));
    }

    @Test
    public void playField_mazeIsSolvable_returnsFalse_withStartCoordinates_blockedMiddleWallPath() {
        p.getWall(0,5).setLocked(true);
        p.getWall(1,5).setLocked(true);
        p.getWall(2,5).setLocked(true);
        p.getWall(3,5).setLocked(true);
        p.getWall(4,5).setLocked(true);
        p.getWall(5,5).setLocked(true);
        p.getWall(6,5).setLocked(true);
        p.getWall(7,5).setLocked(true);
        p.getWall(8,5).setLocked(true);


        assertFalse(p.mazeIsSolvable(0, 0, 8, 8));
    }
}

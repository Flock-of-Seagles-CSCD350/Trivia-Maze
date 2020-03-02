package org.flockofseagles.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import org.flockofseagles.DatabaseQuestionUtility;
import org.flockofseagles.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayField extends GridPane {
    protected Canvas[][] field;
    protected Player player = new Player(0, 0);
    protected Question q;
    private ArrayList<Wall> walls = new ArrayList<>();
    private List<Question> questionList;
    protected boolean correctAnswer = false;


    public PlayField(Canvas canvas) {
        super();

        if (canvas == null)
            throw new IllegalArgumentException("Null canvas object passed into PlayField Constructor");

        DatabaseQuestionUtility db = new DatabaseQuestionUtility();
        questionList = db.loadQuestionSet();

        this.setWidth(canvas.getWidth());
        this.setHeight(canvas.getHeight());
        this.field = initializePlayField();
        setWalls();
    }

    public Canvas[][] initializePlayField() {
        int rows = 9;
        int cols = 9;
        Canvas[][] cArra = new Canvas[rows][cols];
        Canvas canvas;

        //rows
        for (int i = 0; i < rows; i++) {
            //cols
            for (int j = 0; j < cols; j++) {
                canvas = new Canvas(this.getWidth() / rows, this.getHeight() / cols);
                this.add(canvas, j, i, 1, 1);
                cArra[i][j] = canvas;
                cArra[i][j].setUserData("Empty");
            }
        }
        return cArra;
    }

    public void setMaze() {
        Canvas canvas;
        Wall w;
        PowerUp p = new PowerUp();

        //rows
        for (int i = 0; i < this.field.length; i++) {
            //cols
            for (int j = 0; j < this.field.length; j++) {
                if (i == 0 && j == 0) {
                    canvas = this.field[i][j];
                    player.draw(canvas);
                }

                // if current row is an even number
                if ((i % 2) == 0 && j + 1 < this.field.length) {
                    j++;
                    canvas = this.field[i][j];
                    w = getWall(i, j);
                    w.drawVert(canvas);
                } else if ((j % 2) == 0 && (i % 2) == 1) {
                    canvas = this.field[i][j];
                    w = getWall(i, j);
                    w.drawHorz(canvas);
                } else if ((j % 2) == 1 && (i % 2) == 1) {
                    canvas = this.field[i][j];
                    w = getWall(i, j);
                    w.drawMid(canvas);
                } else {
                    canvas = this.field[i][j];
                }
            }
        }

    }

    public void updatePlayer(int i) {
        Canvas canvas = getCanvas(player.xVal, player.yVal);
        Wall w;

        //move up
        if (i == 1) {
            w = getWall(player.xVal - 1, player.yVal);

            if (correctAnswer) {
                w.isPassable = true;
                clearCanvas(canvas);
                canvas = this.field[player.xVal - 2][player.yVal];
                player.draw(canvas);
                player.xVal = player.xVal - 2;
            } else {
                canvas = this.field[player.xVal - 1][player.yVal];
                w.isLocked = true;
                w.drawHorzLocked(canvas);
            }
        } else if (i == 2) {    //move down

            w = getWall(player.xVal + 1, player.yVal);

            if (correctAnswer) {
                w.isPassable = true;
                clearCanvas(canvas);
                canvas = this.field[player.xVal + 2][player.yVal];
                player.draw(canvas);
                player.xVal = player.xVal + 2;
            } else {
                canvas = this.field[player.xVal + 1][player.yVal];
                w.isLocked = true;
                w.drawHorzLocked(canvas);
            }
        } else if (i == 3) { //move left

            w = getWall(player.xVal, player.yVal - 1);

            if (correctAnswer) {
                w.isPassable = true;
                clearCanvas(canvas);
                canvas = this.field[player.xVal][player.yVal - 2];
                player.draw(canvas);
                player.yVal = player.yVal - 2;
            } else {
                canvas = this.field[player.xVal][player.yVal - 1];
                w.isLocked = true;
                w.drawVertLocked(canvas);
            }
        } else if (i == 4) { //move right

            w = getWall(player.xVal, player.yVal + 1);

            if (correctAnswer) {
                w.isPassable = true;
                clearCanvas(canvas);
                canvas = this.field[player.xVal][player.yVal + 2];
                player.draw(canvas);
                player.yVal = player.yVal + 2;
            } else {
                canvas = this.field[player.xVal][player.yVal + 1];
                w.isLocked = true;
                w.drawVertLocked(canvas);
            }
        }
    }

    public void clearCanvas(Canvas canvas) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.setUserData("Empty");
    }

    public Canvas getCanvas(int x, int y) {
        return this.field[x][y];
    }

    public Wall getWall(int x, int y) {
        for (Wall w : walls) {
            if (w.xVal == x && w.yVal == y)
                return w;
        }
        return null;
    }

    private void setWalls() {
        Wall w;

        //rows
        for (int i = 0; i < this.field.length; i++) {
            //cols
            for (int j = 0; j < this.field.length; j++) {

                // if current row is an even number
                if ((i % 2) == 0 && j + 1 < this.field.length) {
                    j++;
                    w = new Wall(i, j);
                    walls.add(w);
                } else if ((j % 2) == 0 && (i % 2) == 1) {
                    w = new Wall(i, j);
                    walls.add(w);
                } else if ((j % 2) == 1 && (i % 2) == 1) {
                    w = new Wall(i, j);
                    walls.add(w);
                }
            }
        }
    }

    public Question getQuestion() {
        return this.questionList.remove(0);
    }

    public int getLength() {
        return this.field.length;
    }

    public boolean mazeIsSolvable(int x, int y, int targetX, int targetY) {
        boolean[][] visited = new boolean[field.length][field.length];
        boolean[][] badPath = new boolean[field.length][field.length];

        for (int i = 0; i < visited.length; i++)
            for (int j = 0; j < visited.length; j++) {
                visited[i][j] = false;
                badPath[i][j] = false;
            }


        return solveMaze(x, y, targetX, targetY, visited, badPath);
    }

    private boolean solveMaze(int x, int y, int targetX, int targetY, boolean[][] visited, boolean[][] badPath) {
        if (x >= field.length || y >= field.length || x < 0 || y < 0)
            return false;
        if (x == targetX && y == targetY)
            return true;

        visited[x][y] = true;

        if (getWall(x - 1, y) != null && !getWall(x - 1, y).isLocked && (x - 2 >= 0) && !badPath[x - 2][y] && !visited[x - 2][y] && solveMaze(x - 2, y, targetX, targetY, visited, badPath))
            return true;
        else if (getWall(x, y + 1) != null && !getWall(x, y + 1).isLocked && (y + 2 < field.length) && !badPath[x][y + 2] && !visited[x][y + 2] && solveMaze(x, y + 2, targetX, targetY, visited, badPath))
            return true;
        else if (getWall(x + 1, y) != null && !getWall(x + 1, y).isLocked && (x + 2 < field.length) && !badPath[x + 2][y] && !visited[x + 2][y] && solveMaze(x + 2, y, targetX, targetY, visited, badPath))
            return true;
        else if (getWall(x, y - 1) != null && !getWall(x, y - 1).isLocked && (y - 2 >= 0) && !badPath[x][y - 2] && !visited[x][y - 2] && solveMaze(x, y - 2, targetX, targetY, visited, badPath))
            return true;
        else {
            badPath[x][y] = true;
            visited[x][y] = false;
            return false;
        }
    }

}

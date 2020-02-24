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
	protected Player player = new Player(0 , 0);
	protected Question q;
	ArrayList<Wall> walls = new ArrayList<>();
	private List<Question> questionList;
	protected boolean correctAnswer = false;


	public PlayField(Canvas canvas) {
		super();

		if(canvas == null)
			throw new IllegalArgumentException("Null canvas object passed into PlayField Constructor");

		DatabaseQuestionUtility db = new DatabaseQuestionUtility();
		db.addInitialQuestionSets();
		questionList = db.loadQuestionSet();

		this.setWidth(canvas.getWidth());
		this.setHeight(canvas.getHeight());
		this.field = initializePlayField();
	}

	public Canvas[][] initializePlayField() {
		int rows = 9;
		int cols = 9;
		Canvas[][] cArra = new Canvas[rows][cols];
		Canvas canvas;

		//rows
		for(int i = 0; i < rows; i++) {
			//cols
			for(int j = 0; j < cols; j++) {
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
		for(int i = 0; i < this.field.length; i++) {
			//cols
			for(int j = 0; j < this.field.length; j++) {
				if(i == 0 && j == 0) {
					canvas = this.field[i][j];
					player.draw(canvas);
				}

				// if current row is an even number
				if((i % 2) == 0 && j + 1 < this.field.length)
				{
					j++;
					canvas = this.field[i][j];
					w = new Wall(i, j);
					walls.add(w);
					w.drawVert(canvas);
				}
				else if((j % 2) == 0 && (i % 2) == 1)
				{
					canvas = this.field[i][j];
					w = new Wall(i, j);
					walls.add(w);
					w.drawHorz(canvas);
				}
				else if((j % 2) == 1 && (i % 2) == 1)
				{
					canvas = this.field[i][j];
					w = new Wall(i,j);
					w.drawMid(canvas);
				}
				else {
					canvas = this.field[i][j];
				}
			}
		}

	}

	public void updatePlayer(int i)
	{
		Canvas canvas = getCanvas(player.xVal, player.yVal);
		Wall w;

		//move up
		if(i == 1)
		{
			w = getWall(player.xVal - 1, player.yVal);

			if(correctAnswer)
			{
				w.isPassable = true;
				clearCanvas(canvas);
				canvas = this.field[player.xVal - 2][player.yVal];
				player.draw(canvas);
				player.xVal = player.xVal - 2;
			}
			else
			{
				canvas = this.field[player.xVal - 1][player.yVal];
				w.isLocked = true;
				w.drawHorzLocked(canvas);
			}
		}

		else if(i == 2)
		{	//move down

			w = getWall(player.xVal + 1, player.yVal);

			if(correctAnswer)
			{
				w.isPassable = true;
				clearCanvas(canvas);
				canvas = this.field[player.xVal + 2][player.yVal];
				player.draw(canvas);
				player.xVal = player.xVal + 2;
			}
			else
			{
				canvas = this.field[player.xVal + 1][player.yVal];
				w.isLocked = true;
				w.drawHorzLocked(canvas);
			}
		}

		else if(i == 3)
		{ //move left

			w = getWall(player.xVal, player.yVal - 1);

			if(correctAnswer)
			{
				w.isPassable = true;
				clearCanvas(canvas);
				canvas = this.field[player.xVal][player.yVal - 2];
				player.draw(canvas);
				player.yVal = player.yVal - 2;
			}
			else
			{
				canvas = this.field[player.xVal][player.yVal - 1];
				w.isLocked = true;
				w.drawVertLocked(canvas);
			}
		}

		else if(i == 4)
		{ //move right

			w = getWall(player.xVal, player.yVal + 1);

			if(correctAnswer)
			{
				w.isPassable = true;
				clearCanvas(canvas);
				canvas = this.field[player.xVal][player.yVal + 2];
				player.draw(canvas);
				player.yVal = player.yVal + 2;
			}
			else
			{
				canvas = this.field[player.xVal][player.yVal + 1];
				w.isLocked = true;
				w.drawVertLocked(canvas);
			}
		}
	}

	public void clearCanvas(Canvas canvas) {
		GraphicsContext g = canvas.getGraphicsContext2D();
		g.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
		canvas.setUserData("Empty");
	}

	public Canvas getCanvas(int x, int y) {
		return this.field[x][y];
	}

	public Wall getWall(int x, int y) {
		for(Wall w : walls) {
			if(w.xVal == x && w.yVal == y)
				return w;
		}
		return null;
	}

	public Question getQuestion() {
		return this.questionList.remove(0);
	}

	public int getLength()
	{
		return this.field.length;
	}


}

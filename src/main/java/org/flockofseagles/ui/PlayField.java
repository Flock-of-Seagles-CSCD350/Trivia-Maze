package org.flockofseagles.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.flockofseagles.DatabaseQuestionUtility;
import org.flockofseagles.Question;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayField extends GridPane {
	protected Canvas[][] field;
	protected Player player = new Player(0 , 0);
	protected Question q;
	ArrayList<Wall> walls = new ArrayList<>();
	private List<Question> questionList;
	protected boolean correctAnswer = false;


	public PlayField(Canvas canvas, int diff) {
		super();

		if(canvas == null)
			throw new IllegalArgumentException("Null canvas object passed into PlayField Constructor");

		DatabaseQuestionUtility db = new DatabaseQuestionUtility();
		db.addInitialQuestionSets();
		questionList = db.loadQuestionSet();

		this.setWidth(canvas.getWidth());
		this.setHeight(canvas.getHeight());
		this.field = initializePlayField(diff);
	}

	public Canvas[][] initializePlayField(int l)
	{
		int rows, cols;
		Canvas[][] cArra;
		Canvas canvas;

		if(l == 1) //normal difficulty
		{
			rows = 9;
			cols = 9;
		}
		else if(l == 2)
		{
			rows = 13;
			cols = 13;
		}
		else
		{
			rows = 21;
			cols = 21;
		}

		cArra = new Canvas[rows][cols];

		for (int i = 0; i < rows; i++)
		{
			//cols
			for (int j = 0; j < cols; j++)
			{
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
				else if(j == this.field.length - 1 && i == this.field.length - 1)
				{
					canvas = this.field[i][j];
					clearCanvas(canvas);
					//ImageView im = new ImageView();
					//im.setImage(new Image("/images/football.png"));
					//GraphicsContext gc = canvas.getGraphicsContext2D();
					//canvas.setUserData("Goal");
					//gc.drawImage(im.getImage(), 0, 0, canvas.getWidth(), canvas.getHeight());
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
		Media pick;
		MediaPlayer mPlayer;

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
				pick = new Media(Paths.get("src/main/resources/sounds/close_door.wav").toUri().toString());
				mPlayer = new MediaPlayer(pick);
				mPlayer.play();

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
				pick = new Media(Paths.get("src/main/resources/sounds/close_door.wav").toUri().toString());
				mPlayer = new MediaPlayer(pick);
				mPlayer.play();

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
				pick = new Media(Paths.get("src/main/resources/sounds/close_door.wav").toUri().toString());
				mPlayer = new MediaPlayer(pick);
				mPlayer.play();

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
				pick = new Media(Paths.get("src/main/resources/sounds/close_door.wav").toUri().toString());
				mPlayer = new MediaPlayer(pick);
				mPlayer.play();

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

	public void clearPlayField()
	{
		Canvas canvas;

		for(int i = 0; i < this.field.length; i++)
		{
			for(int j = 0; j < this.field.length; j++)
			{
				canvas = this.field[i][j];
				GraphicsContext g = canvas.getGraphicsContext2D();
				g.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
				canvas.setUserData("Empty");
			}
		}
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

	public boolean isEnd(int x , int y)
	{
		return x == this.getLength() - 1 && y == this.getLength() - 1;
	}


}

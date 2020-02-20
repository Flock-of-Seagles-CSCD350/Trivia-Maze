package org.flockofseagles.UI;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class PlayField extends GridPane
{
	protected Canvas[][] field;
	protected Player player = new Player(0 , 0);
	ArrayList<Wall> walls = new ArrayList<>();

	public PlayField(Canvas canvas)
	{
		super();
		this.setWidth(canvas.getWidth());
		this.setHeight(canvas.getHeight());
		this.field = initializePlayField();
	}


	public Canvas[][] initializePlayField()
	{
		int rows = 9;
		int cols = 9;
		Canvas[][] cArra = new Canvas[rows][cols];
		Canvas canvas;

		for(int i = 0; i < rows; i++)//rows
		{
			for(int j = 0; j < cols; j++)//cols
			{
				canvas = new Canvas(this.getWidth() / rows, this.getHeight() / cols);
				this.add(canvas, j, i, 1, 1);
				cArra[i][j] = canvas;
				cArra[i][j].setUserData("Empty");
			}
		}

		return cArra;
	}

	public void setMaze()
	{
		Canvas canvas;
		Wall w;
		PowerUp p = new PowerUp();

		for(int i = 0; i < this.field.length; i++)//rows
		{
			for(int j = 0; j < this.field.length; j++)//cols
			{
				if(i == 0 && j == 0)
				{
					canvas = this.field[i][j];
					player.draw(canvas);
				}

				if((i % 2) == 0 && j + 1 < this.field.length) // if current row is an even number
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
					p.draw(canvas);
				}
				else
				{
					canvas = this.field[i][j];
				}
			}
		}

	}

	public void updatePlayer(int i)
	{
		Canvas canvas = getCanvas(player.xVal, player.yVal);
		Wall w;

		if(i == 1)//move up
		{
			if(player.xVal == 0)
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Invalid Move");
				alert.setHeaderText("Cannot move up!");
				alert.show();
			}


			else
			{
				//w = getWall(player.xVal - 1, player.yVal);
				clearCanvas(canvas);
				canvas = this.field[player.xVal - 2][player.yVal];
				player.draw(canvas);
				player.xVal = player.xVal - 2;

			}
		}
		else if(i == 2)//move down
		{
			if(player.xVal == this.field.length - 1)
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Invalid Move");
				alert.setHeaderText("Cannot move down");
				alert.show();
			}


			else
			{
				//w = getWall(player.xVal - 1, player.yVal);
				clearCanvas(canvas);
				canvas = this.field[player.xVal + 2][player.yVal];
				player.draw(canvas);
				player.xVal = player.xVal + 2;

			}
		}
		else if(i == 3)//move left
		{
			if(player.yVal == 0)
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Invalid Move");
				alert.setHeaderText("Cannot move left");
				alert.show();
			}


			else
			{
				//w = getWall(player.xVal - 1, player.yVal);
				clearCanvas(canvas);
				canvas = this.field[player.xVal][player.yVal - 2];
				player.draw(canvas);
				player.yVal = player.yVal - 2;

			}
		}
		else if(i == 4)//move right
		{
			if(player.yVal == this.field.length - 1)
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Invalid Move");
				alert.setHeaderText("Cannot move right!");
				alert.show();
			}


			else
			{
				//w = getWall(player.xVal + 1, player.yVal);
				clearCanvas(canvas);
				canvas = this.field[player.xVal][player.yVal + 2];
				player.draw(canvas);
				player.yVal = player.yVal + 2;

			}
		}
	}

	public void clearCanvas(Canvas canvas)
	{
		GraphicsContext g = canvas.getGraphicsContext2D();
		g.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
		canvas.setUserData("Empty");
	}

	public Canvas getCanvas(int x, int y)
	{
		return this.field[x][y];
	}

	public Wall getWall(int x, int y)
	{
		for(Wall w : walls)
		{
			if(w.xVal == x && w.yVal == y)
				return w;
		}
		return null;
	}
}

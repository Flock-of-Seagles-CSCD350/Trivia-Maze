package UI;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;

public class PlayField extends GridPane
{
	protected Canvas[][] field;
	private Player player = new Player(0 , 0);

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

	public void setWalls()
	{
		Canvas canvas;
		Wall w = new Wall();

		for(int i = 0; i < this.field.length; i++)//rows
		{
			for(int j = 0; j < this.field.length; j++)//cols
			{
				if((i % 2) == 0 && j + 1 < this.field.length) // if current row is an even number
				{
					j++;
					canvas = this.field[i][j];
					w.drawVert(canvas);
				}
				else if((j % 2) == 0 && (i % 2) == 1)
				{
					canvas = this.field[i][j];
					w.drawHorz(canvas);
				}
				else
				{}





			}
		}

	}
}

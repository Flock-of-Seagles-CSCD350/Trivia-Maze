package org.flockofseagles.UI;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Wall
{
	protected boolean isLocked;
	protected int xVal, yVal;
	public Wall(int x, int y)
	{
		this.xVal = x;
		this.yVal = y;
		this.isLocked = false;
	}

	public void drawVert(Canvas canvas)
	{
		ImageView im = new ImageView();
		im.setImage(new Image("/org/flockofseagles/UI/images/wall_vert.png"));
		GraphicsContext gc = canvas.getGraphicsContext2D();
		canvas.setUserData("Wall");
		gc.drawImage(im.getImage(), 0, 0, canvas.getWidth(), canvas.getHeight());
	}

	public void drawHorz(Canvas canvas)
	{
		ImageView im = new ImageView();
		im.setImage(new Image("/org/flockofseagles/UI/images/wall_horz.png"));
		GraphicsContext gc = canvas.getGraphicsContext2D();
		canvas.setUserData("Wall");
		gc.drawImage(im.getImage(), 0, 0, canvas.getWidth(), canvas.getHeight());
	}

	public boolean checkLocked()
	{
		return this.isLocked;
	}
}

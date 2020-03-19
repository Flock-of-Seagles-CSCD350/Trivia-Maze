package org.flockofseagles.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Setter;

import java.io.Serializable;

public class Wall implements Serializable {

	@Setter
	protected boolean isLocked, isPassable;
	protected int xVal, yVal;

	public Wall(int x, int y) {
		this.xVal       = x;
		this.yVal       = y;
		this.isLocked   = false;
		this.isPassable = false;
    }

    public void drawVert(Canvas canvas) {
        ImageView im = new ImageView();
	    im.setImage(new Image(isLocked ? "/images/wall_vert_closed.png" : "/images/wall_vert.png"));
        GraphicsContext gc = canvas.getGraphicsContext2D();
        canvas.setUserData("Wall");
        gc.drawImage(im.getImage(), 0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void drawHorz(Canvas canvas) {
        ImageView im = new ImageView();
	    im.setImage(new Image(isLocked ? "/images/wall_horz_closed.png" : "/images/wall_horz.png"));
        GraphicsContext gc = canvas.getGraphicsContext2D();
        canvas.setUserData("Wall");
        gc.drawImage(im.getImage(), 0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void drawMid(Canvas canvas) {
        ImageView im = new ImageView();
        im.setImage(new Image("/images/wall_middle.png"));
        GraphicsContext gc = canvas.getGraphicsContext2D();
        canvas.setUserData("Wall");
        gc.drawImage(im.getImage(), 0, 0, canvas.getWidth(), canvas.getHeight());
    }

}

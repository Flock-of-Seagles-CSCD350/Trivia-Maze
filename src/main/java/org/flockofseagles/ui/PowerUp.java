package org.flockofseagles.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PowerUp
{

	public void usePowerUp(Player player) {

	}

	public void draw(Canvas canvas) {
		ImageView im = new ImageView();
		im.setImage(new Image("/org/flockofseagles/ui/images/powerup.png"));
		GraphicsContext gc = canvas.getGraphicsContext2D();
		canvas.setUserData("Wall");
		gc.drawImage(im.getImage(), 0, 0, canvas.getWidth(), canvas.getHeight());
	}
}

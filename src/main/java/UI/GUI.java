package UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.prefs.Preferences;

public class GUI extends Application
{
	private boolean isPlaying = false;
	private boolean isPossible = true;
	private Preferences pref;
	private PlayField field;
	private OptionsLayoutController controller;
	{
		controller = new OptionsLayoutController();
	}
	private Canvas mPrimary = new Canvas(301, 315);


	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException
	{
		//Parent root = FXMLLoader.load(getClass().getResource("Document.fxml"));
		final StackPane st = new StackPane();
		final BorderPane root = new BorderPane();

		field = new PlayField(mPrimary);
		field.initializePlayField();
		field.setWalls();

		st.setPrefSize(mPrimary.getWidth(), mPrimary.getHeight());
		st.setAlignment(Pos.CENTER);
		st.getChildren().add(mPrimary);
		st.getChildren().add(field);

		root.getCenter();
		root.setCenter(st);
		root.setPrefSize(mPrimary.getWidth(), mPrimary.getHeight());
		Scene scene = new Scene(root, mPrimary.getWidth(), mPrimary.getHeight());
		//Scene s = new Scene(root, controller.pane.getWidth(), controller.pane.getHeight());
		stage.setTitle("Trivia Maze");
		stage.setScene(scene);
		stage.show();
	}
}

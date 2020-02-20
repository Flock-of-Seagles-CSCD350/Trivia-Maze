package org.flockofseagles.UI;

import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GUI extends Application
{

	PlayField field;

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("Document.fxml"));
		field = OptionsLayoutController.field;
		Scene scene = new Scene(root, 400, 600);
		scene.setOnKeyPressed(this::onKeyPressed);
		primaryStage.setTitle("Trivia Maze");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void onKeyPressed(KeyEvent keyEvent)
	{
		if(new KeyCodeCombination(KeyCode.UP).match(keyEvent))
		{
			System.out.println("pressed");
			field.updatePlayer(1);
		}
		else if(new KeyCodeCombination(KeyCode.DOWN).match(keyEvent))
		{

			field.updatePlayer(2);
		}
		else if(new KeyCodeCombination(KeyCode.LEFT).match(keyEvent))
		{
			field.updatePlayer(3);
		}
		else if(new KeyCodeCombination(KeyCode.RIGHT).match(keyEvent))
		{
			field.updatePlayer(4);
		}
	}


}



































	/*private boolean isPlaying = false;
	private boolean isPossible = true;
	private Preferences pref;
	private PlayField field;
	private OptionsLayoutController controller;
	{
		try
		{
			controller = new OptionsLayoutController();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	private Canvas mPrimary = controller.gameCanvas;


	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException
	{

		AnchorPane ap = controller.pane;
		MenuBar menu = controller.menu;
		ap.setNodeOrientation(NodeOrientation.INHERIT);
		ap.getChildren().add(menu);
		//Parent root = FXMLLoader.load(getClass().getResource("Document.fxml"));
		final StackPane st = controller.stPane;
		//final BorderPane root = new BorderPane();

		field = new PlayField(mPrimary);
		field.initializePlayField();
		field.setMaze();

		//st.setPrefSize(mPrimary.getWidth(), mPrimary.getHeight());
		//st.setAlignment(Pos.CENTER);
		st.getChildren().add(mPrimary);
		st.getChildren().add(field);

		ap.setNodeOrientation(NodeOrientation.INHERIT);
		//root.getCenter();
		//root.setCenter(st);
		//root.setPrefSize(mPrimary.getWidth(), mPrimary.getHeight());
		Scene scene = new Scene(ap, ap.getWidth(), ap.getHeight());
		//Scene s = new Scene(root, controller.pane.getWidth(), controller.pane.getHeight());
		stage.setTitle("Trivia Maze");
		stage.setScene(scene);
		stage.show();
	}
	*/


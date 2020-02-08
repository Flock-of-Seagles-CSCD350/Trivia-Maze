package UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application
{

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("Document.fxml"));
		Scene scene = new Scene(root, 400, 375);

		stage.setTitle("Trivia Maze");
		stage.setScene(scene);
		stage.show();
	}
}

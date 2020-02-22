package org.flockofseagles.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;

public class GUI extends Application {

	PlayField field;

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Document.fxml"));
		field = OptionsLayoutController.field;
		Scene scene = new Scene(root, 600, 600);
		scene.setOnKeyPressed(
				keyEvent -> {
					if(new KeyCodeCombination(KeyCode.UP).match(keyEvent)) {
						field.updatePlayer(1);
					} else if(new KeyCodeCombination(KeyCode.DOWN).match(keyEvent)) {

						field.updatePlayer(2);
					} else if(new KeyCodeCombination(KeyCode.LEFT).match(keyEvent)) {
						field.updatePlayer(3);
					} else if(new KeyCodeCombination(KeyCode.RIGHT).match(keyEvent)) {
						field.updatePlayer(4);
					}
				}
		);
		primaryStage.setTitle("Trivia Maze");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}


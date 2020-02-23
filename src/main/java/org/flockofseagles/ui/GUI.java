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

		FXMLLoader documentLoader = new FXMLLoader(getClass().getClassLoader().getResource("Document.fxml"));
		Parent root = documentLoader.load();
		OptionsLayoutController optionsController = documentLoader.getController();
		field = OptionsLayoutController.field;
		Scene scene = new Scene(root, 600, 600);

		scene.setOnKeyPressed( optionsController::onKeyPressed );

		primaryStage.setTitle("Trivia Maze");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}


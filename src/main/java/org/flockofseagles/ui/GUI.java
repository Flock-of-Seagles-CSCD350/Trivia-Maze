package org.flockofseagles.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class GUI extends Application {

	PlayField field;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader              documentLoader    = new FXMLLoader(getClass().getClassLoader().getResource("Document.fxml"));
		Parent                  root              = documentLoader.load();
		OptionsLayoutController optionsController = documentLoader.getController();
		field = OptionsLayoutController.field;
		Scene  scene = new Scene(root, 600, 500);
		JMetro metro = new JMetro(Style.LIGHT);
		metro.setScene(scene);

		scene.setOnKeyPressed(optionsController::onKeyPressed);
		optionsController.menuItem_difficulty.setOnAction(optionsController::onDifficulty);
		optionsController.menuItem_loadGame.setOnAction(optionsController::onLoadGame);
		optionsController.menuItem_saveGame.setOnAction(optionsController::onSaveGame);
		optionsController.menuItem_deleteSave.setOnAction(optionsController::onDeleteSave);
		primaryStage.setTitle("Trivia Maze");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}


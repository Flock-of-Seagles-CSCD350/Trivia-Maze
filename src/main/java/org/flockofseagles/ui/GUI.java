package org.flockofseagles.ui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

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
		Scene scene = new Scene(root, 600, 500);

		scene.setOnKeyPressed(optionsController::onKeyPressed);
		optionsController.menuItem_difficulty.setOnAction(optionsController::onDifficulty);
		primaryStage.setTitle("Trivia Maze");
		primaryStage.setScene(scene);
		primaryStage.show();
	}


}


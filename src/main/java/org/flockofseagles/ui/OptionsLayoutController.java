package org.flockofseagles.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class OptionsLayoutController extends Dialog<Void> implements Initializable {
	@FXML
	public StackPane stPane;
	public Canvas gameCanvas = new Canvas();
	@FXML
	public Label txt_question;
	@FXML
	public VBox vbox_answers;
	@FXML
	public RadioButton answer1;
	@FXML
	public RadioButton answer2;
	@FXML
	public RadioButton answer3;
	@FXML
	public RadioButton answer4;
	@FXML
	public AnchorPane pane;
	@FXML
	public MenuBar menu;
	public static PlayField field;
	@FXML
	public Label lbl_info;


	/*
		public OptionsLayoutController() throws IOException {
			super();
	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Document.fxml"));
			loader.setController(this);
			Parent root = loader.load();

		}
		*/
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		field = new PlayField(gameCanvas);
		field.initializePlayField();
		field.setMaze();
		Image img = new Image("/images/grass.png");
		stPane.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
		stPane.getChildren().add(field);

		txt_question.setText(field.getQuestion().getQuestion());
		txt_question.setWrapText(true);
	}

	private void updateFields() {
		txt_question.setText(field.getQuestion().getQuestion());
	}


	public void onKeyPressed(KeyEvent keyEvent) {
		if(new KeyCodeCombination(KeyCode.UP).match(keyEvent)) {
			field.updatePlayer(1);
		} else if(keyEvent.getCode() == KeyCode.DOWN) {
			field.updatePlayer(2);
		} else if(keyEvent.getCode() == KeyCode.LEFT) {
			field.updatePlayer(3);
		} else if(keyEvent.getCode() == KeyCode.RIGHT) {
			field.updatePlayer(4);
		}
		updateFields();
	}


}

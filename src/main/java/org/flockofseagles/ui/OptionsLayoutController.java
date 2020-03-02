package org.flockofseagles.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.flockofseagles.TriviaMaze;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OptionsLayoutController extends Dialog<Void> implements Initializable {

	@FXML
	public StackPane  stPane;
	public Canvas     gameCanvas = new Canvas();
	@FXML
	public AnchorPane pane;
	@FXML
	public MenuBar    menu;

	public static PlayField   field;
	private       ToggleGroup radioGroup;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		field = new PlayField(gameCanvas);
		field.initializePlayField();
		field.setMaze();
		Image img = new Image("/images/grass.png");
		stPane.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
		                                                        BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
		stPane.getChildren().add(field);
	}

	public void onKeyPressed(KeyEvent keyEvent) {
		Wall w;

		if (new KeyCodeCombination(KeyCode.UP).match(keyEvent)) {
			if (field.player.xVal == 0) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Out of bounds!");
				alert.setHeaderText("Cannot move up!");
				alert.show();
            } else {
                try {
                    w = field.getWall(field.player.xVal - 1, field.player.yVal);

                    if (w.isPassable) {
                        field.correctAnswer = true;
                        field.updatePlayer(1);
                    } else if (w.isLocked) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("False Start!");
                        alert.setHeaderText("That door is locked! Try another one.");
                        alert.show();
                    } else {
                        openPopUp();
                        field.updatePlayer(1);
                    }
                    field.correctAnswer = false;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (keyEvent.getCode() == KeyCode.DOWN) {
            if (field.player.xVal == (field.getLength() - 1)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Out of bounds!");
                alert.setHeaderText("Cannot move down!");
                alert.show();
            } else {
                try {
                    w = field.getWall(field.player.xVal + 1, field.player.yVal);

                    if (w.isPassable) {
                        field.correctAnswer = true;
                        field.updatePlayer(2);
                    } else if (w.isLocked) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("False Start!");
                        alert.setHeaderText("That door is locked! Try another one.");
                        alert.show();
                    } else {
                        openPopUp();
                        field.updatePlayer(2);
                    }
                    field.correctAnswer = false;


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (keyEvent.getCode() == KeyCode.LEFT) {

            if (field.player.yVal == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Out of bounds!");
                alert.setHeaderText("Cannot move left");
                alert.show();
            } else {
                try {
                    w = field.getWall(field.player.xVal, field.player.yVal - 1);

                    if (w.isPassable) {
                        field.correctAnswer = true;
                        field.updatePlayer(3);
                    } else if (w.isLocked) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("False Start!");
                        alert.setHeaderText("That door is locked! Try another one.");
                        alert.show();
                    } else {
                        openPopUp();
                        field.updatePlayer(3);
                    }
                    field.correctAnswer = false;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (keyEvent.getCode() == KeyCode.RIGHT) {
            if (field.player.yVal == (field.getLength() - 1)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Out of bounds!");
                alert.setHeaderText("Cannot move right");
                alert.show();
            } else {
                try {
                    w = field.getWall(field.player.xVal, field.player.yVal + 1);

                    if (w.isPassable) {
                        field.correctAnswer = true;
                        field.updatePlayer(4);
                    } else if (w.isLocked) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("False Start!");
                        alert.setHeaderText("That door is locked! Try another one.");
                        alert.show();
                    } else {
                        openPopUp();
                        field.updatePlayer(4);
                    }
                    field.correctAnswer = false;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Maze is solvable: " + field.mazeIsSolvable(field.player.xVal, field.player.yVal, 8, 8));
    }

    public void openPopUp() throws IOException {
	    FXMLLoader      popUpLoader = new FXMLLoader(getClass().getClassLoader().getResource("popup.fxml"));
	    Parent          root        = popUpLoader.load();
	    PopUpController controller  = popUpLoader.getController();
	    controller.setUp();
	    Stage stage = new Stage();
	    stage.setTitle("PopUp tester");
	    stage.initStyle(StageStyle.UNDECORATED);
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.showAndWait();
    }

	public void closeGame(ActionEvent actionEvent) {
		System.exit(0);
		Platform.exit();
	}

	public void saveGame(ActionEvent actionEvent) {
		TriviaMaze.saveGame();
	}

}

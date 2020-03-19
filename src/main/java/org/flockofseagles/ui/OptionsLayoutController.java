package org.flockofseagles.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.flockofseagles.TriviaMaze;
import org.flockofseagles.ui.util.Difficulty;
import org.flockofseagles.util.SaveGame;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;
import java.util.ResourceBundle;

public class OptionsLayoutController extends Dialog<Void> implements Initializable {

	@FXML
	public StackPane  stPane;
	public Canvas     gameCanvas = new Canvas();
	@FXML
	public AnchorPane pane;
	@FXML
	public MenuBar    menu;
	@FXML
	public MenuItem   menuItem_difficulty;
	@FXML
	public MenuItem   menuItem_loadGame;
	@FXML
	public MenuItem   menuItem_saveGame;
	@FXML
	public MenuItem   help_about;


	public static PlayField field;
	public        boolean   adminPrivileges = false;

	private static Difficulty  difficulty = Difficulty.EASY;
	private        ToggleGroup radioGroup;
	private        ToggleGroup difficultyGroup;
	private        ToggleGroup loadGroup;
	private        ToggleGroup saveGroup;
	private        boolean     isPlaying  = true;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		field = new PlayField(gameCanvas, difficulty, new SaveGame(0));
		field.setMaze();
		Image img = new Image("/images/grass.png");
		stPane.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
		                                                        BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
		stPane.getChildren().add(field);

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Welcome");
		alert.setHeaderText("Welcome to Football Trivia Maze!\n" +
		                    "> Use the arrow keys to move around.\n" +
		                    "> Each time you move your player, a question will be asked\n" +
		                    "> If you answer correctly, you can move past it.\n" +
		                    "> If you answer incorrectly, the door will lock.\n" +
		                    "> Make you way to the end to win. Good luck!\n");

		Button btnGo = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
		btnGo.setText("Let's Go!");
		alert.showAndWait();
	}

	public void reInitialize(SaveGame saveGame) {
		field.clearPlayField();
		field = null;
		field = new PlayField(gameCanvas, difficulty, saveGame);
		field.setMaze();
		Image img = new Image("/images/grass.png");
		stPane.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
		                                                        BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
		stPane.getChildren().add(field);
		TriviaMaze.setSaveGame(saveGame);
	}

	public void onKeyPressed(KeyEvent keyEvent) {
		if (isPlaying) {
			Wall w;
			field.correctAnswer = false;

			if (new KeyCodeCombination(KeyCode.UP).match(keyEvent)) {
				if (field.getDataStore().getPlayer().xVal == 0) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Out of bounds!");
					alert.setHeaderText("Cannot move up!");
					alert.show();
				} else if (adminPrivileges) {
					field.correctAnswer = true;
					field.updatePlayer(1);
				} else {
					try {
						w = field.getWall(field.getDataStore().getPlayer().xVal - 1, field.getDataStore().getPlayer().yVal);

						if (w.isPassable) {
							field.correctAnswer = true;
							field.updatePlayer(1);
						} else if (w.isLocked) {
							showLockedDoorPopup();
						} else {
							openPopUp();
							field.updatePlayer(1);
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else if (keyEvent.getCode() == KeyCode.DOWN) {
				if (field.getDataStore().getPlayer().xVal == (field.getLength() - 1)) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Out of bounds!");
					alert.setHeaderText("Cannot move down!");
					alert.show();
				} else if (adminPrivileges) {
					field.correctAnswer = true;
					field.updatePlayer(2);
				} else {
					try {
						w = field.getWall(field.getDataStore().getPlayer().xVal + 1, field.getDataStore().getPlayer().yVal);

						if (w.isPassable) {
							field.correctAnswer = true;
							field.updatePlayer(2);
						} else if (w.isLocked) {
							showLockedDoorPopup();
						} else {
							openPopUp();
							field.updatePlayer(2);
						}


					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else if (keyEvent.getCode() == KeyCode.LEFT) {

				if (field.getDataStore().getPlayer().yVal == 0) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Out of bounds!");
					alert.setHeaderText("Cannot move left");
					alert.show();
				} else if (adminPrivileges) {
					field.correctAnswer = true;
					field.updatePlayer(3);
				} else {
					try {
						w = field.getWall(field.getDataStore().getPlayer().xVal, field.getDataStore().getPlayer().yVal - 1);

						if (w.isPassable) {
							field.correctAnswer = true;
							field.updatePlayer(3);
						} else if (w.isLocked) {
							showLockedDoorPopup();
						} else {
							openPopUp();
							field.updatePlayer(3);
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else if (keyEvent.getCode() == KeyCode.RIGHT) {
				if (field.getDataStore().getPlayer().yVal == (field.getLength() - 1)) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Out of bounds!");
					alert.setHeaderText("Cannot move right");
					alert.show();
				} else if (adminPrivileges) {
					field.correctAnswer = true;
					field.updatePlayer(4);
				} else {
					try {
						w = field.getWall(field.getDataStore().getPlayer().xVal, field.getDataStore().getPlayer().yVal + 1);

						if (w.isPassable) {
							field.correctAnswer = true;
							field.updatePlayer(4);
						} else if (w.isLocked) {
							showLockedDoorPopup();
						} else {
							openPopUp();
							field.updatePlayer(4);
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else if (new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_ANY).match(keyEvent)) {
				if (!adminPrivileges) {
					adminPrivileges = true;
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText("Admin Privileges Enabled");
					alert.show();
				} else {
					adminPrivileges = false;
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText("Admin Privileges Disabled");
					alert.show();
				}
			}

			if (field.isEnd(field.getDataStore().getPlayer().xVal, field.getDataStore().getPlayer().yVal)) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Congratulations!");
				alert.setHeaderText("Winner winner chicken dinner!\n" +
				                    "Thank you for playing!");
				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == ButtonType.OK) {
					alert.close();

					playAgainPopup();
				}
			}

			if (!field.mazeIsSolvable(field.getDataStore().getPlayer().xVal,
			                          field.getDataStore().getPlayer().yVal,
			                          field.getLength() - 1,
			                          field.getLength() - 1)) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("You lost :(");
				alert.setHeaderText("Maybe next time");
				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == ButtonType.OK) {
					alert.close();

					playAgainPopup();
				}
			}
		}
	}

	private void showLockedDoorPopup() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("False Start!");
		alert.setHeaderText("That door is locked! Try another one.");
		alert.show();
	}

	private void playAgainPopup() {
		Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
		alert2.setTitle("New Game");
		alert2.setHeaderText("Would you like to Play again?");
		Button btnYES = (Button) alert2.getDialogPane().lookupButton(ButtonType.OK);
		btnYES.setText("YES");
		alert2.getButtonTypes().add(ButtonType.NO);
		Optional<ButtonType> choice = alert2.showAndWait();

		if (choice.isPresent() && choice.get() == ButtonType.OK) {
			reInitialize(new SaveGame(0));
		} else {
			System.exit(0);
		}
	}

	public void openPopUp() throws IOException {
		FXMLLoader      popUpLoader = new FXMLLoader(getClass().getClassLoader().getResource("popup.fxml"));
		Parent          root        = popUpLoader.load();
		PopUpController controller  = popUpLoader.getController();
		controller.setField(field);
		controller.setUp();
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		Scene  scene = new Scene(root);
		JMetro metro = new JMetro(Style.LIGHT);
		metro.setScene(scene);
		stage.setScene(scene);
		stage.showAndWait();
	}

	public void setDifficulty(Difficulty difficulty) {
		OptionsLayoutController.difficulty = difficulty;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void onDifficulty(ActionEvent actionEvent) {
		difficultyGroup = new ToggleGroup();
		Stage s = new Stage();
		s.setTitle("Change Difficulty");
		s.initModality(Modality.WINDOW_MODAL);
		VBox vbox = new VBox();

		Button btnOK = new Button("OK");

		RadioButton easy = new RadioButton("Easy");
		easy.setToggleGroup(difficultyGroup);

		RadioButton medium = new RadioButton("Medium");
		medium.setToggleGroup(difficultyGroup);

		RadioButton hard = new RadioButton("Hard");
		hard.setToggleGroup(difficultyGroup);

		difficultyGroup.selectToggle(easy);

		RadioButton selected = (RadioButton) difficultyGroup.getSelectedToggle();
		vbox.getChildren().addAll(easy, medium, hard, btnOK);
		vbox.setAlignment(Pos.CENTER_LEFT);
		vbox.setSpacing(10);
		Scene  scene = new Scene(vbox, 300, 200);
		JMetro metro = new JMetro(Style.LIGHT);
		metro.setScene(scene);
		s.setScene(scene);
		s.show();

		btnOK.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Confirm Changes");
				alert.setHeaderText("Changing the difficulty will end your current iteration of the game.\n" +
				                    "All current data will be lost and you will have to start over");
				ButtonType btnCancel = ButtonType.CANCEL;
				alert.getButtonTypes().add(btnCancel);
				Optional<ButtonType> result = alert.showAndWait();

				if (result.isPresent() && result.get() == ButtonType.OK) {
					if (selected.equals(difficultyGroup.getSelectedToggle())) {
						s.close();
					} else {
						if (((RadioButton) difficultyGroup.getSelectedToggle()).getText().equals("Easy")) {
							difficultyGroup.selectToggle(selected);
							setDifficulty(Difficulty.EASY);
							reInitialize(new SaveGame(0));
						} else if (((RadioButton) difficultyGroup.getSelectedToggle()).getText().equals("Medium")) {
							difficultyGroup.selectToggle(selected);
							setDifficulty(Difficulty.MEDIUM);
							reInitialize(new SaveGame(0));
						} else {
							difficultyGroup.selectToggle(selected);
							setDifficulty(Difficulty.HARD);
							reInitialize(new SaveGame(0));
						}


						s.close();

					}
				}


			}
		});
	}

	public void onLoadGame(ActionEvent actionEvent) {
		loadGroup = new ToggleGroup();
		Stage s = new Stage();
		s.setTitle("Load Game");
		s.initModality(Modality.WINDOW_MODAL);
		VBox vbox = new VBox();

		Button btnOK = new Button("Load");

		var buttons      = new RadioButton[5];
		var descriptions = new Text[5];
		var saves        = TriviaMaze.getSaves();
		for (int i = 0; i < 5; i++) {
			RadioButton btn      = new RadioButton(String.format("Save Game %d", i + 1));
			Text        textArea = new Text();
			if (saves[i] != null) {
				var data = saves[i].getData();
				// Get Localized Last Save String
				DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
				String            lastSave  = data.getLastSave().atZone(ZoneId.systemDefault()).format(formatter);

				// Get Player Location
				int x = (data.getPlayer().xVal / 2) + 1;
				int y = (data.getPlayer().yVal / 2) + 1;

				textArea.setText(String.format("%s\n%s    Player @ (R-%d, C-%d)", lastSave, data.getDifficulty(), x, y));
				btn.setOnAction(actionEvent1 -> btnOK.setDisable(false));
				btn.setUserData(i);
			} else {
				textArea.setText("Empty Slot");
				btn.setDisable(true);
			}
			descriptions[i] = textArea;
			btn.setToggleGroup(loadGroup);
			buttons[i] = btn;
		}

		for (int x = 0; x < 5; x++) {
			vbox.getChildren().add(buttons[x]);
			vbox.getChildren().add(descriptions[x]);
		}
		vbox.getChildren().add(btnOK);
		vbox.setAlignment(Pos.CENTER_LEFT);
		vbox.setSpacing(10);
		vbox.setStyle("-fx-padding: 10");
		Scene  scene = new Scene(vbox, 300, 400);
		JMetro metro = new JMetro(Style.LIGHT);
		metro.setScene(scene);
		s.setScene(scene);
		s.show();

		if (loadGroup.getSelectedToggle() == null) {
			btnOK.setDisable(true);
		}
		btnOK.setOnAction(actionEvent1 -> {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Confirm Changes");
			alert.setHeaderText("Loading a save will discard the progress in your current game!\n" +
			                    "Are you sure you want to continue?");
			ButtonType btnCancel = ButtonType.CANCEL;
			alert.getButtonTypes().add(btnCancel);
			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent() && result.get() == ButtonType.OK) {
				// re-init using chosen save data
				reInitialize(saves[Integer.parseInt(loadGroup.getSelectedToggle().getUserData().toString())]);
			}
			s.close();
		});
	}

	public void onSaveGame(ActionEvent actionEvent) {
		if (field.getDataStore().getLastSave() != Instant.EPOCH) {
			TriviaMaze.saveGame(field.getDataStore(), TriviaMaze.getSaveGame().getSlot());
		} else {
			saveGroup = new ToggleGroup();
			Stage s = new Stage();
			s.setTitle("Save Game");
			s.initModality(Modality.WINDOW_MODAL);
			VBox vbox = new VBox();

			Button btnOK = new Button("Save");

			var buttons      = new RadioButton[5];
			var descriptions = new Text[5];
			var saves        = TriviaMaze.getSaves();
			for (int i = 0; i < 5; i++) {
				RadioButton btn      = new RadioButton(String.format("Save Game %d", i + 1));
				Text        textArea = new Text();
				if (saves[i] != null) {
					var data = saves[i].getData();
					// Get Localized Last Save String
					DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
					String            lastSave  = data.getLastSave().atZone(ZoneId.systemDefault()).format(formatter);

					// Get Player Location
					int x = data.getPlayer().xVal / 2;
					int y = data.getPlayer().yVal / 2;

					textArea.setText(String.format("Saved on: %s\nDifficulty: %s, Player @ (%d,%d)", lastSave, data.getDifficulty(), x,
					                               y));
					btn.setUserData(i + "X");
				} else {
					textArea.setText("Empty Slot");
					btn.setUserData(i);
				}
				descriptions[i] = textArea;
				btn.setToggleGroup(saveGroup);
				btn.setOnAction(actionEvent1 -> btnOK.setDisable(false));
				buttons[i] = btn;
			}

			for (int x = 0; x < 5; x++) {
				vbox.getChildren().add(buttons[x]);
				vbox.getChildren().add(descriptions[x]);
			}
			vbox.getChildren().add(btnOK);
			vbox.setAlignment(Pos.CENTER_LEFT);
			vbox.setSpacing(10);
			vbox.setStyle("-fx-padding: 10");
			Scene  scene = new Scene(vbox, 300, 400);
			JMetro metro = new JMetro(Style.LIGHT);
			metro.setScene(scene);
			s.setScene(scene);
			s.show();

			if (saveGroup.getSelectedToggle() == null) {
				btnOK.setDisable(true);
			}

			btnOK.setOnAction(actionEvent1 -> {
				field.getDataStore().setLastSave(Instant.now());
				int slot = 0;
				if (saveGroup.getSelectedToggle().getUserData().toString().endsWith("X")) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Confirm Changes");
					alert.setHeaderText("Using this slot will overwrite the save data it currently has!\n" +
					                    "Are you sure you want to continue? This can't be undone!");
					ButtonType btnCancel = ButtonType.CANCEL;
					alert.getButtonTypes().add(btnCancel);
					Optional<ButtonType> result = alert.showAndWait();

					if (result.isPresent() && result.get() == ButtonType.OK) {
						slot = Integer.parseInt(saveGroup.getSelectedToggle().getUserData().toString().substring(0, 1));
					}
				} else {
					slot = Integer.parseInt(saveGroup.getSelectedToggle().getUserData().toString());
				}
				TriviaMaze.saveGame(field.getDataStore(), slot);
				s.close();
			});
		}
	}

	public void closeGame(ActionEvent actionEvent) {
		System.exit(0);
		Platform.exit();
	}

}

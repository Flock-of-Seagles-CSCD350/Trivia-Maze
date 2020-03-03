package org.flockofseagles.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.*;
import org.flockofseagles.Question;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class OptionsLayoutController extends Dialog<Void> implements Initializable {
	@FXML
	public StackPane stPane;
	@FXML
	public Canvas gameCanvas = new Canvas();
	@FXML
	public AnchorPane pane;
	@FXML
	public MenuBar menu;
	@FXML
	public MenuItem file_save;
	@FXML
	public MenuItem menuItem_difficulty;
	@FXML
	public MenuItem help_about;


	public static PlayField field;
	private ToggleGroup radioGroup;
	private ToggleGroup difficultyGroup;
	private static int difficulty = 1;
	public boolean adminPrivileges = false;


	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		field = new PlayField(gameCanvas, difficulty);
		field.setMaze();
		Image img = new Image("/images/grass.png");
		stPane.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
		stPane.getChildren().add(field);

	}

	public void reInitialize()
	{
		field.clearPlayField();
		field = null;
		field = new PlayField(gameCanvas, difficulty);
		field.setMaze();
		Image img = new Image("/images/grass.png");
		stPane.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
		stPane.getChildren().add(field);
	}


	public void onKeyPressed(KeyEvent keyEvent)
	{
		Wall w;

		if(new KeyCodeCombination(KeyCode.UP).match(keyEvent))
		{
			if(field.player.xVal == 0)
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Out of bounds!");
				alert.setHeaderText("Cannot move up!");
				alert.show();
			}
			else if(adminPrivileges)
			{
				field.correctAnswer = true;
				field.updatePlayer(1);
			}
			else
			{
				try
				{
					w = field.getWall(field.player.xVal - 1, field.player.yVal);

					if (w.isPassable)
					{
						field.correctAnswer = true;
						field.updatePlayer(1);
					}
					else if (w.isLocked)
					{
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("False Start!");
						alert.setHeaderText("That door is locked! Try another one.");
						alert.show();
					} else
					{
						openPopUp();
						field.updatePlayer(1);
					}
					field.correctAnswer = false;

				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}


		else if(keyEvent.getCode() == KeyCode.DOWN)
		{
			if(field.player.xVal == (field.getLength() - 1))
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Out of bounds!");
				alert.setHeaderText("Cannot move down!");
				alert.show();
			}
			else if(adminPrivileges)
			{
				field.correctAnswer = true;
				field.updatePlayer(2);
			}
			else
			{
				try
				{
					w = field.getWall(field.player.xVal + 1, field.player.yVal);

					if (w.isPassable)
					{
						field.correctAnswer = true;
						field.updatePlayer(2);
					}
					else if (w.isLocked)
					{
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("False Start!");
						alert.setHeaderText("That door is locked! Try another one.");
						alert.show();
					}
					else
					{
						openPopUp();
						field.updatePlayer(2);
					}
					field.correctAnswer = false;


				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}


		else if(keyEvent.getCode() == KeyCode.LEFT)
		{

			if(field.player.yVal == 0)
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Out of bounds!");
				alert.setHeaderText("Cannot move left");
				alert.show();
			}
			else if(adminPrivileges)
			{
				field.correctAnswer = true;
				field.updatePlayer(3);
			}
			else
			{
				try
				{
					w = field.getWall(field.player.xVal, field.player.yVal - 1);

					if (w.isPassable)
					{
						field.correctAnswer = true;
						field.updatePlayer(3);
					}
					else if (w.isLocked)
					{
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("False Start!");
						alert.setHeaderText("That door is locked! Try another one.");
						alert.show();
					}
					else
					{
						openPopUp();
						field.updatePlayer(3);
					}
					field.correctAnswer = false;

				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}


		else if(keyEvent.getCode() == KeyCode.RIGHT)
		{
			if(field.player.yVal == (field.getLength() - 1))
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Out of bounds!");
				alert.setHeaderText("Cannot move right");
				alert.show();
			}
			else if(adminPrivileges)
			{
				field.correctAnswer = true;
				field.updatePlayer(4);
			}
			else
			{
				try
				{
					w = field.getWall(field.player.xVal, field.player.yVal + 1);

					if (w.isPassable)
					{
						field.correctAnswer = true;
						field.updatePlayer(4);
					}
					else if (w.isLocked)
					{
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("False Start!");
						alert.setHeaderText("That door is locked! Try another one.");
						alert.show();
					}
					else
					{
						openPopUp();
						field.updatePlayer(4);
					}
					field.correctAnswer = false;

				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		else if(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_ANY).match(keyEvent))
		{
			if(!adminPrivileges)
			{
				adminPrivileges = true;
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Admin Privileges Enabled");
				alert.show();
			}
			else
			{
				adminPrivileges = false;
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Admin Privileges Disabled");
				alert.show();
			}
		}
	}

	public void openPopUp() throws IOException
	{
		FXMLLoader popUpLoader = new FXMLLoader(getClass().getClassLoader().getResource("popup.fxml"));
		Parent root = popUpLoader.load();
		PopUpController controller = popUpLoader.getController();
		controller.setUp();
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.showAndWait();
	}

	public void setDifficulty(int i)
	{
		difficulty = i;
	}

	public int getDifficulty()
	{
		return difficulty;
	}

	public void onDifficulty(ActionEvent actionEvent)
	{
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
		Scene scene = new Scene(vbox, 300, 125);
		s.setScene(scene);
		s.show();

		btnOK.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent actionEvent)
			{
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Confirm Changes");
				alert.setHeaderText("Changing the difficulty will end your current iteration of the game.\n" +
						"All current data will be lost and you will have to start over");
				ButtonType btnCancel = ButtonType.CANCEL;
				alert.getButtonTypes().add(btnCancel);
				Optional<ButtonType> result = alert.showAndWait();

				if(result.get() == ButtonType.OK)
				{
					if(selected.equals(difficultyGroup.getSelectedToggle()))
					{
						s.close();
					}
					else
					{
						if(((RadioButton) difficultyGroup.getSelectedToggle()).getText().equals("Easy"))
						{
							difficultyGroup.selectToggle(selected);
							setDifficulty(1);
							reInitialize();
						}
						else if(((RadioButton) difficultyGroup.getSelectedToggle()).getText().equals("Medium"))
						{
							difficultyGroup.selectToggle(selected);
							setDifficulty(2);
							reInitialize();
						}
						else
						{
							difficultyGroup.selectToggle(selected);
							setDifficulty(3);
							reInitialize();
						}

						s.close();

					}
				}


			}
		});
	}

}

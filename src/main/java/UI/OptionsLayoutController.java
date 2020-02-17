package UI;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionsLayoutController extends Dialog<Void> implements Initializable
{
	public StackPane stPane;
	public Canvas gameCanvas = new Canvas();
	public Label txt_question;
	public VBox vbox_answers;
	public RadioButton answer1;
	public RadioButton answer2;
	public RadioButton answer3;
	public RadioButton answer4;
	public AnchorPane pane;
	public MenuBar menu;
	public static PlayField field;
	public Label lbl_info;


	/*
		public OptionsLayoutController() throws IOException
		{
			super();
	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Document.fxml"));
			loader.setController(this);
			Parent root = loader.load();
	
	
	
		}
		*/
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{

		field = new PlayField(gameCanvas);
		field.initializePlayField();
		field.setMaze();
		stPane.getChildren().add(field);
	}


	public void onKeyPressed(KeyEvent keyEvent)
	{
		if(new KeyCodeCombination(KeyCode.UP).match(keyEvent))
		{
			System.out.println("pressed");
			field.updatePlayer(1);
		}
		else if(keyEvent.getCode() == KeyCode.DOWN)
		{
			field.updatePlayer(2);
		}
		else if(keyEvent.getCode() == KeyCode.LEFT)
		{
			field.updatePlayer(3);
		}
		else if(keyEvent.getCode() == KeyCode.RIGHT)
		{
			field.updatePlayer(4);
		}
	}


}

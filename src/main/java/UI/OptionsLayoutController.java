package UI;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionsLayoutController extends Dialog<Void> implements Initializable
{
	public AnchorPane gamePane;
	public Canvas gameCanvas = new Canvas();
	public GridPane controlGrid;
	public Button btn_up;
	public Button btn_down;
	public Button btn_left;
	public Button btn_right;
	public Label txt_question;
	public VBox vbox_answers;
	public RadioButton answer1;
	public RadioButton answer2;
	public RadioButton answer3;
	public RadioButton answer4;
	public AnchorPane pane;
	public MenuBar menu;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{

	}
}

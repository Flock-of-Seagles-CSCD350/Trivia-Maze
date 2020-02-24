package org.flockofseagles.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.flockofseagles.Question;

import java.net.URL;
import java.util.ResourceBundle;

public class PopUpController extends Dialog<Void> implements Initializable
{
	@FXML
	public AnchorPane mainPane;

	@FXML
	public SplitPane spl_pane;

	@FXML
	public Label lbl_question;

	@FXML
	public VBox vbox_answers;

	@FXML
	public RadioButton rb_answer1;

	@FXML
	public RadioButton rb_answer2;

	@FXML
	public RadioButton rb_answer3;

	@FXML
	public RadioButton rb_answer4;

	@FXML
	public ImageView img_status;

	@FXML
	public Button btn_OK;

	public static PlayField field = OptionsLayoutController.field;
	public ToggleGroup radioGroup;
	public boolean isAnswered = false;

	public void setUp()
	{
		btn_OK.setOnAction(ActionEvent -> closeWindow(ActionEvent));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		updateQuestionFields();
	}

	public void updateQuestionFields()
	{
		Question q = field.getQuestion();
		lbl_question.setText(q.getQuestion());

		String[] answerSet = q.getPossibleAnswers();


		rb_answer1.setText(answerSet[0]);
		rb_answer2.setText(answerSet[1]);
		rb_answer3.setText(answerSet[2]);
		rb_answer4.setText(answerSet[3]);

		radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
		{
			@Override
			public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1)
			{

					isAnswered = true;
					RadioButton selected = (RadioButton) radioGroup.getSelectedToggle();


					if (selected.getText().equals(answerSet[0])) //answer is correct
					{
						img_status.setImage(new Image("/images/correct.png"));
						rb_answer2.setDisable(true);
						rb_answer3.setDisable(true);
						rb_answer4.setDisable(true);
						field.correctAnswer = true;

					} else										//answer was incorrect
					{
						img_status.setImage(new Image("/images/wrong.png"));
						rb_answer1.setDisable(true);
						rb_answer2.setDisable(true);
						rb_answer3.setDisable(true);
						rb_answer4.setDisable(true);
					}

			}
		});


	}

	public void closeWindow(ActionEvent e)
	{
		if(isAnswered)
		{
			Node source = (Node) e.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
		}

	}


}

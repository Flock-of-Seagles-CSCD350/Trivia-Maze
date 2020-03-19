package org.flockofseagles.console.admin.question;
/* Created by GamerBah on 3/1/20 */

import org.flockofseagles.DatabaseQuestionUtility;
import org.flockofseagles.Question;
import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.InputWrapper;
import org.flockofseagles.console.MenuItem;
import org.flockofseagles.console.admin.ConfirmationMenu;
import org.flockofseagles.console.admin.question.answer.AddAnswerMenu;

public class AddQuestionMenu extends ConsoleMenu {

	private Question question;

	public AddQuestionMenu(final Question question, final ConsoleMenu previousMenu) {
		super("Question Editor", previousMenu);
		this.question = question;

		addMenuItem(
				new MenuItem("Edit Question Text", this::edit),
				new MenuItem("Add Question Answers", () -> new AddAnswerMenu(question).open()),
				new MenuItem("Delete Question", this::delete)
		);
	}

	@Override
	protected String getDescription() {
		return "\"" + question.getQuestion() + "\"\n";
	}

	@Override
	public ConsoleMenu reload() {
		return new AddQuestionMenu(question, getPreviousMenu());
	}

	private void edit() {
		String text = InputWrapper.readString("Enter new text for the question: ");

		DatabaseQuestionUtility database = new DatabaseQuestionUtility();
		database.editQuestion(question.getQuestion(), text);
		System.out.println("Text successfully changed!");
		question.setQuestion(text);
		reload().open();
	}

	private void delete() {
		int result = new ConfirmationMenu(this).open();
		if (result == 1) {
			DatabaseQuestionUtility database = new DatabaseQuestionUtility();
			database.removeQuestion(question.getQuestion());
			System.out.println("Question successfully deleted!");
			getPreviousMenu().reload().open(true);
		} else {
			this.open();
		}
	}

}

package org.flockofseagles.console.admin.question.answer;
/* Created by GamerBah on 2/16/20 */

import org.flockofseagles.DatabaseQuestionUtility;
import org.flockofseagles.Question;
import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.InputWrapper;
import org.flockofseagles.console.MenuItem;
import org.flockofseagles.console.admin.ConfirmationMenu;

public class EditAnswerMenu extends ConsoleMenu {

	private final Question question;
	private final String   answer;

	public EditAnswerMenu(final Question question, final String answer, final ConsoleMenu previousMenu) {
		super("Answer Editor", previousMenu);
		this.question = question;
		this.answer   = answer;

		addMenuItem(
				new MenuItem("Edit Answer", this::edit),
				new MenuItem("Delete Answer", this::delete)
		);
	}

	@Override
	protected String getDescription() {
		return "\"" + answer + "\"\n";
	}

	@Override
	public ConsoleMenu reload() {
		return new EditAnswerMenu(question, answer, getPreviousMenu());
	}


	private void edit() {
		String text = InputWrapper.readString("Enter new text for the answer: ");

		DatabaseQuestionUtility database = new DatabaseQuestionUtility();
		database.editAnswer(question.getQuestion(), answer, text);
		System.out.println("Text successfully changed!");
		reload().open();
	}

	private void delete() {
		int result = new ConfirmationMenu(this).open();
		if (result == 1) {
			DatabaseQuestionUtility database = new DatabaseQuestionUtility();
			database.removeAnswer(question.getQuestion(), answer);
			System.out.println("Answer successfully deleted!");
			getPreviousMenu().reload().open(true);
		} else {
			this.open();
		}
	}

}

package org.flockofseagles.console.admin.question;
/* Created by GamerBah on 2/16/20 */

import org.flockofseagles.Question;
import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.MenuItem;
import org.flockofseagles.console.admin.question.answer.ListAnswersMenu;

public class EditQuestionMenu extends ConsoleMenu {

	private Question question;

	public EditQuestionMenu(final Question question, final ConsoleMenu previousMenu) {
		super("Question Editor", previousMenu);
		this.question = question;

		addMenuItem(
				new MenuItem("Edit Question Text", this::editText),
				new MenuItem("Edit Question Answers", () -> new ListAnswersMenu(question, this).open()),
				new MenuItem("Delete Question", this::deleteQuestion)
		);
	}

	@Override
	protected String getDescription() {
		return "\"" + question + "\"\n";
	}

	private void editText() {
		// TODO: Use Question class
		//question = InputWrapper.readString("Enter new text for the question: ");
		System.out.println("Text successfully changed!");
		this.open();
	}

	private void deleteQuestion() {
		// TODO: Use Database
		System.out.println("Not Yet Implemented");
		this.open();
	}

}

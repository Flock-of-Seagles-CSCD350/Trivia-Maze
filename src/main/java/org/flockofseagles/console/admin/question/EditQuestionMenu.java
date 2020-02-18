package org.flockofseagles.console.admin.question;
/* Created by GamerBah on 2/16/20 */

import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.InputWrapper;
import org.flockofseagles.console.MenuItem;
import org.flockofseagles.console.admin.question.answer.ListAnswersMenu;

import java.util.Scanner;

public class EditQuestionMenu extends ConsoleMenu {

	private String question;

	public EditQuestionMenu(final String question, final ConsoleMenu previousMenu, final Scanner input) {
		super("Question Editor", previousMenu, input);
		this.question = question;

		addMenuItem(
				new MenuItem("Edit Question Text", this::editText),
				new MenuItem("Edit Question Answers", () -> new ListAnswersMenu(question, this, input).open()),
				new MenuItem("Delete Question", this::deleteQuestion)
		);
	}

	@Override
	protected String getDescription() {
		return "\"" + question + "\"\n";
	}

	private void editText() {
		// TODO: Use Question class
		question = InputWrapper.readString("Enter new text for the question: ");
		System.out.println("Text successfully changed!");
		this.open();
	}

	private void deleteQuestion() {
		// TODO: Use Database
		System.out.println("Not Yet Implemented");
		this.open();
	}

}

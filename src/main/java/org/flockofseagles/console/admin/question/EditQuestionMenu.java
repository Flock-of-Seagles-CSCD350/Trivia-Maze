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
				new MenuItem("Edit Question Name", this::editName),
				new MenuItem("Edit Question Answers", () -> new ListAnswersMenu(question, this, input).open()),
				new MenuItem("Delete Question", () -> System.out.println("NYI"))
		);
	}

	@Override
	protected String getDescription() {
		return "\"" + question + "\"\n";
	}

	private void editName() {
		// TODO: Use Question class
		String name = InputWrapper.readString("Enter a new name for the question: ");
		getPreviousMenu().open();
	}

}

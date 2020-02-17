package org.flockofseagles.console.admin.question.answer;
/* Created by GamerBah on 2/16/20 */

import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.InputWrapper;
import org.flockofseagles.console.MenuItem;

import java.util.Scanner;

public class EditAnswerMenu extends ConsoleMenu {

	private final String answer;

	public EditAnswerMenu(final String answer, final ConsoleMenu previousMenu, final Scanner input) {
		super("Answer Editor", previousMenu, input);
		this.answer = answer;

		addMenuItem(
				new MenuItem("Edit Answer", this::edit),
				new MenuItem("Delete Answer", this::remove)
		);
	}

	@Override
	protected String getDescription() {
		return "\"" + answer + "\"\n";
	}

	private void edit() {
		// TODO: Use Question class & integrate with database
		String name = InputWrapper.readString("Enter a new name for the question: ");
		System.out.println("Not Yet Implemented");
		getPreviousMenu().open();
	}

	private void remove() {
		// TODO: Integrate with database
		System.out.println("Question deleted successfully!");
		getPreviousMenu().open();
	}

}

package org.flockofseagles.console.admin.question.answer;
/* Created by GamerBah on 2/16/20 */

import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.InputWrapper;
import org.flockofseagles.console.MenuItem;

public class EditAnswerMenu extends ConsoleMenu {

	private final String answer;

	public EditAnswerMenu(final String answer, final ConsoleMenu previousMenu) {
		super("Answer Editor", previousMenu);
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
		String name = InputWrapper.readString("Enter new text for the answer: ");
		System.out.println("Not Yet Implemented");
		this.open();
	}

	private void remove() {
		// TODO: Integrate with database
		System.out.println("Not Yet Implemented");
		this.open();
	}

}

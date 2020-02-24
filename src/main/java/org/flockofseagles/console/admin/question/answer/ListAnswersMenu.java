package org.flockofseagles.console.admin.question.answer;
/* Created by GamerBah on 2/16/20 */

import org.flockofseagles.Question;
import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.MenuItem;

public class ListAnswersMenu extends ConsoleMenu {

	private final Question question;

	public ListAnswersMenu(final Question question, final ConsoleMenu previousMenu) {
		super("Editing Answers", previousMenu);
		this.question = question;

		addMenuItem(
				new MenuItem("Red", () -> new EditAnswerMenu("Red", this).open()),
				new MenuItem("Blue", () -> new EditAnswerMenu("Blue", this).open()),
				new MenuItem("Green", () -> new EditAnswerMenu("Green", this).open()),
				new MenuItem("All of the above!", () -> new EditAnswerMenu("All of the above!", this).open())
		);
	}

	@Override
	protected String getDescription() {
		return "\"" + question + "\"" + "\n" +
			   "\n" +
			   "Select a question by its number, or\n" +
			   "add a new question by entering  +\n";
	}

}

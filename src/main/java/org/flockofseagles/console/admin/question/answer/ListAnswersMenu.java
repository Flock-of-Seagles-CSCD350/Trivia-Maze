package org.flockofseagles.console.admin.question.answer;
/* Created by GamerBah on 2/16/20 */

import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.MenuItem;

import java.util.Scanner;

public class ListAnswersMenu extends ConsoleMenu {

	private final String question;

	public ListAnswersMenu(final String question, final ConsoleMenu previousMenu, final Scanner input) {
		super("Editing Answers", previousMenu, input);
		this.question = question;

		addMenuItem(
				new MenuItem("Red", () -> new EditAnswerMenu("Red", this, input).open()),
				new MenuItem("Blue", () -> new EditAnswerMenu("Blue", this, input).open()),
				new MenuItem("Green", () -> new EditAnswerMenu("Green", this, input).open()),
				new MenuItem("All of the above!", () -> new EditAnswerMenu("All of the above!", this, input).open())
		);
	}

	@Override
	protected String getDescription() {
		return "\"" + question + "\"" + """


		                                Select a question by its number, or
		                                add a new question by entering  +
		                                """;
	}

}

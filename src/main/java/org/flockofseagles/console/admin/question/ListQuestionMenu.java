package org.flockofseagles.console.admin.question;

import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.MenuItem;

import java.util.Scanner;

public class ListQuestionMenu extends ConsoleMenu {

	public ListQuestionMenu(final ConsoleMenu previousMenu, final Scanner input) {
		super("Maze Questions", previousMenu, input);

		addMenuItem(new MenuItem("What's the best color?", () -> new EditQuestionMenu("What's the best color?", this, input).open()));
	}

	@Override
	protected String getDescription() {
		return "Select a question by its number, or\n" +
			   "add a new question by entering  +\n";
	}

}

package org.flockofseagles.console.admin;

import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.MenuItem;

import java.util.Scanner;

public class ListQuestionMenu extends ConsoleMenu {

	public ListQuestionMenu(final Scanner input) {
		super("Maze Questions", input);

		menuItems.add(new MenuItem("Question 1", () -> new EditQuestionMenu("Question 1", this, input).open()));
	}

	@Override
	protected String getDescription() {
		return """
		       Select a question by its number, or
		       add a new question by entering  +
		             """;
	}

}

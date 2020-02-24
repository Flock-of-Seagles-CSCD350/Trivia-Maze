package org.flockofseagles.console.admin.question;

import org.flockofseagles.DatabaseQuestionUtility;
import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.MenuItem;

import java.util.Arrays;

public class ListQuestionMenu extends ConsoleMenu {

	public ListQuestionMenu(final ConsoleMenu previousMenu) {
		super("Maze Questions", previousMenu);

		DatabaseQuestionUtility database = new DatabaseQuestionUtility();

		Arrays.asList(database.loadQuestionSet())
		      .forEach(question -> addMenuItem(new MenuItem(question.getQuestion(), () -> new EditQuestionMenu(question, this).open())));
	}

	@Override
	protected String getDescription() {
		return "Select a question by its number, or\n" +
		       "add a new question by entering  +\n";
	}

}

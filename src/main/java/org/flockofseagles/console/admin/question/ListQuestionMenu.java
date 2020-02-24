package org.flockofseagles.console.admin.question;

import org.flockofseagles.DatabaseQuestionUtility;
import org.flockofseagles.Question;
import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.MenuItem;

import java.util.Arrays;
import java.util.List;

public class ListQuestionMenu extends ConsoleMenu {

	public ListQuestionMenu(final ConsoleMenu previousMenu) {
		super("Maze Questions", previousMenu);

		DatabaseQuestionUtility database  = new DatabaseQuestionUtility();
		List<Question>          questions = Arrays.asList(database.loadQuestionSet());

		questions.forEach(question -> {
			if (question != null) {
				addMenuItem(new MenuItem(question.getQuestion(), () -> new EditQuestionMenu(question, this).open()));
			}
		});

		addMenuItem(new MenuItem("Add New Item", () -> System.out.println("Not Yet Implemented")));
	}

	@Override
	public ConsoleMenu reload() {
		return new ListQuestionMenu(getPreviousMenu());
	}

}

package org.flockofseagles.console.admin.question;

import org.flockofseagles.DatabaseQuestionUtility;
import org.flockofseagles.Question;
import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.InputWrapper;
import org.flockofseagles.console.MenuItem;

import java.util.List;

public class ListQuestionMenu extends ConsoleMenu {

	public ListQuestionMenu(final ConsoleMenu previousMenu) {
		super("Maze Questions", previousMenu);

		DatabaseQuestionUtility database  = new DatabaseQuestionUtility();
		List<Question>          questions = database.loadQuestionSet();

		questions.forEach(question -> {
			if (question != null) {
				addMenuItem(new MenuItem(question.getQuestion(), () -> new EditQuestionMenu(question, this).open()));
			}
		});

		addMenuItem(new MenuItem("Add New Item", () -> {
			var question = new Question(new String[]{}, 1, InputWrapper.readString("Enter the question: "));
			new DatabaseQuestionUtility().addQuestion(question.getQuestion(), question.getPossibleAnswers());
			new AddQuestionMenu(question, this).open();
		}));
	}

	@Override
	public ConsoleMenu reload() {
		return new ListQuestionMenu(getPreviousMenu());
	}

}

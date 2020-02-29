package org.flockofseagles.console.admin.question.answer;
/* Created by GamerBah on 2/16/20 */

import org.flockofseagles.Question;
import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.MenuItem;

import java.util.Arrays;

public class ListAnswersMenu extends ConsoleMenu {

	private final Question question;

	public ListAnswersMenu(final Question question, final ConsoleMenu previousMenu) {
		super("Editing Answers", previousMenu);
		this.question = question;
		var answers = Arrays.asList(question.getPossibleAnswers());

		answers.forEach(s -> {
			if (s != null) {
				addMenuItem(new MenuItem(s, () -> new EditAnswerMenu(question, s, this).open()));
			}
		});

		addMenuItem(new MenuItem("Add New Item", () -> new EditAnswerMenu(question, "", this)));
	}

	@Override
	public ConsoleMenu reload() {
		return new ListAnswersMenu(question, getPreviousMenu());
	}

	@Override
	protected String getDescription() {
		return "\"" + question.getQuestion() + "\"" + "\n" +
		       "\n" +
		       "Select a question by its number, or\n" +
		       "add a new question by entering  +\n";
	}

}

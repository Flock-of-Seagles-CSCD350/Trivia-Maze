package org.flockofseagles.console.admin.question.answer;
/* Created by GamerBah on 3/1/20 */

import org.flockofseagles.DatabaseQuestionUtility;
import org.flockofseagles.Question;
import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.InputWrapper;
import org.flockofseagles.console.admin.AdminMenu;
import org.flockofseagles.console.admin.question.EditQuestionMenu;
import org.flockofseagles.console.admin.question.ListQuestionMenu;

public class AddAnswerMenu extends ConsoleMenu {

	private final Question question;

	public AddAnswerMenu(final Question question) {
		super("Answer Editor");
		this.question = question;
	}

	@Override
	protected String getDescription() {
		return "\"" + question.getQuestion() + "\"\n";
	}

	@Override
	public ConsoleMenu reload() {
		return null;
	}

	@Override
	public int open() {
		System.out.println("\nEnter the text for each answer, and then hit enter.");
		System.out.println("You'll return to the previous menu once 4 answers are entered.");

		DatabaseQuestionUtility database = new DatabaseQuestionUtility();
		for (int x = 1; x <= 4; x++) {
			database.addAnswer(question.getQuestion(), InputWrapper.readString("Enter answer text (" + x + "/4): "));
		}

		new EditQuestionMenu(question, new ListQuestionMenu(new AdminMenu())).open();
		return 0;
	}

}

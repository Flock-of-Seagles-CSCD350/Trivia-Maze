package org.flockofseagles.console.admin;
/* Created by GamerBah on 2/16/20 */

import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.MenuItem;

import java.util.Scanner;

public class EditQuestionMenu extends ConsoleMenu {

	private String question;

	public EditQuestionMenu(final String question, final ConsoleMenu previousMenu, final Scanner input) {
		super("Question Editor", previousMenu, input);
		this.question = question;

		menuItems.add(new MenuItem("Edit Question Name", () -> System.out.println("NYI")));
		menuItems.add(new MenuItem("Edit Question Answers", () -> System.out.println("NYI")));
		menuItems.add(new MenuItem("Delete Question", () -> System.out.println("NYI")));
		menuItems.add(new MenuItem("Go Back", previousMenu::open));
	}

	@Override
	protected String getDescription() {
		return "\"" + question + "\"\n";
	}

}

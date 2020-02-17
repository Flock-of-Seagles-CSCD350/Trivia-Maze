package org.flockofseagles.console.admin;
/* Created by GamerBah on 2/12/20 */

import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.MenuItem;
import org.flockofseagles.console.admin.question.ListQuestionMenu;

import java.util.Scanner;

public class AdminMenu extends ConsoleMenu {

	public AdminMenu(final Scanner input) {
		super("Admin Console", input);

		addMenuItem(new MenuItem("View Maze Questions", () -> new ListQuestionMenu(this, input).open()));
	}

}

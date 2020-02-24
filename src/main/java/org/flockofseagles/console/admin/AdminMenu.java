package org.flockofseagles.console.admin;
/* Created by GamerBah on 2/12/20 */

import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.MenuItem;
import org.flockofseagles.console.admin.question.ListQuestionMenu;

public class AdminMenu extends ConsoleMenu {

	public AdminMenu() {
		super("Admin Console");

		addMenuItem(new MenuItem("View Maze Questions", () -> new ListQuestionMenu(this).open(true)));
		addMenuItem(new MenuItem("Exit", () -> System.exit(0)));
	}

	@Override
	public ConsoleMenu reload() {
		return this;
	}

}

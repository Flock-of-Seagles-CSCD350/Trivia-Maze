package org.flockofseagles.console.admin;
/* Created by GamerBah on 2/12/20 */

import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.MenuItem;

import java.util.Scanner;

public class AdminMenu extends ConsoleMenu {

	public AdminMenu(final Scanner input) {
		super("Admin Console", input);

		menuItems.add(new MenuItem("View Maze Questions",
		                           () -> new ListQuestionMenu(input).open()));
	}

}

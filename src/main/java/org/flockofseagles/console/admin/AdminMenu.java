package org.flockofseagles.console.admin;
/* Created by GamerBah on 2/12/20 */

import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.MenuItem;

public class AdminMenu extends ConsoleMenu {

	public AdminMenu() {
		super("Admin Console");

		menuItems.add(new MenuItem("View Maze Questions", () -> new QuestionMenu().open()));
	}


}

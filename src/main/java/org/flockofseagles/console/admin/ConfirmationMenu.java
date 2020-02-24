package org.flockofseagles.console.admin;
/* Created by GamerBah on 2/23/20 */

import org.flockofseagles.console.ConsoleMenu;
import org.flockofseagles.console.MenuItem;

public class ConfirmationMenu extends ConsoleMenu {

	public ConfirmationMenu(ConsoleMenu previousMenu) {
		super("Action Confirmation", previousMenu);

		addMenuItem(new MenuItem("Yes"));
		addMenuItem(new MenuItem("No"));
	}

	@Override
	protected String getDescription() {
		return "Are you sure you want to continue?\n" +
		       "This action cannot be undone!\n";
	}

	@Override
	public ConsoleMenu reload() {
		return this;
	}

}

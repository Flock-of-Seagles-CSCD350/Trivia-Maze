package org.flockofseagles.console;
/* Created by GamerBah on 2/12/20 */

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class ConsoleMenu {

	@Getter
	private final String      title;
	@Getter
	private final ConsoleMenu previousMenu;

	@Getter
	private final ArrayList<MenuItem> menuItems = new ArrayList<>();

	public ConsoleMenu(final String title, final ConsoleMenu previousMenu) {
		this.title        = title;
		this.previousMenu = previousMenu;
	}

	public ConsoleMenu(final String title) {
		this(title, null);
	}

	public int open(final boolean sort) {
		if (sort) {
			menuItems.sort(MenuItem::compareTo);
		}

		if (previousMenu != null) {
			menuItems.removeIf(menuItem -> menuItem.getTitle().equals("Go Back"));
			menuItems.add(new MenuItem("Go Back", previousMenu::open));
		}
		System.out.println();
		System.out.println("\u2B16 " + title + " \u2B17");
		System.out.print(getDescription());
		menuItems.forEach(menuItem -> {
			if (menuItem.getTitle().equals("Go Back") || menuItem.getTitle().equals("Add New Item")) {
				System.out.println();
			}
			int index = menuItems.indexOf(menuItem) + 1;
			System.out.printf("    %s%d \u2B9E %s\n", index < 10 ? " " : "", index, menuItem.getTitle());
		});
		int choice = InputWrapper.readMenuChoice("Make a selection: ", menuItems.size());
		menuItems.get(choice - 1).select();
		return choice;
	}

	public int open() {
		return open(false);
	}

	public abstract ConsoleMenu reload();

	protected void addMenuItem(final MenuItem menuItem) {
		menuItems.add(menuItem);
	}

	protected void addMenuItem(final MenuItem... menuItems) {
		this.menuItems.addAll(Arrays.asList(menuItems));
	}

	protected String getDescription() {
		return "";
	}

}

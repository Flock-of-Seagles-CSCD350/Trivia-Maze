package org.flockofseagles.console;
/* Created by GamerBah on 2/12/20 */

import java.util.ArrayList;
import java.util.Scanner;

public abstract class ConsoleMenu {

	protected final String title;

	private final ConsoleMenu previousMenu;
	private final Scanner     input;

	protected final ArrayList<MenuItem> menuItems = new ArrayList<>();

	public ConsoleMenu(final String title, final ConsoleMenu previousMenu, final Scanner input) {
		this.title        = title;
		this.previousMenu = previousMenu;
		this.input        = input;
	}

	public ConsoleMenu(final String title, final Scanner input) {
		this(title, null, input);
	}

	public void open() {
		System.out.println();
		System.out.println("\u2B16 " + title + " \u2B17");
		System.out.print(getDescription());
		menuItems.forEach(menuItem -> System.out.printf("     %d \u2B9E %s\n", menuItems.indexOf(menuItem) + 1, menuItem.getTitle()));
		int choice = InputWrapper.readMenuChoice("Make a selection: ", menuItems.size());
		menuItems.get(choice - 1).select();
	}

	protected String getDescription() {
		return "";
	}

}

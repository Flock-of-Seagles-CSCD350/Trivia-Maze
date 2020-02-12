package org.flockofseagles.console;
/* Created by GamerBah on 2/12/20 */

import java.util.ArrayList;
import java.util.Scanner;

public abstract class ConsoleMenu {

    private final Scanner input = new Scanner(System.in);

    private final String name;
    private final ConsoleMenu previousMenu;

    protected final ArrayList<MenuItem> menuItems = new ArrayList<>();

    public ConsoleMenu(final String name, final ConsoleMenu previousMenu) {
        this.name = name;
        this.previousMenu = previousMenu;
    }

    public ConsoleMenu(final String name) {
        this(name, null);
    }

    public void open() {
        System.out.println();
        menuItems.forEach(menuItem -> System.out.printf("%d) %s", menuItems.indexOf(menuItem), menuItem.getTitle()));
    }

}

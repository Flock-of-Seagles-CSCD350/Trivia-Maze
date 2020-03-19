package org.flockofseagles.console;
/* Created by GamerBah on 2/16/20 */

import java.util.Scanner;

public final class InputWrapper {

	private static final Scanner INPUT = new Scanner(System.in);

	public static int readMenuChoice(final String message, final int upperBound) {
		System.out.print(message);
		String choice = INPUT.next();
		while (!choice.matches("[0-9]+") || (choice.matches("[0-9]+")
		                                     && Integer.parseInt(choice) < 0
		                                     || (Integer.parseInt(choice) > upperBound && upperBound != -1))) {
			System.out.println("\u274C That's not a valid selection! \u274C\n");
			System.out.print(message);
			choice = INPUT.next();
		}
		INPUT.nextLine();
		return Integer.parseInt(choice);
	}

	public static int readMenuChoice(final String message) {
		return readMenuChoice(message, -1);
	}

	public static String readString(final String message) {
		System.out.print(message);
		String string = INPUT.nextLine();
		while (string.isEmpty()) {
			System.out.println("\u274C That's not a valid entry! \u274C\n");
			System.out.print(message);
			string = INPUT.next();
		}
		return string;
	}

}

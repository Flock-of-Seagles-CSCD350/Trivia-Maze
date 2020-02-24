package org.flockofseagles;

import org.flockofseagles.console.admin.AdminMenu;

import java.util.Scanner;

public class TriviaMaze {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		new AdminMenu().open();
	}

}

package org.flockofseagles;

import org.flockofseagles.console.admin.AdminMenu;
import org.flockofseagles.ui.GUI;
import org.flockofseagles.ui.OptionsLayoutController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TriviaMaze {

	public static void main(String[] args) {
		if (args.length > 0 && args[0].equals("--admin")) {
			new AdminMenu().open();
		} else {
			GUI.main(args);
		}
	}

	public static void saveGame() {
		try {
			FileOutputStream   outputStream       = new FileOutputStream("savegame.dat");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(OptionsLayoutController.field);
			objectOutputStream.close();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			// TODO: Error in GUI
		}
		// TODO: Successful save message in GUI
	}

}

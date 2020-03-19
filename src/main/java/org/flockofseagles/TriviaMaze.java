package org.flockofseagles;

import lombok.Getter;
import lombok.Setter;
import org.flockofseagles.console.admin.AdminMenu;
import org.flockofseagles.ui.GUI;
import org.flockofseagles.util.DataStore;
import org.flockofseagles.util.SaveGame;

import java.io.*;

public class TriviaMaze {

	@Getter
	@Setter
	private static SaveGame saveGame;

	public static void main(String[] args) {
		if (args.length > 0 && args[0].equals("--admin")) {
			new AdminMenu().open();
		} else {
			GUI.main(args);
		}
	}

	public static void saveGame(DataStore data, int slot) {
		var save = new SaveGame(data, slot);
		save.save();
		saveGame = save;
	}

	public static boolean deleteSavedGame(int slot) {
		File file = new File(String.format("save_%d.dat", slot));
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}

	public static SaveGame[] getSaves() {
		var saves = new SaveGame[5];
		for (int x = 0; x < 5; x++) {
			File file = new File(String.format("save_%d.dat", x));
			if (file.exists()) {
				saves[x] = new SaveGame(readData(file.getName()), x);
			}
		}
		return saves;
	}

	private static DataStore readData(final String file) {
		DataStore load = null;
		try {
			FileInputStream   inputStream       = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			try {
				load = (DataStore) objectInputStream.readObject();
			} catch (InvalidClassException e) {
				return null;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.err.println("Unable to load save from " + file + ". Maybe the data is corrupted?");
			}
			objectInputStream.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Unable to access save from " + file + ".");
		}
		return load;
	}

}

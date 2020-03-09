package org.flockofseagles;

import lombok.Getter;
import lombok.Setter;
import org.flockofseagles.console.admin.AdminMenu;
import org.flockofseagles.ui.GUI;
import org.flockofseagles.util.DataStore;
import org.flockofseagles.util.SaveGame;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;

public class TriviaMaze {

	@Getter
	@Setter
	private SaveGame saveGame;

	public static void main(String[] args) {
		if (args.length > 0 && args[0].equals("--admin")) {
			new AdminMenu().open();
		} else {
			GUI.main(args);
		}
	}

	public static void saveGame(DataStore data) {
		new SaveGame(data).save();
	}

	public static DataStore getLastSave() {
		DataStore load = null;
		try {
			FileInputStream   inputStream       = new FileInputStream("savegame.dat");
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			try {
				load = (DataStore) objectInputStream.readObject();
			} catch (InvalidClassException e) {
				return null;
			}
			objectInputStream.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("Dungeon class not found");
			e.printStackTrace();
		}
		return load;
	}

}

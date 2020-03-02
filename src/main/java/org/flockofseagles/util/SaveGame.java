package org.flockofseagles.util;
/* Created by GamerBah on 3/1/20 */

import lombok.Getter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveGame implements SaveData {

	@Getter
	private DataStore data;

	public SaveGame(DataStore data) {
		this.data = data;
	}

	@Override
	public void save() {
		try {
			FileOutputStream   outputStream       = new FileOutputStream("savegame.dat");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(data);
			objectOutputStream.close();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			// TODO: Error in GUI
		}
		// TODO: Successful save message in GUI
	}

	@Override
	public void delete() {

	}

	public static void load(int slot) {
		System.err.println("Not Yet Implemented");
	}

}

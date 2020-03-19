package org.flockofseagles.util;
/* Created by GamerBah on 3/1/20 */

import lombok.Getter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.Instant;

public class SaveGame implements SaveData {

	@Getter
	private DataStore data;
	@Getter
	private int       slot;
	@Getter
	private Instant   date;

	public SaveGame(DataStore data, int slot) {
		this.data = data;
		this.slot = slot;
	}

	@Override
	public Result save() {
		try {
			var fileName = String.format("save_%d.dat", slot);

			File file = new File(fileName);
			// Make sure the file doesn't exist so we don't overwrite anything accidentally
			if (file.exists()) {

			}
			FileOutputStream   outputStream       = new FileOutputStream(fileName);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(data);
			objectOutputStream.close();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			return Result.FAIL;
		}
		return Result.OKAY;
	}

	@Override
	public void delete() {

	}

	public static void load(int slot) {
		System.err.println("Not Yet Implemented");
	}

}

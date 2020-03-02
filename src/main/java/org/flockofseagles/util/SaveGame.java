package org.flockofseagles.util;
/* Created by GamerBah on 3/1/20 */

import lombok.Getter;
import org.flockofseagles.TriviaMaze;

public class SaveGame implements SaveData {

	@Getter
	private TriviaMaze savedGame;

	public SaveGame(TriviaMaze savedGame) {
		this.savedGame = savedGame;
	}

	@Override
	public void save() {

	}

	@Override
	public void delete() {

	}

}

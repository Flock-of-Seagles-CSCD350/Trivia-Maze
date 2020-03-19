package org.flockofseagles.util;
/* Created by GamerBah on 3/1/20 */

import lombok.Getter;
import lombok.Setter;
import org.flockofseagles.Question;
import org.flockofseagles.ui.Player;
import org.flockofseagles.ui.Wall;
import org.flockofseagles.ui.util.Difficulty;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

public final class DataStore implements Serializable {

	@Getter
	private final int width, height;
	@Getter
	private final Player                     player;
	@Getter
	private final HashMap<Question, Boolean> questions;
	@Getter
	private final Difficulty                 difficulty;
	@Getter
	private final ArrayList<Wall>            walls = new ArrayList<>();

	@Getter
	@Setter
	private Instant lastSave = Instant.EPOCH;

	public DataStore(final int width, final int height, final Player player, final HashMap<Question, Boolean> questions,
			final Difficulty difficulty) {
		this.width      = width;
		this.height     = height;
		this.player     = player;
		this.questions  = questions;
		this.difficulty = difficulty;
	}

}

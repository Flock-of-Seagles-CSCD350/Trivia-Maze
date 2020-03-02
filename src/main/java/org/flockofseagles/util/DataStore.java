package org.flockofseagles.util;
/* Created by GamerBah on 3/1/20 */

import lombok.Getter;
import org.flockofseagles.Question;
import org.flockofseagles.ui.Player;

import java.io.Serializable;
import java.util.HashMap;

public final class DataStore implements Serializable {

	@Getter
	private final int width, height;
	@Getter
	private final Player                     player;
	@Getter
	private final HashMap<Question, Boolean> questions;

	public DataStore(final int width, final int height, final Player player, final HashMap<Question, Boolean> questions) {
		this.width     = width;
		this.height    = height;
		this.player    = player;
		this.questions = questions;
	}

}

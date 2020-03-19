package org.flockofseagles.ui.util;
/* Created by GamerBah on 3/8/20 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Difficulty {

	EASY(9),
	MEDIUM(15),
	HARD(21);

	private int size;

}

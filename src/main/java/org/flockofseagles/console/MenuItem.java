package org.flockofseagles.console;
/* Created by GamerBah on 2/12/20 */

public class MenuItem implements MenuRunnable, Comparable<MenuItem> {

	private final String   title;
	private final Runnable event;

	public MenuItem(final String title, final Runnable event) {
		this.title = title;
		this.event = event;
	}

	public MenuItem(final String title) {
		this(title, () -> {
		});
	}

	public String getTitle() {
		return title;
	}

	public void select() {
		event.run();
	}

	@Override
	public int compareTo(MenuItem other) {
		return title.compareTo(other.title);
	}

}

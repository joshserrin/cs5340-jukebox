package org.jukebox.model;

/**
 * @author jserrin
 */
public class Album {
	private final String title;

	public Album(String title) {
		if (null == title) {
			throw new IllegalArgumentException("title cannot be null");
		}
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return title;
	}
}

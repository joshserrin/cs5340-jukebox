package org.jukebox.model;

/**
 * @author jserrin
 */
public class Album {
	private final String title;
	private final Genre genre;

	public Album(String title, Genre genre) {
		if (null == title) {
			throw new IllegalArgumentException("title cannot be null");
		}
		if (null == genre) {
			throw new IllegalArgumentException("genre cannot be null");
		}
		this.genre = genre;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return title;
	}

	public Genre getGenre() {
		return genre;
	}
}

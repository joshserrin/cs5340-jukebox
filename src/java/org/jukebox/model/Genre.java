package org.jukebox.model;

public class Genre {
	private final String name;

	public Genre(String name) {
		if (null == name) {
			throw new IllegalArgumentException("name cannot be null");
		}
		this.name = name;
	}

	public String getName() {
		return name;
	}
}

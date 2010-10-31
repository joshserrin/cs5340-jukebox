package org.jukebox.model;

/**
 * @author jserrin
 */
public class Artist {
	private final String name;

	public Artist(String name) {
		if (null == name) {
			throw new IllegalArgumentException("name cannot be null");
		}
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}

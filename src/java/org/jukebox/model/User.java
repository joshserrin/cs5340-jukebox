package org.jukebox.model;

/**
 * @author jserrin
 */
public class User {
	private final String name;
	private final Playlist mySongs;

	public User(String name) {
		if (null == name) {
			throw new IllegalArgumentException("name cannot be null");
		}
		this.name = name;
		this.mySongs = new Playlist();
	}

	public String getName() {
		return name;
	}

	public Playlist getPlaylist() {
		return mySongs;
	}
}

package org.jukebox.model;

/**
 * @author jserrin
 */
public class Request {
	private final Song song;

	public Request(Song song) {
		if (null == song) {
			throw new IllegalArgumentException("song cannot be null");
		}
		this.song = song;
	}

	public Song getSong() {
		return song;
	}
}

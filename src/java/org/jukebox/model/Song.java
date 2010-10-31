package org.jukebox.model;

/**
 * @author jserrin
 */
public class Song {
	private final String title;
	private final Artist artist;
	private final Album album;

	public Song(String title, Artist artist, Album album) {
		if (null == title) {
			throw new IllegalArgumentException("title cannot be null");
		}
		if (null == artist) {
			throw new IllegalArgumentException("artist cannot be null");
		}
		if (null == album) {
			throw new IllegalArgumentException("album cannot be null");
		}
		this.title = title;
		this.artist = artist;
		this.album = album;
	}

	public String getTitle() {
		return title;
	}

	public Artist getArtist() {
		return artist;
	}

	public Album getAlbum() {
		return album;
	}
}

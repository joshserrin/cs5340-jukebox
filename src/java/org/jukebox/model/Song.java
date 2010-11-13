package org.jukebox.model;

import java.net.URL;

/**
 * @author jserrin
 */
public class Song {
	private final String title;
	private final Artist artist;
	private final Album album;
	private final URL url;

	public Song(String title, Artist artist, Album album, URL url) {
		if (null == title) {
			throw new IllegalArgumentException("title cannot be null");
		}
		if (null == artist) {
			throw new IllegalArgumentException("artist cannot be null");
		}
		if (null == album) {
			throw new IllegalArgumentException("album cannot be null");
		}
		if (null == url) {
			throw new IllegalArgumentException("null argument");
		}
		this.url = url;
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

	public URL getURL() {
		return url;
	}

	@Override
	public String toString() {
		return "Song [title=" + title + ", artist=" + artist + ", album="
				+ album + ", url=" + url + "]";
	}
}

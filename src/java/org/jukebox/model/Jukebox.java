package org.jukebox.model;

import java.util.List;

public class Jukebox {
	private final Library library;
	private final Playlist playlist;

	public Jukebox(Library lib) {
		if (null == lib) {
			throw new IllegalArgumentException("lib cannot be null");
		}
		this.library = lib;
		this.playlist = new Playlist();
	}

	public void addSongToPlaylist(Song song) {
		if (null == song) {
			throw new IllegalArgumentException("song cannot be null");
		}
		System.out.println("\"" + song.getTitle()
				+ "\" has been added to the playlist");
		playlist.add(song);
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public Song randomSong() {
		return library.randomSong();
	}

	public List<Song> allSongsSortedbyArtist() {
		return library.allSongsSortedByArtist();
	}
}

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

	public void addSongToPlaylist(Request request) {
		if (null == request) {
			throw new IllegalArgumentException("request cannot be null");
		}
		System.out.println("\"" + request.getSong().getTitle()
				+ "\" has been added to the playlist");
		playlist.add(request);
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public Request randomRequest() {
		return new Request(library.randomSong());
	}

	public List<Song> allSongsSortedbyArtist() {
		return library.allSongsSortedByArtist();
	}
}

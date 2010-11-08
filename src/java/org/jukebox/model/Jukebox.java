package org.jukebox.model;

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

	/**
	 * Searching for null or an empty string will produce all the songs in the
	 * jukebox
	 * 
	 * @param searchString
	 * @return
	 */
	public SearchResults searchForSongMatching(String searchString) {
		if (null == searchString || searchString.trim().equals("")) {
			return new AllSongs(library);
		}
		return new LimitedResults(library, new SearchParameters(searchString));
	}

	public SearchResults allSongs() {
		return new AllSongs(library);
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
}

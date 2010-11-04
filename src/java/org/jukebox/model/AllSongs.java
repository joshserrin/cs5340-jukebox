package org.jukebox.model;

public class AllSongs implements SearchResults {
	private final Library library;

	public AllSongs(Library library) {
		if (null == library) {
			throw new IllegalArgumentException("library cannot be null");
		}
		this.library = library;
	}

	@Override
	public Song get(int rowIndex) {
		return library.songAtIndex(rowIndex);
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	public int size() {
		return library.size();
	}
}

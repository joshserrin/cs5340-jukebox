package org.jukebox.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class LimitedResults implements SearchResults {
	private final List<Song> songs;

	public LimitedResults(Library library, SearchParameters searchString) {
		if (null == library) {
			throw new IllegalArgumentException("library cannot be null");
		}
		if (null == searchString) {
			throw new IllegalArgumentException("searchString cannot be null");
		}
		this.songs = new CopyOnWriteArrayList<Song>(
				library.searchAll(searchString));
	}

	@Override
	public Song get(int i) {
		return songs.get(i);
	}

	@Override
	public boolean isEmpty() {
		return songs.isEmpty();
	}

	@Override
	public int size() {
		return songs.size();
	}

}

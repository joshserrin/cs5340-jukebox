package org.jukebox.model;

import java.util.NoSuchElementException;

public class NoSongs implements SearchResults {
	public boolean isEmpty() {
		return true;
	}

	public int size() {
		return 0;
	}

	public Song get(int i) {
		throw new NoSuchElementException(i + " is not contained!");
	}
}

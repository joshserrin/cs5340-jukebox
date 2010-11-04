package org.jukebox.model;

public interface SearchResults {
	public boolean isEmpty();

	public int size();

	public Song get(int rowIndex);
}

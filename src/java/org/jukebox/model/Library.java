package org.jukebox.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Library {
	private final List<Song> songs;

	private final Random r = new Random();

	public Library(Collection<Song> songs) {
		if (null == songs) {
			throw new IllegalArgumentException("songs cannot be null");
		}
		// Make sure you copy the list
		this.songs = Collections.synchronizedList(new ArrayList<Song>(songs));
	}

	public Song randomSong() {
		int i = r.nextInt(songs.size());
		return songs.get(i);
	}

	public List<Song> allSongsSortedByArtist() {
		List<Song> copy = new ArrayList<Song>(songs);
		Collections.sort(copy, new Comparator<Song>() {
			@Override
			public int compare(Song o1, Song o2) {
				return o1.getArtist().getName()
						.compareTo(o2.getArtist().getName());
			}
		});
		return Collections.unmodifiableList(copy);
	}
}

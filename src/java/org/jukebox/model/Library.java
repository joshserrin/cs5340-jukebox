package org.jukebox.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Library {
	private final List<Song> songs;

	public Library(Collection<Song> songs) {
		if (null == songs) {
			throw new IllegalArgumentException("songs cannot be null");
		}
		// Make sure you copy the list
		this.songs = Collections.synchronizedList(new ArrayList<Song>(songs));
	}

	public Collection<Song> searchAll(SearchParameters params) {
		if (null == params) {
			throw new IllegalArgumentException("params cannot be null");
		}
		Collection<Song> coll = new ArrayList<Song>();
		for (Song s : songs) {
			if (hasArtistContaining(s, params)
					|| hasSongTitleContaining(s, params)
					|| hasAlbumContaining(s, params)) {
				coll.add(s);
			}
		}
		return coll;
	}

	private boolean hasAlbumContaining(Song song, SearchParameters params) {
		assert null != song;
		assert null != params;
		String a = params.getSearchString().toLowerCase();
		return song.getAlbum().getTitle().toLowerCase().contains(a);
	}

	private boolean hasSongTitleContaining(Song song, SearchParameters params) {
		assert null != song;
		assert null != params;
		String t = params.getSearchString().toLowerCase();
		return song.getTitle().toLowerCase().contains(t);
	}

	private boolean hasArtistContaining(Song song, SearchParameters params) {
		assert null != song;
		assert null != params;
		String a = params.getSearchString().toLowerCase();
		return song.getArtist().getName().toLowerCase().contains(a);
	}

	public int size() {
		return songs.size();
	}

	public Song songAtIndex(int i) {
		return songs.get(i);
	}

	private final Random r = new Random(47);

	public Song randomSong() {
		int i = r.nextInt(songs.size());
		return songs.get(i);
	}
}

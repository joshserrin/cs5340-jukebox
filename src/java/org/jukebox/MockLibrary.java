package org.jukebox;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.jukebox.model.Album;
import org.jukebox.model.Artist;
import org.jukebox.model.Genre;
import org.jukebox.model.Library;
import org.jukebox.model.Song;

public class MockLibrary {

	public static Library create() {
		return new Library(constructSongs(20));
	}

	private static Collection<Song> constructSongs(int count) {
		Collection<Song> songs = new ArrayList<Song>();
		Random r = new Random(47);
		for (int i = 0; i < count; i++) {
			// The library is sorted on the artist's name so we don't want the
			// first character to always be the same.
			Artist artist = new Artist(new BigInteger(3, r).toString()
					+ "-artist");
			// There should only be a small amount of Genre's. For the mock
			// library, 5 should be enough.
			Genre genre = new Genre("Genre" + (i % 5));
			Album album = new Album("Album" + i, genre);
			Song s = new Song("Song" + i, artist, album);
			songs.add(s);
		}
		return songs;
	}
}

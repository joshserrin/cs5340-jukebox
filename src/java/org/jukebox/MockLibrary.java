package org.jukebox;

import java.util.Arrays;

import org.jukebox.model.Album;
import org.jukebox.model.Artist;
import org.jukebox.model.Genre;
import org.jukebox.model.Library;
import org.jukebox.model.Song;

public class MockLibrary {

	public static Library create() {
		return new Library(Arrays.asList(becauseOfYou(), song2()));
	}

	private static Song becauseOfYou() {
		return new Song("Because Of You", new Artist("Tony Bennett"),
				new Album("Album", new Genre("genre")));
	}

	private static Song song2() {
		return new Song("Song2", new Artist("Artist2"), new Album("Album2",
				new Genre("genre")));
	}
}

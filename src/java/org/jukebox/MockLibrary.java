package org.jukebox;

import java.io.File;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.jukebox.model.Album;
import org.jukebox.model.Artist;
import org.jukebox.model.Genre;
import org.jukebox.model.Library;
import org.jukebox.model.Song;

public class MockLibrary {

	public static Library create() {
		return new Library(constructSongs(40));
	}

	// All songs should be contained in the Music directory of the project
	private static final List<String> all = new ArrayList<String>();
	static {
		File music = new File("Music");
		File[] files = music.listFiles();
		for (File f : files) {
			if (!f.isDirectory()) {
				System.out.println(f);
				all.add(f.getName());
			}
		}
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
			File file = randomSong(all);
			String title = file.getName();
			try {
				songs.add(new Song(title, artist, album, file.toURI().toURL()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		return songs;
	}

	private static final Random r = new Random(47);

	private static File randomSong(List<String> available) {
		return new File("Music/" + available.get(r.nextInt(available.size())));
	}
}

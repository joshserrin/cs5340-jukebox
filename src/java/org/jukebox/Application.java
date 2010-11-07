package org.jukebox;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jukebox.model.Album;
import org.jukebox.model.Artist;
import org.jukebox.model.Genre;
import org.jukebox.model.Jukebox;
import org.jukebox.model.Library;
import org.jukebox.model.Song;
import org.jukebox.ui.JukeboxPanel;

public class Application {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JPanel mainPanel = new JukeboxPanel(new Jukebox(library()),
						new Dimension(1100, 700));
				final JFrame frame = new JFrame("Jukebox");
				frame.setLayout(new BorderLayout());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(mainPanel);
				frame.pack();
				frame.setLocationRelativeTo(null); // display in middle of
													// screen
				frame.setVisible(true);
			}
		});
	}

	// TODO load the library
	private static Library library() {
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

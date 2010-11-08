package org.jukebox.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jukebox.model.Song;

public class SongPanel extends JPanel {

	public SongPanel(Song song) {
		if (null == song) {
			throw new IllegalArgumentException("song cannot be null");
		}
		JLabel songTitle = new JLabel("\"" + song.getTitle() + "\"");
		JLabel by = new JLabel("by");
		JLabel artist = new JLabel(song.getArtist().getName());
		JLabel album = new JLabel(song.getAlbum().getTitle());
		JLabel genre = new JLabel("(" + song.getAlbum().getGenre().getName()
				+ ")");

		songTitle.setFont(new Font(UIConstants.getDefaultFontName(),
				Font.PLAIN, UIConstants.getMediumFontSize()));
		by.setFont(new Font(UIConstants.getDefaultFontName(), Font.PLAIN,
				UIConstants.getSmallFontSize()));
		artist.setFont(new Font(UIConstants.getDefaultFontName(), Font.PLAIN,
				UIConstants.getMediumFontSize()));
		album.setFont(new Font(UIConstants.getDefaultFontName(), Font.ITALIC,
				UIConstants.getSmallFontSize()));

		JPanel titleAndArtist = new JPanel(new FlowLayout());
		titleAndArtist.add(songTitle);
		titleAndArtist.add(by);
		titleAndArtist.add(artist);

		JPanel albumAndGenre = new JPanel(new FlowLayout());
		albumAndGenre.add(album);
		albumAndGenre.add(genre);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(titleAndArtist);
		this.add(albumAndGenre);

		for (JComponent c : Arrays.<JComponent> asList(this, songTitle, by,
				artist, album, genre)) {
			c.setBackground(Color.white);
			c.setOpaque(true);
		}

		this.setBorder(BorderFactory.createRaisedBevelBorder());
	}

}

package org.jukebox.ui;

import java.awt.BorderLayout;
import static org.jukebox.ui.UIConstants.*;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jukebox.model.Song;
import org.jukebox.utils.Option;

public class SongPanel extends JPanel {

	public SongPanel(Song song) {
		if (null == song) {
			throw new IllegalArgumentException("song cannot be null");
		}
		JLabel songTitle = new JLabel(QUOTE + song.getTitle() + QUOTE);
		JLabel by = new JLabel("by");
		JLabel artist = new JLabel(song.getArtist().getName());
		JLabel album = new JLabel(song.getAlbum().getTitle());
		JLabel genre = new JLabel(OPEN_PAREN
				+ song.getAlbum().getGenre().getName() + CLOSE_PAREN);

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

		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		center.add(titleAndArtist);
		center.add(albumAndGenre);

		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setLayout(new BorderLayout());
		this.add(center, BorderLayout.CENTER);
		Option<ImageIcon> cover = song.getAlbum().getCover();
		if (cover.isSome()) {
			JLabel c = new JLabel(cover.get());
			this.add(c, BorderLayout.WEST);
		}
	}
}

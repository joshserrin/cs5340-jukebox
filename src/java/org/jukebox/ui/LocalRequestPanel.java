package org.jukebox.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.jukebox.model.Request;
import org.jukebox.model.Song;
import org.jukebox.utils.Option;

/**
 * @author jserrin
 */
public class LocalRequestPanel extends JPanel {
	private static final int FONT_SIZE = 14;

	public LocalRequestPanel(Request r) {
		this(Option.of(r));
	}

	public LocalRequestPanel() {
		this(Option.of((Request) null));
	}

	private LocalRequestPanel(Option<Request> r) {
		assert null != r;

		this.setBackground(Color.white);
		Border a = BorderFactory.createRaisedBevelBorder();
		Border b = BorderFactory.createLineBorder(r.isSome() ? Color.black
				: Color.gray); // disabled look
		Border c = BorderFactory.createCompoundBorder(a, b);
		this.setBorder(c);

		if (r.isSome()) {
			Song song = r.get().getSong();
			Component title = new SongTitlePanel(song);
			Component artist = new SongArtistPanel(song);
			Component album = new SongAlbumPanel(song);
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.add(title);
			this.add(artist);
			this.add(album);
		}
	}

	private class SongTitlePanel extends JLabel {
		public SongTitlePanel(Song song) {
			super("\"" + song.getTitle() + "\"");
			this.setFont(new Font(UIConstants.getDefaultFontName(), Font.PLAIN,
					FONT_SIZE));
		}
	}

	private class SongArtistPanel extends JLabel {
		public SongArtistPanel(Song song) {
			super(song.getArtist().getName());
			this.setFont(new Font(UIConstants.getDefaultFontName(), Font.PLAIN,
					FONT_SIZE));
		}
	}

	private class SongAlbumPanel extends JLabel {
		public SongAlbumPanel(Song song) {
			super(song.getAlbum().getTitle());
			this.setFont(new Font(UIConstants.getDefaultFontName(),
					Font.ITALIC, FONT_SIZE));
		}
	}
}

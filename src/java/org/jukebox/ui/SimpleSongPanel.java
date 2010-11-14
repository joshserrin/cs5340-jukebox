package org.jukebox.ui;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jukebox.model.Song;
import org.jukebox.utils.Option;
import static org.jukebox.ui.UIConstants.*;

public class SimpleSongPanel extends JPanel {
	public SimpleSongPanel(Song song) {
		if (null == song) {
			throw new IllegalArgumentException("song cannot be null");
		}

		JLabel title = new JLabel(QUOTE + song.getTitle() + QUOTE);
		JLabel artist = new JLabel(song.getArtist().getName());

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Option<ImageIcon> cover = song.getAlbum().getCover();
		if (cover.isSome()) {
			JLabel c = new JLabel(cover.get());
			this.add(c);
		}
		this.add(title);
		this.add(artist);
	}
}

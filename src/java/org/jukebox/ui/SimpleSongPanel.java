package org.jukebox.ui;

import static org.jukebox.ui.UIConstants.QUOTE;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jukebox.model.Request;
import org.jukebox.model.ShortStory;
import org.jukebox.model.Song;
import org.jukebox.utils.Option;

public class SimpleSongPanel extends JPanel {
	public SimpleSongPanel(Request request) {
		if (null == request) {
			throw new IllegalArgumentException("request cannot be null");
		}

		Song song = request.getSong();
		JLabel title = new JLabel(QUOTE + song.getTitle() + QUOTE);
		JLabel artist = new JLabel(song.getArtist().getName());

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Option<ImageIcon> cover = song.getAlbum().getCover();
		Option<ShortStory> storyOpt = request.getUserStory();
		if (cover.isSome()) {
			this.add(new JLabel(cover.get()));
		}
		this.add(title);
		this.add(artist);
		if (storyOpt.isSome()) {
			ShortStory story = storyOpt.get();
			this.add(new JLabel("Added by " + story.getUser()));
		}
	}
}

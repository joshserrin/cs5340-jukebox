package org.jukebox.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jukebox.model.Jukebox;
import org.jukebox.model.Player;
import org.jukebox.model.Player.PlayObserver;
import org.jukebox.model.Player.PlayingDetails;
import org.jukebox.model.Song;
import org.jukebox.utils.Option;

/**
 * @author jserrin
 */
public class PlayingPanel extends JPanel {
	private final Player player;

	public PlayingPanel(Jukebox jukebox) {
		if (null == jukebox) {
			throw new IllegalArgumentException("jukebox cannot be null");
		}
		this.player = new Player(jukebox);
		player.add(new DisplayNewSong());
		JLabel nowPlaying = new JLabel("Now Playing:");
		nowPlaying.setFont(new Font(UIConstants.getDefaultFontName(),
				Font.BOLD, UIConstants.getLargeFontSize()));

		this.setBackground(Color.GREEN);

		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.add(nowPlaying);

		// We want the application to start playing songs as soon as it starts
		// up
		player.play();
	}

	private class DisplayNewSong implements PlayObserver {
		private Option<Component> playingSongDisplay = Option.of(null);

		@Override
		public void newSongPlaying(final PlayingDetails details) {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					PlayingPanel pp = PlayingPanel.this;
					if (playingSongDisplay.isSome()) {
						pp.remove(playingSongDisplay.get());
					}
					SongPanel sp = new SongPanel(details.getSong());
					playingSongDisplay = Option.<Component> of(sp);
					pp.add(sp);
					pp.validate();
					pp.repaint();
				}
			});
		}
	}
}

package org.jukebox.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.jukebox.model.Player;
import org.jukebox.model.Player.PlayObserver;
import org.jukebox.model.Player.PlayingDetails;
import org.jukebox.utils.Option;

/**
 * @author jserrin
 */
public class PlayingPanel extends JPanel {
	private final SongTime songTime;

	public PlayingPanel(Player player) {
		if (null == player) {
			throw new IllegalArgumentException("player cannot be null");
		}
		this.songTime = new SongTime();
		player.add(new DisplayNewSong());
		JLabel nowPlaying = new JLabel("Now Playing:");
		nowPlaying.setFont(new Font(UIConstants.getDefaultFontName(),
				Font.BOLD, UIConstants.getLargeFontSize()));

		this.setBackground(Color.GREEN);

		this.setLayout(new BorderLayout());
		this.add(nowPlaying, BorderLayout.WEST);

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
					JPanel panel = new JPanel(new BorderLayout());
					panel.add(sp, BorderLayout.CENTER);
					songTime.newSongPlaying(details);
//					panel.add(songTime, BorderLayout.SOUTH);

					pp.add(sp);
					pp.validate();
					pp.repaint();
				}
			});
		}
	}

	private class SongTime extends JPanel {
		private final JProgressBar progress;

		public SongTime() {
			this.progress = new JProgressBar();
			this.setLayout(new BorderLayout());
			this.add(progress, BorderLayout.CENTER);
		}

		public void newSongPlaying(PlayingDetails details) {
		}
	}
}

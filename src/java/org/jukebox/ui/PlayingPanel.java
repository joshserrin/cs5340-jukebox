package org.jukebox.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.jukebox.model.Player;
import org.jukebox.model.Player.PlayObserver;
import org.jukebox.model.Player.PlayingDetails;
import org.jukebox.model.ShortStory;
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

		this.setBackground(new Color(0, 255, 0, 100));

		this.setLayout(new BorderLayout());
		this.add(nowPlaying, BorderLayout.WEST);
		this.add(songTime, BorderLayout.SOUTH);

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
					Option<ShortStory> sOpt = details.getStory();
					JPanel p = new JPanel(new FlowLayout());
					SongPanel sp = new SongPanel(details.getSong());
					p.add(sp);
					if (sOpt.isSome()) {
						StoryPanel stp = new StoryPanel(sOpt.get());
						p.add(stp);
					}
					playingSongDisplay = Option.<Component> of(p);

					pp.add(p);
					pp.validate();
					pp.repaint();
				}
			});
		}

		@Override
		public void songUpdate(PlayingDetails details) {
			songTime.updateTimes(details);
		}
	}

	private class SongTime extends JPanel {
		private final JProgressBar progress;

		public SongTime() {
			this.progress = new JProgressBar(0, 100);
			progress.setStringPainted(true);
			this.setLayout(new BorderLayout());
			this.add(progress, BorderLayout.CENTER);
		}

		public void updateTimes(PlayingDetails details) {
			int duration = details.getLengthSeconds();
			int played = details.getPlayedAmountSeconds();

			progress.setMinimum(0);
			progress.setValue(played);
			progress.setMaximum(duration);

			String l = toTime(duration);
			String p = toTime(played);

			progress.setString(p + " / " + l);
		}

		private String toTime(int seconds) {
			// Let's assume that songs can't be an hour long
			int m = seconds / 60;
			int s = seconds % 60;
			DecimalFormat df = new DecimalFormat("00");
			return df.format(m) + ":" + df.format(s);
		}
	}
}

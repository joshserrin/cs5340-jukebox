package org.jukebox.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author jserrin
 */
public class PlayingPanel extends JPanel {
	public PlayingPanel() {
		JLabel nowPlaying = new JLabel("Now Playing:");
		nowPlaying.setFont(new Font(UIConstants.getDefaultFontName(),
				Font.BOLD, 32));

		this.setBackground(Color.GREEN);

		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		this.add(nowPlaying);
	}
}

package org.jukebox.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.jukebox.model.Jukebox;
import org.jukebox.model.Player;

/**
 * @author jserrin
 */
public class JukeboxPanel extends JPanel {
	public JukeboxPanel(Jukebox jukebox) {
		if (null == jukebox) {
			throw new IllegalArgumentException("jukebox cannot be null");
		}

		Player player = new Player(jukebox);
		JPanel playing = new PlayingPanel(player);
		CenterPanel center = new CenterPanel(jukebox);

		this.setLayout(new BorderLayout());
		this.add(playing, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
	}
}

package org.jukebox.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.jukebox.model.Jukebox;

/**
 * @author jserrin
 */
public class JukeboxPanel extends JPanel {
	public JukeboxPanel(Jukebox jukebox, Dimension prefSize) {
		if (null == prefSize) {
			throw new IllegalArgumentException("prefSize cannot be null");
		}

		this.setPreferredSize(prefSize);

		JPanel playing = new PlayingPanel();
		CenterPanel center = new CenterPanel(jukebox);

		this.setLayout(new BorderLayout());
		this.add(playing, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
	}
}

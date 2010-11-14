package org.jukebox.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

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
		Dimension prefSize = new Dimension(1100, 700);
		this.setPreferredSize(prefSize);
		Player player = new Player(jukebox);
		JPanel playing = new PlayingPanel(player);
		JPanel library = new LibraryPanel(jukebox);
		JPanel playlist = new PlaylistPanel(jukebox.getPlaylist());

		JSplitPane center = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				playlist, library);
		center.setDividerLocation((int) (prefSize.width * .35));

		this.setLayout(new BorderLayout());
		this.add(playing, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
	}
}

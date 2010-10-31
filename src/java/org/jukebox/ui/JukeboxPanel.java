package org.jukebox.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

/**
 * @author jserrin
 */
public class JukeboxPanel extends JPanel {
	public JukeboxPanel(Dimension prefSize) {
		if (null == prefSize) {
			throw new IllegalArgumentException("prefSize cannot be null");
		}
		this.setPreferredSize(prefSize);
		JPanel user = new UserPanel();
		JPanel playing = new PlayingPanel();
		JPanel playlists = new PlaylistsPanel();
		JPanel browse = new BrowseLibraryPanel();

		JPanel north = new JPanel(new FlowLayout(FlowLayout.LEADING));
		north.add(user);
		north.add(playing);

		playlists.setPreferredSize(new Dimension(
				(int) (prefSize.getWidth() * .33),
				(int) (prefSize.getHeight() * .8)));
		user.setPreferredSize(new Dimension((int) (prefSize.getWidth() * .25),
				0)); // let the parent determine the height

		this.setLayout(new BorderLayout());
		this.add(north, BorderLayout.NORTH);
		this.add(playlists, BorderLayout.WEST);
		this.add(browse, BorderLayout.CENTER);
		// this.add(sp, BorderLayout.CENTER);
	}
}

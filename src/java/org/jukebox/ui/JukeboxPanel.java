package org.jukebox.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.jukebox.model.Jukebox;
import org.jukebox.ui.transition.UserChangeTransition;

/**
 * @author jserrin
 */
public class JukeboxPanel extends JPanel {
	public JukeboxPanel(Jukebox jukebox, Dimension prefSize) {
		if (null == prefSize) {
			throw new IllegalArgumentException("prefSize cannot be null");
		}

		this.setPreferredSize(prefSize);
		UserPanel user = new UserPanel(jukebox);
		user.setBorder(BorderFactory.createLineBorder(Color.black));
		JPanel playing = new PlayingPanel();
		JPanel playlists = new PlaylistsPanel();
		CenterPanel center = new CenterPanel(jukebox);

		JSplitPane north = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, user,
				playing);
		north.setDividerSize(0);
		north.setDividerLocation((int) (prefSize.getWidth() * .25));

		playlists.setPreferredSize(new Dimension(
				(int) (prefSize.getWidth() * .33),
				(int) (prefSize.getHeight() * .70)));

		this.setLayout(new BorderLayout());
		this.add(north, BorderLayout.NORTH);
		this.add(playlists, BorderLayout.WEST);
		this.add(center, BorderLayout.CENTER);

		jukebox.addUserObserver(new UserChangeTransition(user, center));
	}
}

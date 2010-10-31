package org.jukebox.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.jukebox.model.Playlist;
import org.jukebox.model.Request;
import org.jukebox.model.User;
import org.jukebox.utils.Option;

/**
 * @author jserrin
 */
public class PlaylistsPanel extends JPanel {
	private final GlobalPlaylistPanel global;
	private final LocalPlaylistPanel local;

	public PlaylistsPanel() {
		this.global = new GlobalPlaylistPanel();
		this.local = new LocalPlaylistPanel();

		JTabbedPane tp = new JTabbedPane();
		tp.addTab("Playlist", global);
		tp.addTab("My Songs", local);

		this.setLayout(new BorderLayout());
		this.add(tp);
	}

	public void setUser(final Option<User> user) {
		assert null != user;
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (user.isSome()) {
					local.setUser(user.get());
				} else {
					local.clear();
				}
			}
		});
	}

	private class LocalPlaylistPanel extends JPanel {
		private final int slots = 5;

		public LocalPlaylistPanel() {
			this.setLayout(new GridLayout(slots, 1));
		}

		public void clear() {
			this.removeAll();
		}

		public void setUser(User user) {
			assert EventQueue.isDispatchThread();
			assert null != user;
			Playlist playlist = user.getPlaylist();
			assert playlist.size() <= slots;
			clear();
			int added = 0;
			for (Request r : playlist.orderedRequests()) {
				this.add(new LocalRequestPanel(r));
				added--;
			}
			for (int i = 0; i < added; i++) {
				this.add(new LocalRequestPanel());
			}

		}
	}

	private class GlobalPlaylistPanel extends JPanel {
		public GlobalPlaylistPanel() {
			JPanel view = new JPanel();
			view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
			JScrollPane sp = new JScrollPane(view,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			this.setLayout(new BorderLayout());
			this.add(sp, BorderLayout.CENTER);
		}
	}
}

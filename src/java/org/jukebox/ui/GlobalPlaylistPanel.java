package org.jukebox.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jukebox.model.Playlist;
import org.jukebox.model.PlaylistObserver;
import org.jukebox.model.Request;
import org.jukebox.model.Song;

/**
 * @author jserrin
 */
public class GlobalPlaylistPanel extends JPanel {
	private final Playlist playlist;
	private final JPanel view;

	public GlobalPlaylistPanel(Playlist playlist) {
		if (null == playlist) {
			throw new IllegalArgumentException("playlist cannot be null");
		}
		this.playlist = playlist;

		this.view = new JPanel();
		view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
		JScrollPane sp = new JScrollPane(view,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		this.setLayout(new BorderLayout());
		this.add(sp, BorderLayout.CENTER);

		playlist.addObserver(new PlaylistObserver() {
			@Override
			public void songAdded(Song song) {
				updatePlaylist();
			}

			@Override
			public void songRemoved(Song s) {
				updatePlaylist();
			}
		});
	}

	private void updatePlaylist() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Iterator<Request> requests = playlist.orderedRequests();
				view.removeAll();
				if (requests.hasNext()) {
					Request nextSong = requests.next();
					view.add(new SongWithIndex("Next", nextSong));
					int i = 2;
					while (requests.hasNext()) {
						view.add(new SongWithIndex(i++, requests.next()));
					}
				}
				GlobalPlaylistPanel.this.validate();
				GlobalPlaylistPanel.this.repaint();
			}
		});
	}

	private class SongWithIndex extends JPanel {
		public SongWithIndex(int i, Request request) {
			this(Integer.toString(i), request);
		}

		public SongWithIndex(String index, Request request) {
			assert null != index;
			assert null != request;

			this.setBorder(BorderFactory.createLineBorder(Color.black));

			final JLabel indexLabel = new JLabel(index);
			indexLabel.setPreferredSize(new Dimension(100, 30));
			indexLabel.setFont(new Font(UIConstants.getDefaultFontName(),
					Font.BOLD, UIConstants.getMediumFontSize()));
			Component song = new SongPanel(request.getSong());

			this.setLayout(new FlowLayout(FlowLayout.LEADING));
			this.add(indexLabel);
			this.add(song);

			indexLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println(indexLabel.getSize());
				}
			});
		}
	}
}
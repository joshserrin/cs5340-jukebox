package org.jukebox.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
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
public class PlaylistPanel extends JPanel {
	private final Playlist playlist;
	private final JPanel view;

	public PlaylistPanel(Playlist playlist) {
		if (null == playlist) {
			throw new IllegalArgumentException("playlist cannot be null");
		}
		this.playlist = playlist;

		this.view = new JPanel();
		view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
		JScrollPane sp = new JScrollPane(view,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JPanel modify = new JPanel(new FlowLayout());
		modify.add(new JButton(new RemoveSong()));
		// modify.add(new JButton(new ReorderPlaylist()));

		this.setLayout(new BorderLayout());
		this.add(sp, BorderLayout.CENTER);
		this.add(modify, BorderLayout.SOUTH);

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
				PlaylistPanel.this.validate();
				PlaylistPanel.this.repaint();
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
			Component song = new SimpleSongPanel(request);

			this.setLayout(new FlowLayout(FlowLayout.LEADING));
			this.add(indexLabel);
			this.add(song);
		}
	}

	private class RemoveSong extends AbstractAction {
		public RemoveSong() {
			super("Remove Song");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JPanel panel = new RemoveSongsPanel(playlist);
			String title = "Remove song by clicking the X button";
			final JDialog dialog = new JDialog((Frame) null, title, true);
			dialog.setLayout(new BorderLayout());
			dialog.add(new JScrollPane(panel), BorderLayout.CENTER);
			dialog.add(new JButton(new AbstractAction("Okay") {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(dialog.getSize());
					dialog.setVisible(false);
					dialog.dispose();
				}
			}), BorderLayout.SOUTH);
			dialog.setPreferredSize(new Dimension(300, 700));
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			updatePlaylist();
		}
	}
}
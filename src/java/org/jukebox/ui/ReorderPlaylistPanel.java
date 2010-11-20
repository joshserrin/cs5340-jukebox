package org.jukebox.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jukebox.model.Playlist;
import org.jukebox.model.Request;

public class ReorderPlaylistPanel extends JPanel {
	private final Playlist playlist;

	public ReorderPlaylistPanel(Playlist playlist) {
		if (null == playlist) {
			throw new IllegalArgumentException("playlist cannot be null");
		}
		this.playlist = playlist;
		showPlaylist();
	}

	private void showPlaylist() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JPanel panel = ReorderPlaylistPanel.this;
				panel.removeAll();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				Iterator<Request> it = playlist.orderedRequests();
				while (it.hasNext()) {
					final Request request = it.next();
					JPanel p = new JPanel(new FlowLayout(FlowLayout.LEADING));
					p.setBorder(BorderFactory.createRaisedBevelBorder());
					final int size = 48;
					JLabel up = new JLabel("Sooner", IconLoader.getIcon(
							"uparrow.png", size, size), JLabel.LEADING);
					JLabel down = new JLabel("Later", IconLoader.getIcon(
							"downarrow.png", size, size), JLabel.LEADING);
					up.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							System.out.println("Increasing index of "
									+ request.getSong().getTitle());
							playlist.increaseIndex(request);
							showPlaylist();
						}
					});
					down.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							System.out.println("Decreasing index of "
									+ request.getSong().getTitle());
							playlist.decreaseIndex(request);
							showPlaylist();
						}
					});
					JPanel arrows = new JPanel();
					arrows.setLayout(new BorderLayout());
					arrows.add(up, BorderLayout.NORTH);
					arrows.add(new JPanel(), BorderLayout.CENTER);
					arrows.add(down, BorderLayout.SOUTH);
					p.add(arrows);
					p.add(new SimpleSongPanel(request));
					panel.add(p);
				}
				panel.getParent().validate();
				panel.getParent().repaint();
			}
		});
	}
}

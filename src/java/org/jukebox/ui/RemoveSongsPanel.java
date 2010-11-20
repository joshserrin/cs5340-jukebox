package org.jukebox.ui;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jukebox.model.Playlist;
import org.jukebox.model.Request;

public class RemoveSongsPanel extends JPanel {
	private final Playlist playlist;

	public RemoveSongsPanel(Playlist playlist) {
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
				JPanel panel = RemoveSongsPanel.this;
				panel.removeAll();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				Iterator<Request> it = playlist.orderedRequests();
				while (it.hasNext()) {
					final Request request = it.next();
					JPanel p = new JPanel(new FlowLayout(FlowLayout.LEADING));
					p.setBorder(BorderFactory.createRaisedBevelBorder());
					JLabel label = new JLabel(IconLoader.getIcon("remove.png",
							48, 48));
					label.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if (JOptionPane.YES_OPTION == JOptionPane
									.showConfirmDialog(null,
											"Are you sure you want to remove "
													+ request.getSong()
															.getTitle(),
											"Confirm Remove",
											JOptionPane.YES_NO_OPTION)) {
								System.out.println("Removing "
										+ request.getSong().getTitle());
								playlist.remove(request);
								showPlaylist();
							}
						}
					});
					p.add(label);
					p.add(new SimpleSongPanel(request));
					panel.add(p);
				}
				panel.getParent().validate();
				panel.getParent().repaint();
			}
		});
	}
}

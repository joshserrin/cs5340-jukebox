package org.jukebox.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jukebox.model.Jukebox;

/**
 * This panel is quite complex. It acts as both a panel but also as a UI state
 * transition controller. For instance, when no user is logged in the
 * BrowseLibraryPanel should be displayed. When the user logs in the SearchPanel
 * should be displayed.
 * 
 * @author jserrin
 */
public class CenterPanel extends JPanel {
	private final BrowseLibraryPanel browselib;
	private final Jukebox jukebox;

	public CenterPanel(Jukebox jukebox) {
		if (null == jukebox) {
			throw new IllegalArgumentException("jukebox cannot be null");
		}
		this.jukebox = jukebox;
		this.browselib = new BrowseLibraryPanel(jukebox);

		this.setLayout(new BorderLayout());
		this.add(browselib, BorderLayout.CENTER);
	}

	public void displayBrowse() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				CenterPanel.this.removeAll();
				CenterPanel.this.add(browselib, BorderLayout.CENTER);
				CenterPanel.this.repaint();
				System.out
						.println("Search should be removed and Browse should be back");
			}
		});
	}

	public void displaySearch() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				CenterPanel.this.removeAll();
				CenterPanel.this.add(new SearchPanel(jukebox),
						BorderLayout.CENTER);
				CenterPanel.this.validate();
			}
		});
	}
}

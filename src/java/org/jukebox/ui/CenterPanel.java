package org.jukebox.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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
	private final JButton browseLibButton;
	private final JButton viewPlaylistButton;
	private final JTabbedPane tabs;

	public CenterPanel(Jukebox jukebox) {
		if (null == jukebox) {
			throw new IllegalArgumentException("jukebox cannot be null");
		}

		Font font = new Font(UIConstants.getDefaultFontName(), Font.BOLD, 32);
		this.browseLibButton = new JButton(new BrowseLibrary());
		browseLibButton.setFont(font);
		this.viewPlaylistButton = new JButton(new ViewPlaylist());
		viewPlaylistButton.setFont(font);

		this.tabs = new JTabbedPane();
		tabs.addTab("Upcoming Songs",
				new GlobalPlaylistPanel(jukebox.getPlaylist()));
		tabs.addTab("Browse", new SearchPanel(jukebox));

		displayHome();
	}

	private void present(Component c) {
		assert EventQueue.isDispatchThread();
		removeAll();
		setLayout(new BorderLayout());
		add(c, BorderLayout.CENTER);
		validate();
	}

	public void displayHome() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
				p.add(browseLibButton);
				p.add(viewPlaylistButton);
				present(p);
				System.out.println("Home screen should be displayed");
			}
		});
	}

	public void displaySearch() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				tabs.setSelectedIndex(1);
				present(tabs);
				System.out.println("Searching should be displayed");
			}
		});
	}

	private void displayPlaylist() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				tabs.setSelectedIndex(0);
				present(tabs);
				System.out.println("The playlist should be displayed");
			}
		});
	}

	private class ViewPlaylist extends AbstractAction {
		public ViewPlaylist() {
			super("View Playlist");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			displayPlaylist();
		}
	}

	private class BrowseLibrary extends AbstractAction {
		public BrowseLibrary() {
			super("Browse Songs");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					displaySearch();
				}
			});
		}
	}
}

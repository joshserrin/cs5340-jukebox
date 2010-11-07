package org.jukebox.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jukebox.model.Jukebox;
import org.jukebox.model.SearchResults;
import org.jukebox.model.Song;

public class SearchPanel extends JPanel {
	private final Jukebox jukebox;
	private final SearchController searcher;

	public SearchPanel(Jukebox jukebox) {
		if (null == jukebox) {
			throw new IllegalArgumentException("jukebox cannot be null");
		}
		this.jukebox = jukebox;

		final SearchBox search = new SearchBox();
		Results results = new Results();
		this.searcher = new SearchController(results);
		searcher.displayAllSongs();

		JPanel upper = new JPanel(new FlowLayout());
		upper.add(search);
		upper.add(new JButton(new AbstractAction("Display All Songs") {
			@Override
			public void actionPerformed(ActionEvent e) {
				search.clear();
			}
		}));

		this.setLayout(new BorderLayout());
		this.add(upper, BorderLayout.NORTH);
		this.add(results, BorderLayout.CENTER);
	}

	private class SearchController {
		private final ExecutorService exec = Executors
				.newSingleThreadExecutor();
		private final Results results;

		public SearchController(Results results) {
			assert null != results;
			this.results = results;
		}

		public void displayAllSongs() {
			exec.submit(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					results.display(jukebox.allSongs());
					return null;
				}
			});
		}

		public void displayResultsOf(final String searchString) {
			exec.submit(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					System.out.println("Displaying search results for "
							+ searchString);
					results.display(jukebox.searchForSongMatching(searchString));
					return null;
				}
			});
		}

	}

	private class SearchBox extends JTextField {
		public SearchBox() {
			super(30);
			this.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					SearchPanel.this.searcher.displayResultsOf(SearchBox.this
							.getText());
				}
			});
		}

		public void clear() {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					SearchBox.this.setText("");
					searcher.displayAllSongs();
				}
			});
		}
	}

	private class Results extends JPanel {
		public Results() {
			this.setBorder(BorderFactory.createLineBorder(Color.blue));
		}

		public void display(SearchResults matching) {
			if (matching.isEmpty()) {
				displayNoMatches();
			} else {
				displayMatches(matching);
			}
		}

		private void displayMatches(final SearchResults matching) {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					final JTable table = new JTable(new ResultsTableModel(
							matching));
					JScrollPane sp = new JScrollPane(table);
					table.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							int row = table.rowAtPoint(e.getPoint());
							Song song = matching.get(row);
							jukebox.addSongToPlaylist(song);
						}
					});
					present(sp);
					System.out.println("Displaying some matching results: "
							+ matching);
				}
			});
		}

		private void present(Component c) {
			assert EventQueue.isDispatchThread();
			assert null != c;
			removeAll();
			setLayout(new BorderLayout());
			add(c, BorderLayout.CENTER);
			validate();
			repaint();
		}

		private void displayNoMatches() {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					System.out.println("Displaying no search results");
					JLabel label = new JLabel("No Results");
					label.setFont(new Font(UIConstants.getDefaultFontName(),
							UIConstants.getDefaultStyle(), UIConstants
									.getLargeFontSize()));
					present(label);
				}
			});
		}
	}
}

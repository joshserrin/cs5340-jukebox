package org.jukebox.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jukebox.model.Jukebox;
import org.jukebox.model.SearchResults;

public class SearchPanel extends JPanel {
	private final SearchController searcher;

	public SearchPanel(Jukebox jukebox) {
		if (null == jukebox) {
			throw new IllegalArgumentException("jukebox cannot be null");
		}

		SearchBox search = new SearchBox();
		Filter filter = new Filter();
		Results results = new Results();
		this.searcher = new SearchController(results, jukebox);
		searcher.displayAllSongs();

		JPanel upper = new JPanel(new FlowLayout());
		upper.add(search);
		upper.add(filter);

		this.setLayout(new BorderLayout());
		this.add(upper, BorderLayout.NORTH);
		this.add(results, BorderLayout.CENTER);
	}

	private class SearchController {
		private final ExecutorService exec = Executors
				.newSingleThreadExecutor();
		private final Results results;
		private final Jukebox jukebox;

		public SearchController(Results results, Jukebox jukebox) {
			assert null != results;
			assert null != jukebox;
			this.results = results;
			this.jukebox = jukebox;
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
	}

	private class Filter extends JComboBox {
		public Filter() {
			super(new String[] { "All", "Rock", "Classical" });
		}
	}

	private class Results extends JPanel {
		public Results() {
			this.setLayout(new BorderLayout());
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
					System.out.println("Displaying some matching results");
					JScrollPane sp = new JScrollPane(new JTable(
							new ResultsTableModel(matching)));
					present(sp);
				}
			});
		}

		private void present(Component c) {
			assert EventQueue.isDispatchThread();
			assert null != c;
			removeAll();
			add(c, BorderLayout.CENTER);
			validate();
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

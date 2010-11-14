package org.jukebox.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jukebox.model.Jukebox;
import org.jukebox.model.Song;

public class LibraryPanel extends JPanel {
	public static final int SONGS_PER_PAGE = 4;
	private final Jukebox jukebox;

	public LibraryPanel(Jukebox jukebox) {
		if (null == jukebox) {
			throw new IllegalArgumentException("jukebox cannot be null");
		}
		this.jukebox = jukebox;

		// Get all the songs of the library in sorted order by Artist
		// Group songs by first letter of artist's name
		// For each group create a series of JPanels similar to a book (left and
		// right sections containing X songs per side)
		// In alphabetical order add the panels to a JPanel with Cardlayout.
		// This will be the center of this panel.

		List<Song> byArtist = jukebox.allSongsSortedbyArtist();
		Map<String, List<Song>> groups = groupSongs(byArtist);
		final CardLayout cards = new CardLayout();
		final JPanel view = new JPanel(cards);
		int pageCount = 0;
		List<String> keys = new ArrayList<String>(groups.keySet());
		Collections.sort(keys);
		for (String key : keys) {
			List<Song> songs = groups.get(key);
			assert null != songs;
			for (int i = 0, k = songs.size(); i < k; i = i + SONGS_PER_PAGE) {
				List<Song> forPage = songs.subList(i,
						Math.min(songs.size(), i + SONGS_PER_PAGE));
				Page page = new Page(key, forPage);
				view.add(page, (key + i));
				pageCount++;
			}
			System.out.println("Created a page for " + key + " containing "
					+ songs.size() + " songs");
		}

		this.setLayout(new BorderLayout());
		this.add(view, BorderLayout.CENTER);
		this.add(new PageFlipper(pageCount, cards, view), BorderLayout.SOUTH);

		this.setBorder(BorderFactory.createTitledBorder("Library"));
	}

	private Map<String, List<Song>> groupSongs(List<Song> byArtist) {
		Map<String, List<Song>> groups = new HashMap<String, List<Song>>();
		for (Song s : byArtist) {
			String key = s.getArtist().getName().substring(0, 1).toLowerCase();
			List<Song> values = groups.get(key);
			if (null == values) {
				values = new ArrayList<Song>();
				groups.put(key, values);
			}
			values.add(s);
		}
		return groups;
	}

	private class PageFlipper extends JPanel {
		private final int numCards;
		private final CardLayout cards;
		private final Container view;
		private final AtomicInteger selectedCard = new AtomicInteger(0);
		private final Action previous, next;
		private final JLabel label;

		public PageFlipper(int _numCards, CardLayout _cards, JPanel _view) {
			assert _numCards > 0;
			assert null != _cards;
			assert null != _view;
			this.numCards = _numCards;
			this.cards = _cards;
			this.view = _view;

			// Add left right buttons at the bottom of this panel to flip
			// through the previous panel.
			final int height = 32;
			final int width = 32;
			this.previous = new FlipBackwards(width, height);
			this.next = new FlipForward(width, height);
			this.label = new JLabel(createText());

			// We currently are looking at the first so we shouldn't be able to
			// click this button
			cards.first(view);
			previous.setEnabled(false);

			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			this.add(new JButton(previous));
			this.add(Box.createHorizontalGlue());
			this.add(label);
			this.add(Box.createHorizontalGlue());
			this.add(new JButton(next));
		}

		private String createText() {
			return String.format("Page %s of %s", selectedCard.get() + 1,
					numCards);
		}

		private class FlipBackwards extends AbstractAction {
			public FlipBackwards(int width, int height) {
				super("", IconLoader.getIcon("arrow-left.png", width, height));
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedCard.get() > 0) {
					cards.previous(view);
					selectedCard.decrementAndGet();
					next.setEnabled(true);
				}
				if (selectedCard.get() == 0) {
					previous.setEnabled(false);
				}
				label.setText(createText());
			}
		}

		private class FlipForward extends AbstractAction {
			public FlipForward(int width, int height) {
				super("", IconLoader.getIcon("arrow-right.png", width, height));
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedCard.get() < numCards) {
					cards.next(view);
					selectedCard.incrementAndGet();
					previous.setEnabled(true);
				}
				if (selectedCard.get() == numCards - 1) {
					next.setEnabled(false);
				}
				label.setText(createText());
			}
		}
	}

	private class Page extends JPanel {
		// private final String key;

		public Page(String key, List<Song> forPage) {
			if (null == key) {
				throw new IllegalArgumentException("key cannot be null");
			}
			if (null == forPage) {
				throw new IllegalArgumentException("forPage cannot be null");
			}

			// this.key = key;
			this.setLayout(new GridLayout(SONGS_PER_PAGE, 1));
			for (final Song song : forPage) {
				SongPanel sp = new SongPanel(song);
				this.add(sp);
				sp.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						System.out.println(song.getTitle()
								+ " should be added to the playlist");
						jukebox.addSongToPlaylist(song);
					}
				});
			}
		}
	}
}

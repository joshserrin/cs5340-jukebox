package org.jukebox.model;

import javax.swing.ImageIcon;

import org.jukebox.utils.Option;

/**
 * @author jserrin
 */
public class Album {
	private final String title;
	private final Genre genre;
	private final Option<ImageIcon> cover;

	public Album(String title, Genre genre, Option<ImageIcon> coverArt) {
		if (null == title) {
			throw new IllegalArgumentException("title cannot be null");
		}
		if (null == genre) {
			throw new IllegalArgumentException("genre cannot be null");
		}
		if (null == coverArt) {
			throw new IllegalArgumentException("coverArt cannot be null");
		}
		this.genre = genre;
		this.title = title;
		this.cover = coverArt;
	}

	public Option<ImageIcon> getCover() {
		return cover;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return title;
	}

	public Genre getGenre() {
		return genre;
	}
}

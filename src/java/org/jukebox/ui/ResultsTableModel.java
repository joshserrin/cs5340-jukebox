package org.jukebox.ui;

import java.awt.EventQueue;

import javax.swing.table.AbstractTableModel;

import org.jukebox.model.SearchResults;
import org.jukebox.model.Song;

public class ResultsTableModel extends AbstractTableModel {
	private static final int SONG = 0;
	private static final int ARTIST = 1;
	private static final int ALBUM = 2;
	private static final int GENRE = 3;
	private final SearchResults matching;
	private final String[] header;

	public ResultsTableModel(SearchResults matching) {
		if (null == matching) {
			throw new IllegalArgumentException("matching cannot be null");
		}
		this.matching = matching;
		this.header = new String[] { "Song", "Artist", "Album", "Genre" };
	}

	@Override
	public int getRowCount() {
		return matching.size();
	}

	@Override
	public int getColumnCount() {
		return header.length;
	}

	@Override
	public String getColumnName(int column) {
		return header[column];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		assert EventQueue.isDispatchThread();
		Song song = matching.get(rowIndex);
		Object value = valueAt(song, columnIndex);
		return value;
	}

	private Object valueAt(Song song, int columnIndex) {
		assert null != song;
		switch (columnIndex) {
		case ARTIST:
			return song.getArtist().getName();
		case SONG:
			return song.getTitle();
		case ALBUM:
			return song.getAlbum().getTitle();
		case GENRE:
			return song.getAlbum().getGenre();
		default:
			throw new RuntimeException(columnIndex
					+ " are not programmed or return not called!");
		}
	}
}

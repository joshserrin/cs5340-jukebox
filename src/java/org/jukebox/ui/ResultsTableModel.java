package org.jukebox.ui;

import javax.swing.table.AbstractTableModel;

import org.jukebox.model.SearchResults;
import org.jukebox.model.Song;

public class ResultsTableModel extends AbstractTableModel {
	private static final int SONG = 0;
	private static final int ARTIST = 1;
	private static final int ALBUM = 2;
	private static final int ADD_BUTTON = 3;
	private final SearchResults matching;
	private final String[] header;

	public ResultsTableModel(SearchResults matching) {
		if (null == matching) {
			throw new IllegalArgumentException("matching cannot be null");
		}
		this.matching = matching;
		// Last column contains the button!
		this.header = new String[] { "Song", "Artist", "Album", "" };
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
	public Object getValueAt(int rowIndex, int columnIndex) {
		Song song = matching.get(rowIndex);
		switch (columnIndex) {
		case ARTIST:
			return song.getArtist().getName();
		case SONG:
			return song.getTitle();
		case ALBUM:
			return song.getAlbum().getTitle();
		case ADD_BUTTON:
			return "ADD!!!";
		default:
			throw new RuntimeException(rowIndex + " and " + columnIndex
					+ " are not programmed or return not called!");
		}
	}
}

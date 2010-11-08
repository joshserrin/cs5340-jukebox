package org.jukebox.model;

public interface PlaylistObserver {
	public void songAdded(Song song);

	public void songRemoved(Song s);
}

package org.jukebox.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * User's playlists have a limit to the size as to prevent a single user from
 * adding too many songs to prevent other users' songs from being played.
 * 
 * @author jserrin
 */
public class Playlist {
	// An ordered list of requests such that the first Request in the list
	// (request.get(0)) would be the first song to play in the playlist.
	private final List<Request> requests = new CopyOnWriteArrayList<Request>();
	private final Collection<PlaylistObserver> observers = new CopyOnWriteArraySet<PlaylistObserver>();

	public int size() {
		return requests.size();
	}

	/**
	 * @return an ordered array such that the first element of the array is the
	 *         first request to play.
	 */
	public Iterator<Request> orderedRequests() {
		return requests.iterator();
	}

	public void add(Song song) {
		if (null == song) {
			throw new IllegalArgumentException("song cannot be null");
		}
		requests.add(new Request(song));
		for (PlaylistObserver obs : observers) {
			obs.songAdded(song);
		}
	}

	public void addObserver(PlaylistObserver playlistObserver) {
		if (null == playlistObserver) {
			throw new IllegalArgumentException(
					"playlistObserver cannot be null");
		}
		observers.add(playlistObserver);
	}
}

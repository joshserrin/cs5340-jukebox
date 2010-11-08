package org.jukebox.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;

import org.jukebox.utils.Option;

/**
 * User's playlists have a limit to the size as to prevent a single user from
 * adding too many songs to prevent other users' songs from being played.
 * 
 * @author jserrin
 */
public class Playlist {
	// An ordered list of requests such that the first Request in the list
	// (request.get(0)) would be the first song to play in the playlist.
	// jserrin - I am going to keep this a Request object in case we want to
	// store information that isn't part of a song. (e.g., who added it, time
	// was added)
	private final Queue<Request> requests = new ConcurrentLinkedQueue<Request>();
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
		notifyAdded(song);
	}

	private void notifyAdded(Song s) {
		assert null != s;
		for (PlaylistObserver obs : observers) {
			obs.songAdded(s);
		}
	}

	private void notifyRemoved(Song s) {
		assert null != s;
		for (PlaylistObserver o : observers) {
			o.songRemoved(s);
		}
	}

	public void addObserver(PlaylistObserver playlistObserver) {
		if (null == playlistObserver) {
			throw new IllegalArgumentException(
					"playlistObserver cannot be null");
		}
		observers.add(playlistObserver);
	}

	public Option<Request> pop() {
		Option<Request> next = Option.of(requests.poll());
		if (next.isSome()) {
			notifyRemoved(next.get().getSong());
		}
		return next;
	}
}

package org.jukebox.model;

import java.util.Collection;
import java.util.Iterator;
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
	private final ConcurrentLinkedQueue<Request> requests = new ConcurrentLinkedQueue<Request>();
	private final Collection<PlaylistObserver> observers = new CopyOnWriteArraySet<PlaylistObserver>();

	public synchronized int size() {
		return requests.size();
	}

	/**
	 * @return an ordered array such that the first element of the array is the
	 *         first request to play.
	 */
	public synchronized Iterator<Request> orderedRequests() {
		return requests.iterator();
	}

	public synchronized void add(Request request) {
		if (null == request) {
			throw new IllegalArgumentException("request cannot be null");
		}
		requests.add(request);
		notifyAdded(request);
	}

	private synchronized void notifyAdded(Request r) {
		assert null != r;
		Song song = r.getSong();
		for (PlaylistObserver obs : observers) {
			obs.songAdded(song);
		}
	}

	private synchronized void notifyRemoved(Song s) {
		assert null != s;
		for (PlaylistObserver o : observers) {
			o.songRemoved(s);
		}
	}

	public synchronized void addObserver(PlaylistObserver playlistObserver) {
		if (null == playlistObserver) {
			throw new IllegalArgumentException(
					"playlistObserver cannot be null");
		}
		observers.add(playlistObserver);
	}

	public synchronized Option<Request> pop() {
		Option<Request> next = Option.of(requests.poll());
		if (next.isSome()) {
			notifyRemoved(next.get().getSong());
		}
		return next;
	}

	public synchronized boolean remove(Request request) {
		if (null == request) {
			throw new IllegalArgumentException("request cannot be null");
		}
		return requests.remove(request);
	}

	/**
	 * Bumps the request up one spot in the queue as to play sooner
	 * 
	 * @param request
	 */
	public synchronized void increaseIndex(Request request) {
		if (null == request) {
			throw new IllegalArgumentException("request cannot be null");
		}
		changeIndex(new CanBeBefore(), new Before(), request);
	}

	/**
	 * Bumps the request back one spot in the queue as to play later
	 * 
	 * @param request
	 */
	public void decreaseIndex(Request request) {
		if (null == request) {
			throw new IllegalArgumentException("request cannot be null");
		}
		changeIndex(new CanBePlayedAfter(requests.size()), new After(), request);
	}

	private void changeIndex(CanChange canChange, NewIndex newIndex,
			Request request) {
		Request[] requests = this.requests.toArray(new Request[] {});
		int oldIndex = -1;
		for (int i = 0; i < requests.length; i++) {
			if (requests[i].equals(request)) {
				oldIndex = i;
				break;
			}
		}
		if (oldIndex != -1 && canChange.shouldChange(oldIndex)) {
			int newI = newIndex.newIndex(oldIndex);
			Request swapWith = requests[newI];
			requests[newI] = request;
			requests[oldIndex] = swapWith;
			this.requests.clear();
			for (int i = 0; i < requests.length; i++) {
				this.requests.add(requests[i]);
			}
		}
	}

	private interface NewIndex {
		public int newIndex(int oldIndex);
	}

	private class Before implements NewIndex {
		public int newIndex(int oldIndex) {
			return oldIndex - 1;
		}
	}

	private class After implements NewIndex {
		public int newIndex(int oldIndex) {
			return oldIndex + 1;
		}
	}

	private interface CanChange {
		public boolean shouldChange(int index);
	}

	private class CanBeBefore implements CanChange {
		public boolean shouldChange(int index) {
			return index > 0;
		}
	}

	private class CanBePlayedAfter implements CanChange {
		private final int length;

		public CanBePlayedAfter(int length) {
			this.length = length;
		}

		public boolean shouldChange(int index) {
			return index < length - 1;
		}
	}
}

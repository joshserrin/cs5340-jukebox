package org.jukebox.model;

import java.util.ArrayList;
import java.util.List;

/**
 * User's playlists have a limit to the size as to prevent a single user from
 * adding too many songs to prevent other users' songs from being played.
 * 
 * @author jserrin
 */
public class Playlist {
	// An ordered list of requests such that the first Request in the list
	// (request.get(0)) would be the first song to play in the playlist.
	private final List<Request> requests = new ArrayList<Request>();

	public int size() {
		return requests.size();
	}

	/**
	 * @return an ordered array such that the first element of the array is the
	 *         first request to play.
	 */
	public Request[] orderedRequests() {
		return requests.toArray(new Request[] {});
	}
}

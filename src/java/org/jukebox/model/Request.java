package org.jukebox.model;

import org.jukebox.utils.Option;

/**
 * @author jserrin
 */
public class Request {
	private final Song song;
	private final Option<ShortStory> story;

	public Request(Song song) {
		this(song, Option.<ShortStory> of(null));
	}

	public Request(Song song, ShortStory shortStory) {
		this(song, Option.of(shortStory));
	}

	private Request(Song song, Option<ShortStory> story) {
		if (null == song) {
			throw new IllegalArgumentException("song cannot be null");
		}
		assert null != story;
		this.song = song;
		this.story = story;
	}

	public Song getSong() {
		return song;
	}

	public Option<ShortStory> getUserStory() {
		return story;
	}
}

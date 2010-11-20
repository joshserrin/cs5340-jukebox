package org.jukebox.model;

public class ShortStory {
	private final String story;
	private final String user;

	public ShortStory(String user, String story) {
		if (null == story) {
			throw new IllegalArgumentException("story cannot be null");
		}
		if (null == user) {
			throw new IllegalArgumentException("user cannot be null");
		}
		this.user = user;
		this.story = story;
	}

	public String getUser() {
		return user;
	}

	public String getStory() {
		return story;
	}
}

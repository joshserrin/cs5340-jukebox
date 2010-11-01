package org.jukebox.model;

public class Jukebox {

	public void setFocusedUser(User user) {
		if (null == user) {
			throw new IllegalArgumentException("user cannot be null");
		}
		System.out.println(user
				+ " has logged in and now has control of the application");
	}

}

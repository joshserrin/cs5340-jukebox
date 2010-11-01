package org.jukebox.model;

import org.jukebox.utils.Observer;
import org.jukebox.utils.Option;
import org.jukebox.utils.Publisher;

public class Jukebox {

	private final Publisher<User> userPublisher = new Publisher<User>();

	/**
	 * Can be null if the user was timed out or logged out.
	 * 
	 * @param user
	 *            can be null
	 */
	public void setFocusedUser(User user) {
		System.out.println(user
				+ " has logged in and now has control of the application");
		userPublisher.publish(Option.of(user));
	}

	/**
	 * @param obs
	 *            when the user changes this {@link Observer} will be notified
	 * @see Jukebox#setFocusedUser(User)
	 */
	public void addUserObserver(Observer<User> obs) {
		if (null == obs) {
			throw new IllegalArgumentException("obs cannot be null");
		}
		userPublisher.addObserver(obs);
	}
}

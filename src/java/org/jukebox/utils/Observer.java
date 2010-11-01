package org.jukebox.utils;

public interface Observer<T> {
	/**
	 * Could be None, lets say, if the obj was removed instead of replaced.
	 * Replaced would be a Some.
	 * 
	 * @param obj
	 */
	public void eventHappened(Option<T> obj);
}

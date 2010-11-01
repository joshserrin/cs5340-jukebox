package org.jukebox.utils;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;

public class Publisher<T> {
	private final Collection<Observer<T>> observers = new CopyOnWriteArraySet<Observer<T>>();

	public void addObserver(Observer<T> obs) {
		if (null == obs) {
			throw new IllegalArgumentException("obs cannot be null");
		}
		observers.add(obs);
	}

	/**
	 * Can be None if the new object is removed (let's say)
	 * 
	 * @param obj
	 */
	public void publish(Option<T> obj) {
		if (null == obj) {
			throw new IllegalArgumentException("obj cannot be null");
		}
		for (Observer<T> o : observers) {
			o.eventHappened(obj);
		}
	}
}

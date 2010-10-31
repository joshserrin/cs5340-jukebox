package org.jukebox.utils;

import java.util.NoSuchElementException;

/**
 * @author jserrin
 * 
 * @param <T>
 */
public abstract class Option<T> {
	/**
	 * @return the object
	 * @throws NoSuchElementException
	 *             if it is None
	 */
	public abstract T get();

	public abstract boolean isSome();

	public boolean isNone() {
		return !isSome();
	}

	/**
	 * @param <T>
	 * @param object
	 * @return if object is not null, returns a Some containing the object.
	 *         Otherwise, a None is returned
	 */
	public static <T> Option<T> of(T object) {
		if (null == object) {
			return new None<T>();
		} else {
			return new Some<T>(object);
		}
	}

	public static final class Some<T> extends Option<T> {
		private final T object;

		public Some(T it) {
			if (null == it) {
				throw new IllegalArgumentException("it cannot be null");
			}
			this.object = it;
		}

		public boolean isSome() {
			return true;
		}

		public T get() {
			return object;
		}
	}

	public static final class None<T> extends Option<T> {
		public boolean isSome() {
			return false;
		}

		public T get() {
			throw new NoSuchElementException();
		}
	}
}

package org.jukebox.model;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jukebox.utils.Option;

public class Player {
	private final ExecutorService exec = Executors.newSingleThreadExecutor();
	private final Jukebox jukebox;
	private final AtomicBoolean isPlaying = new AtomicBoolean(false);
	private final Collection<PlayObserver> obs = new CopyOnWriteArraySet<PlayObserver>();

	public Player(Jukebox jukebox) {
		if (null == jukebox) {
			throw new IllegalArgumentException("jukebox cannot be null");
		}
		this.jukebox = jukebox;
	}

	public void play() {
		exec.submit(new PlayASong());
	}

	public void add(PlayObserver obs) {
		if (null == obs) {
			throw new IllegalArgumentException("obs cannot be null");
		}
		this.obs.add(obs);
	}

	private void play(Song s) throws InterruptedException {
		assert null != s;
		System.out.println("PLAYING: " + s.getTitle());
		notifyPlayingNewSong(new PlayingDetails(s));
		TimeUnit.SECONDS.sleep(10);
	}

	private void notifyPlayingNewSong(PlayingDetails song) {
		assert null != song;
		for (PlayObserver o : obs) {
			o.newSongPlaying(song);
		}
	}

	private class PlayASong implements Callable<Void> {
		@Override
		public Void call() throws Exception {
			if (!isPlaying.get()) {
				isPlaying.set(true);
				System.out.println("The Player has begun to play!!!");
				Playlist playlist = jukebox.getPlaylist();
				Option<Request> next = playlist.pop();
				if (next.isNone()) {
					// play something randomly
					play(jukebox.randomSong());
				} else {
					play(next.get().getSong());
				}
				// Continue playing by calling again
				isPlaying.set(false);
				exec.submit(new PlayASong());
			}
			return null;
		}
	}

	public static interface PlayObserver {
		public void newSongPlaying(PlayingDetails song);
	}

	public static class PlayingDetails {
		private final Song song;

		private PlayingDetails(Song s) {
			assert null != s;
			this.song = s;
		}

		public Song getSong() {
			return song;
		}
	}
}

package org.jukebox.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import org.jukebox.utils.Option;
import org.tritonus.share.sampled.file.TAudioFileFormat;

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

	/**
	 * This should return only once the song has finished playing... so it can
	 * take a LONG time to return.
	 * 
	 * @param s
	 * @throws InterruptedException
	 */
	private void play(final Request request) throws InterruptedException {
		assert null != request;
		Song s = request.getSong();
		System.out.println("PLAYING: " + s);
		try {
			URL url = s.getURL();
			final long length = getLengthSeconds(url);
			InputStream is = new FileInputStream(new File(url.toURI()));
			final JavaSoundAudioDevice device = new JavaSoundAudioDevice();
			final AdvancedPlayer player = new AdvancedPlayer(is, device);
			ExecutorService exec = Executors.newSingleThreadExecutor();
			final AtomicBoolean finished = new AtomicBoolean(false);
			player.setPlayBackListener(new PlaybackListener() {
				@Override
				public void playbackFinished(PlaybackEvent arg0) {
					finished.set(true);
				}
			});
			exec.submit(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					notifyPlayingNewSong(new PlayingDetails(request, length,
							device));
					try {
						player.play();
					} catch (Throwable e) {
						e.printStackTrace();
					}
					return null;
				}
			});
			do {
				TimeUnit.MILLISECONDS.sleep(100);
				notifySongTimeUpdate(new PlayingDetails(request, length, device));
			} while (!finished.get());
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	private static long getLengthSeconds(URL url)
			throws UnsupportedAudioFileException, IOException {
		try {
			File file = new File(url.toURI());
			AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
			if (fileFormat instanceof TAudioFileFormat) {
				Map<?, ?> properties = ((TAudioFileFormat) fileFormat)
						.properties();
				String key = "duration";
				Long microseconds = (Long) properties.get(key);
				int mili = (int) (microseconds / 1000);
				return mili / 1000;
			} else {
				throw new UnsupportedAudioFileException();
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return 0l;
		}
	}

	private void notifyPlayingNewSong(PlayingDetails song) {
		assert null != song;
		for (PlayObserver o : obs) {
			o.newSongPlaying(song);
		}
	}

	private void notifySongTimeUpdate(PlayingDetails details) {
		assert null != details;
		for (PlayObserver o : obs) {
			o.songUpdate(details);
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
					play(jukebox.randomRequest());
				} else {
					play(next.get());
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

		public void songUpdate(PlayingDetails details);
	}

	public static class PlayingDetails {
		private final Request request;
		private final JavaSoundAudioDevice device;
		private final long seconds; // length of the song

		private PlayingDetails(Request request, long seconds,
				JavaSoundAudioDevice player) {
			assert null != request;
			assert null != player;
			this.request = request;
			this.seconds = seconds;
			this.device = player;
		}

		public Song getSong() {
			return request.getSong();
		}

		public int getLengthSeconds() {
			return (int) seconds;
		}

		public int getPlayedAmountSeconds() {
			return device.getPosition() / 1000;
		}

		public Option<ShortStory> getStory() {
			return request.getUserStory();
		}
	}
}

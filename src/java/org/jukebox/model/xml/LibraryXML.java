package org.jukebox.model.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jukebox.model.Album;
import org.jukebox.model.Artist;
import org.jukebox.model.Genre;
import org.jukebox.model.Library;
import org.jukebox.model.Song;
import org.jukebox.utils.Option;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LibraryXML {
	public static final String LIBRARY = "library";
	public static final String SONG = "song";
	public static final String TITLE = "title";
	public static final String ARTIST = "artist";
	public static final String NAME = "name";
	public static final String ALBUM = "album";
	public static final String GENRE = "genre";
	public static final String FILE = "file";
	public static final String LOCATION = "location";
	public static final String COVER = "cover";

	public static Library from(String file) throws IOException,
			ParserConfigurationException, SAXException {
		if (null == file) {
			throw new IllegalArgumentException("file cannot be null");
		}
		File f = new File(file);
		if (!f.exists()) {
			throw new IOException(file + " does not exsist");
		}
		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
		DocumentBuilder build = fact.newDocumentBuilder();
		Document doc = build.parse(f);
		NodeList children = doc.getElementsByTagName(LIBRARY);
		if (null == children) {
			throw new IOException(file + " is not a library document");
		} else if (children.getLength() == 0) {
			throw new IOException(
					"Libraries should have at least one song.  This library is empty: "
							+ file);
		} else if (children.getLength() > 1) {
			throw new IOException(
					"Library documents should have only one library element.  This has multiple: "
							+ file);
		}

		return processLibraryNode(children.item(0));
	}

	private static Library processLibraryNode(Node item) throws IOException {
		assert null != item;
		assert item.hasChildNodes();
		assert item.getNodeName().equals(LIBRARY);
		Collection<Song> songs = new ArrayList<Song>();
		NodeList children = item.getChildNodes();
		for (int i = 0, k = children.getLength(); i < k; i++) {
			Node node = children.item(i);
			Option<Song> sOpt = getSong(node);
			if (sOpt.isSome()) {
				songs.add(sOpt.get());
			}
		}
		return new Library(songs);
	}

	private static Option<Song> getSong(Node node) throws IOException {
		assert null != node;
		if (node.getNodeName().equals(SONG)) {
			String title = getAttributeValue(node, TITLE);
			Artist artist = getArtistOrException(node);
			Album album = getAlbumOrException(node);
			File file = getFileOrException(node);

			return Option.of(new Song(title, artist, album, file.toURI()
					.toURL()));
		} else {
			NodeList children = node.getChildNodes();
			for (int i = 0, k = children.getLength(); i < k; i++) {
				Option<Song> sOpt = getSong(children.item(i));
				if (sOpt.isSome()) {
					return sOpt;
				}
			}
			return Option.of(null); // None
		}
	}

	private interface Function<T> {
		public T from(Node node) throws IOException;
	}

	private static <T> Option<T> getT(String nodeName, Node node,
			Function<T> funct) throws IOException {
		assert null != nodeName;
		assert null != node;
		assert null != funct;
		NodeList children = node.getChildNodes();
		for (int i = 0, k = children.getLength(); i < k; i++) {
			Node c = children.item(i);
			if (c.getNodeName().equals(nodeName)) {
				return Option.of(funct.from(c));
			}
		}
		return Option.of(null);
	}

	private static <T> T getTOrException(String nodeName, Node node,
			Function<T> funct) throws IOException {
		Option<T> opt = getT(nodeName, node, funct);
		if (opt.isNone()) {
			throw new IOException(node + " does not contain a child named "
					+ nodeName);
		} else {
			return opt.get();
		}
	}

	private static File getFileOrException(Node node) throws IOException {
		return getTOrException(FILE, node, new Function<File>() {
			@Override
			public File from(Node node) throws IOException {
				return new File(getAttributeValue(node, LOCATION));
			}
		});
	}

	private static Album getAlbumOrException(Node node) throws IOException {
		return getTOrException(ALBUM, node, new Function<Album>() {
			@Override
			public Album from(Node node) throws IOException {
				return new Album(getAttributeValue(node, TITLE),
						getGenre(node), getCover(node));
			}
		});
	}

	private static Option<ImageIcon> getCover(Node node) throws IOException {
		return getT(COVER, node, new Function<ImageIcon>() {
			@Override
			public ImageIcon from(Node node) throws IOException {
				return new ImageIcon(getAttributeValue(node, LOCATION));
			}
		});
	}

	private static Genre getGenre(Node node) throws IOException {
		return getTOrException(GENRE, node, new Function<Genre>() {
			@Override
			public Genre from(Node node) throws IOException {
				return new Genre(getAttributeValue(node, NAME));
			}
		});
	}

	private static Artist getArtistOrException(Node node) throws IOException {
		return getTOrException(ARTIST, node, new Function<Artist>() {
			@Override
			public Artist from(Node node) throws IOException {
				return new Artist(getAttributeValue(node, NAME));
			}
		});
	}

	private static String getAttributeValue(Node node, String att)
			throws IOException {
		assert null != node;
		assert null != att;
		NamedNodeMap atts = node.getAttributes();
		if (null != atts) {
			Node n = atts.getNamedItem(att);
			if (null == n) {
				throw new IOException(node.getNodeName()
						+ " does not contain the attribute " + att);
			}
			return n.getNodeValue();
		} else {
			throw new IOException(node.getNodeName()
					+ " doesn't have attributes");
		}
	}
}

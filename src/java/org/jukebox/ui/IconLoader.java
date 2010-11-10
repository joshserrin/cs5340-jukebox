package org.jukebox.ui;

import java.awt.Image;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public abstract class IconLoader {

	// places images so that they are placed on the classpath where this class
	// will go.
	public static Icon getIcon(String iconName, int width, int height) {
		URL url = IconLoader.class.getResource(iconName);
		if (null != url) {
			ImageIcon img = new ImageIcon(url);
			return new ImageIcon(img.getImage().getScaledInstance(width,
					height, Image.SCALE_AREA_AVERAGING));
		} else {
			System.err.println("Unable to find icon at " + iconName);
			return new ImageIcon(IconLoader.class.getResource("error.jpg"));
		}
	}
}

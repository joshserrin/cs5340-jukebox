package org.jukebox.ui;

import java.awt.Font;

/**
 * @author jserrin
 */
public abstract class UIConstants {
	public static String getDefaultFontName() {
		return Font.SANS_SERIF;
	}

	public static int getDefaultStyle() {
		return Font.PLAIN;
	}

	public static int getLargeFontSize() {
		return 32;
	}

	public static int getMediumFontSize() {
		return 24;
	}

	public static int getSmallFontSize() {
		return 16;
	}
}

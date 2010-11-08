package org.jukebox;

import javax.swing.JApplet;

import org.jukebox.model.Jukebox;
import org.jukebox.ui.JukeboxPanel;

public class Applet extends JApplet {
	public Applet() {
		this.add(new JukeboxPanel(new Jukebox(MockLibrary.create())));
	}
}

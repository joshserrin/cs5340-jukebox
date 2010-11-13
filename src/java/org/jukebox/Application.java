package org.jukebox;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jukebox.model.Jukebox;
import org.jukebox.model.Library;
import org.jukebox.model.xml.LibraryXML;
import org.jukebox.ui.JukeboxPanel;

public class Application {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Library library = LibraryXML.from("library.xml");
					// Library library = MockLibrary.create();
					JPanel mainPanel = new JukeboxPanel(new Jukebox(library));

					final JFrame frame = new JFrame("Jukebox");
					frame.setLayout(new BorderLayout());
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.add(mainPanel);
					frame.pack();
					frame.setLocationRelativeTo(null); // display in middle of
														// screen
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

package org.jukebox;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jukebox.ui.JukeboxPanel;

public class Application {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JPanel mainPanel = new JukeboxPanel(new Dimension(1100, 700));
				final JFrame frame = new JFrame("Jukebox");
				frame.setLayout(new BorderLayout());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(mainPanel);
				frame.pack();
				frame.setLocationRelativeTo(null); // display in middle of
													// screen
				frame.setVisible(true);
			}
		});
	}
}

package org.jukebox.ui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.jukebox.model.ShortStory;

public class StoryPanel extends JPanel {

	public StoryPanel(ShortStory shortStory) {
		if (null == shortStory) {
			throw new IllegalArgumentException("shortStory cannot be null");
		}

		String text = shortStory.getUser() + " wrote: " + shortStory.getStory();
		JTextArea area = new JTextArea(text, 0, 30);
		area.setFont(new Font(UIConstants.getDefaultFontName(), UIConstants
				.getMediumFontSize(), Font.BOLD));
		area.setLineWrap(true);
		area.setEditable(false);

		this.setLayout(new BorderLayout());
		this.add(area, BorderLayout.CENTER);
	}

}

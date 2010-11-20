package org.jukebox.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.jukebox.model.ShortStory;

public class InputStoryPanel extends JPanel {
	public static final int MAX_CHARACTERS = 200;
	private final JTextField user;
	private final JTextArea story;

	public InputStoryPanel() {
		int row = 4;
		int column = 50;
		int maxSize = row * column;
		JLabel userLabel = new JLabel("Your Name:");
		JLabel storyLabel = new JLabel("Your Story:");
		this.user = new JTextField(column);
		this.story = new JTextArea(row, column);
		JLabel maxStoryLength = new JLabel("* max story length is " + maxSize
				+ " characters");
		story.setWrapStyleWord(true);
		story.setLineWrap(true);
		story.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);

		SequentialGroup hGroup = layout.createSequentialGroup();
		SequentialGroup vGroup = layout.createSequentialGroup();
		hGroup.addGroup(layout.createParallelGroup().addComponent(userLabel)
				.addComponent(storyLabel));
		hGroup.addGroup(layout.createParallelGroup().addComponent(user)
				.addComponent(story)
				.addComponent(maxStoryLength, Alignment.TRAILING));
		vGroup.addGroup(layout.createParallelGroup().addComponent(userLabel)
				.addComponent(user));
		vGroup.addGroup(layout.createParallelGroup().addComponent(storyLabel)
				.addComponent(story));
		vGroup.addComponent(maxStoryLength);
		layout.setHorizontalGroup(hGroup);
		layout.setVerticalGroup(vGroup);

		this.user.addKeyListener(new LimitCharacters(user, column));
		this.story.addKeyListener(new LimitCharacters(story, maxSize));
	}

	public ShortStory getShortStory() {
		return new ShortStory(user.getText(), story.getText());
	}

	private class LimitCharacters extends KeyAdapter {
		private final JTextComponent comp;
		private final int maxSize;

		public LimitCharacters(JTextComponent comp, int maxSize) {
			this.comp = comp;
			this.maxSize = maxSize;
		}

		@Override
		public void keyTyped(KeyEvent e) {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					String text = comp.getText();
					if (text.length() > maxSize) {
						comp.setText(text.substring(0, maxSize));
					}
				}
			});
		}
	}
}

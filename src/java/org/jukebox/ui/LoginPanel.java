package org.jukebox.ui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {
	private final JTextField textField;

	public LoginPanel() {
		JLabel instructions = new JLabel("Please enter your name",
				JLabel.CENTER);
		this.textField = new JTextField(20);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(instructions);
		this.add(textField);
	}

	public String getUserName() {
		return textField.getText();
	}
}

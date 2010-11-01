package org.jukebox.ui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jukebox.model.Jukebox;
import org.jukebox.model.User;

/**
 * @author jserrin
 */
public class BrowseLibraryPanel extends JPanel {
	private final Jukebox jukebox;

	public BrowseLibraryPanel(Jukebox jukebox) {
		if (null == jukebox) {
			throw new IllegalArgumentException("jukebox cannot be null");
		}
		this.jukebox = jukebox;
		JButton browseLib = new JButton(new BrowseLibrary());
		browseLib.setFont(new Font(UIConstants.getDefaultFontName(), Font.BOLD,
				32));
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(browseLib);
	}

	private class BrowseLibrary extends AbstractAction {
		public BrowseLibrary() {
			super("Browse Songs");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			LoginPanel loginPanel = new LoginPanel();
			int result = JOptionPane.showConfirmDialog(BrowseLibraryPanel.this,
					loginPanel, "Please enter your name",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				String userName = loginPanel.getUserName().trim();
				if (userName.isEmpty()) {
					// They didn't enter anything into the text field but
					// instead just clicked OK.
					actionPerformed(null);
				} else {
					User user = new User(userName);
					jukebox.setFocusedUser(user);
				}
			}
		}
	}
}

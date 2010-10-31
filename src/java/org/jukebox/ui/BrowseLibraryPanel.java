package org.jukebox.ui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author jserrin
 */
public class BrowseLibraryPanel extends JPanel {
	public BrowseLibraryPanel() {
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
			System.out
					.println("Browse the library by displaying the search panel");
		}
	}
}

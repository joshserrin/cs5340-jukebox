package org.jukebox.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class SearchPanel extends JPanel {
	public SearchPanel() {
		SearchBox search = new SearchBox();
		Filter filter = new Filter();
		Results results = new Results();

		JPanel upper = new JPanel(new FlowLayout());
		upper.add(search);
		upper.add(filter);

		this.setLayout(new BorderLayout());
		this.add(upper, BorderLayout.NORTH);
		this.add(results, BorderLayout.CENTER);
	}

	private class SearchBox extends JTextField {
		public SearchBox() {
			super(30);
		}
	}

	private class Filter extends JComboBox {
		public Filter() {
			super(new String[] { "All", "Rock", "Classical" });
		}
	}

	private class Results extends JPanel {
		public Results() {
			JTable table = new JTable(new Object[][] {}, new String[] {
					"Title", "Artist", "Album", "Genre", "" });
			JScrollPane sp = new JScrollPane(table);
			this.setLayout(new BorderLayout());
			this.add(sp, BorderLayout.CENTER);
		}
	}
}

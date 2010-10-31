package org.jukebox.ui;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jukebox.model.User;
import org.jukebox.utils.Option;

/**
 * @author jserrin
 */
public class UserPanel extends JPanel {
	private final UserName userName;
	private final NotUser notUser;

	public UserPanel() {
		this.userName = new UserName();
		this.notUser = new NotUser();

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(userName);
		this.add(notUser);
	}

	public void setUser(final Option<User> user) {
		assert null != user;
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (user.isSome()) {
					userName.setUser(user.get());
					notUser.setUser(user.get());
				} else {
					userName.clear();
					notUser.clear();
				}
			}
		});
	}

	private class UserName extends JLabel {
		public UserName() {
			this.setFont(new Font(UIConstants.getDefaultFontName(), Font.BOLD,
					16));
		}

		public void clear() {
			assert EventQueue.isDispatchThread();
			this.setText("");
		}

		public void setUser(User user) {
			assert EventQueue.isDispatchThread();
			assert null != user;
			this.setText("Hello " + user.getName());
		}
	}

	private class NotUser extends JPanel {
		private final JLabel notUser;
		private final JButton clickHere;

		public NotUser() {
			this.notUser = new JLabel();
			this.clickHere = new JButton("Click Here");

			Font font = new Font(UIConstants.getDefaultFontName(), Font.PLAIN,
					10);
			notUser.setFont(font);
			clickHere.setFont(font);

			this.setLayout(new FlowLayout(FlowLayout.LEADING));
			this.add(notUser);
			// Only add it when there is a user. At the point of creation, there
			// is no user.
			// this.add(clickHere);
		}

		public void clear() {
			assert EventQueue.isDispatchThread();
			this.notUser.setText("");
			this.remove(clickHere);
		}

		public void setUser(User user) {
			assert EventQueue.isDispatchThread();
			assert null != user;
			this.notUser.setText("Not " + user.getName() + "? ");
			this.add(clickHere);
		}
	}
}

package org.jukebox.ui;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jukebox.model.Jukebox;
import org.jukebox.model.User;
import org.jukebox.utils.Observer;
import org.jukebox.utils.Option;

/**
 * @author jserrin
 */
public class UserPanel extends JPanel {
	private final UserName userName;
	private final NotUser notUser;

	public UserPanel(Jukebox jukebox) {
		if (null == jukebox) {
			throw new IllegalArgumentException("jukebox cannot be null");
		}

		this.userName = new UserName();
		this.notUser = new NotUser(jukebox);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(userName);
		this.add(notUser);

		jukebox.addUserObserver(new HandleUserChangeEvent());
	}

	private class HandleUserChangeEvent implements Observer<User> {
		@Override
		public void eventHappened(final Option<User> user) {
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
					userName.validate();
					notUser.validate();
				}
			});
		}
	}

	private class UserName extends JLabel {
		public UserName() {
			super("", JLabel.LEFT);
			this.setFont(new Font(UIConstants.getDefaultFontName(), Font.BOLD,
					16));
		}

		public void clear() {
			assert EventQueue.isDispatchThread();
			this.setText("");
			System.out.println("The username has been cleared.");
		}

		public void setUser(User user) {
			assert EventQueue.isDispatchThread();
			assert null != user;
			this.setText("Hello " + user.getName() + "!");
			System.out.println("The username should now be " + user.getName());
		}
	}

	private class NotUser extends JPanel {
		private final JLabel notUser;
		private final JButton clickHere;

		public NotUser(final Jukebox jukebox) {
			this.notUser = new JLabel();
			this.clickHere = new JButton(new AbstractAction("Click Here") {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out
							.println("The user has logged out using the \"Click Here\" Button.");
					jukebox.setFocusedUser(null);
				}
			});

			Font font = new Font(UIConstants.getDefaultFontName(), Font.PLAIN,
					10);
			notUser.setFont(font);
			clickHere.setFont(font);

			this.setLayout(new FlowLayout(FlowLayout.TRAILING));
			this.add(notUser);
			// Only add it when there is a user. At the point of creation, there
			// is no user.
			// this.add(clickHere);
		}

		public void clear() {
			assert EventQueue.isDispatchThread();
			this.notUser.setText("");
			this.remove(clickHere);
			System.out.println("You should not be able to log out anymore");
		}

		public void setUser(User user) {
			assert EventQueue.isDispatchThread();
			assert null != user;
			this.notUser.setText("Not " + user.getName() + "? ");
			this.add(clickHere);
			System.out.println("The logout button should be visible");
		}
	}
}

package org.jukebox.ui.transition;

import java.awt.EventQueue;

import javax.swing.JPanel;

import org.jukebox.model.User;
import org.jukebox.ui.CenterPanel;
import org.jukebox.ui.UserPanel;
import org.jukebox.utils.Observer;
import org.jukebox.utils.Option;

public class UserChangeTransition implements Observer<User> {
	private final UserPanel userPanel;
	private final CenterPanel center;

	public UserChangeTransition(UserPanel user, CenterPanel center) {
		if (null == user) {
			throw new IllegalArgumentException("user cannot be null");
		}
		if (null == center) {
			throw new IllegalArgumentException("center cannot be null");
		}
		this.center = center;
		this.userPanel = user;
	}

	@Override
	public void eventHappened(final Option<User> obj) {
		if (null == obj) {
			throw new IllegalArgumentException("obj cannot be null");
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (obj.isSome()) {
					User user = obj.get();
					userPanel.setUser(user);
					center.displaySearch();
				} else {
					userPanel.removeUser();
					center.displayBrowse();
				}
			}
		});
	}

}

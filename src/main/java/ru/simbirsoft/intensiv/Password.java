package ru.simbirsoft.intensiv;

import ru.simbirsoft.intensiv.workWithDB.WorkWithDB;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static ru.simbirsoft.intensiv.TrackingWindow.tabbedPane;

public class Password implements ChangeListener {

	static boolean doYouNeedCheckPassword = true;

	private String name2;
	private JPanel panel;
	public static JTextField password2;
	TrackingWindow trackingWindow;

	public Password(String name, JPanel panel, TrackingWindow trackingWindow) {
		this.name2 = name;
		this.panel = panel;
		this.trackingWindow = trackingWindow;
	}

	@Override
	public void stateChanged(ChangeEvent e) {

		if (tabbedPane.getSelectedComponent().getName() == null
				|| !tabbedPane.getSelectedComponent().getName().equals(name2)) {
			System.out.println(2);
		} else {
			if ("".equals(WorkWithDB.getPassword(name2))) {
				Password.doYouNeedCheckPassword = false;
			}
			if (Password.doYouNeedCheckPassword) {
				Component[] compo = panel.getComponents();
				for (Component i : compo) {
					i.setEnabled(false);
				}
				showDialog();
			}
			Password.doYouNeedCheckPassword = true;
		}

	}

	private void showDialog() {
		JDialog dialog = new JDialog(trackingWindow, Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dialog.setTitle("Проверка пароля");

		JPanel jPanel = new JPanel();
		jPanel.setLayout(null);

		JLabel name = new JLabel("Ваше имя - " + tabbedPane.getSelectedComponent().getName());
		name.setLayout(null);
		name.setBounds(60, 10, 220, 20);

		jPanel.add(name);

		JLabel password = new JLabel("Пароль:");
		password.setLayout(null);
		password.setBounds(60, 40, 60, 20);

		jPanel.add(password);

		JLabel password1 = new JLabel("Пароль:");
		password1.setLayout(null);
		password1.setVisible(false);
		password1.setBounds(60, 10, 60, 20);

		jPanel.add(password1);

		password2 = new JTextField();
		password2.setLayout(null);
		password2.setBounds(110, 40, 80, 20);

		password2.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {

				Runnable doHighlight = new Runnable() {
					@Override
					public void run() {
						String tempName = password2.getText();
						Pattern p = Pattern.compile("^[А-Яа-яёЁ!@#%ёЁ^\\-/&*()=_+}:?>\"\\[\\]\\{\\}<№;\\.\\, ]{0,16}$");
						Matcher m = p.matcher(tempName);
						if (m.matches() || tempName.length() > 16 || tempName.contains(" ")) {
							Toolkit.getDefaultToolkit().beep();
							password2.setText("");
						}
					}
				};
				SwingUtilities.invokeLater(doHighlight);
			}
		});

		jPanel.add(password2);

		JLabel test = new JLabel("К сожалению, пароль неверен!");
		test.setVisible(false);
		test.setLayout(null);
		test.setBounds(20, 60, 210, 20);

		jPanel.add(test);

		JButton button = new JButton("Войти");
		button.setLayout(null);
		button.setBounds(90, 80, 80, 40);

		// реакция на кнопки Enter и Escape
		button.setFocusable(false);

		KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(final KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dialog.dispose();
				}
				if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ENTER) {
					button.doClick();
				}
				return false;
			}
		};
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);

		dialog.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEventDispatcher);
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEventDispatcher);
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
			}

		});

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (password2.getText().equals(WorkWithDB.getPassword(name2))) {
					Component[] compo = panel.getComponents();
					for (Component i : compo) {
						i.setEnabled(true);
						if (i.getName() == null) {
						} else {
							if (i.getName().equals("stop")) {
								i.setEnabled(RunWindow.tabIsStarted.get(name2));
							}
							if (i.getName().equals("start")) {
								i.setEnabled(!RunWindow.tabIsStarted.get(name2));
							}
							if (i.getName().equals("comboBox")) {
								i.setEnabled(!RunWindow.tabIsStarted.get(name2));
							}
							if (i.getName().equals("addActivityButton")) {
								i.setEnabled(!RunWindow.tabIsStarted.get(name2));
							}
						}
					}
					dialog.dispose();
				} else {
					test.setVisible(true);
				}
			}
		});
		jPanel.add(button);

		dialog.setBounds(0, 0, 250, 180);
		dialog.setResizable(false);
		dialog.getContentPane().add(jPanel);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
package ru.simbirsoft.intensiv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ExitButtonEngine implements ActionListener {

	private JPanel a;
	private Password customListener;
	static CloseWindow closeWindow;
	TrackingWindow trackingWindow;

	public ExitButtonEngine(JPanel a, Password customListener, TrackingWindow trackingWindow) {
		this.customListener = customListener;
		this.a = a;
		this.trackingWindow = trackingWindow;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TrackingWindow.tabbedPane.removeChangeListener(customListener);

		JDialog dialog = new JDialog(trackingWindow, Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dialog.setTitle("Закрытие вкладки");


		JPanel panel = new JPanel();
		panel.setLayout(null);

		JLabel areYoySureLabel = new JLabel("Вы уверены?");
		areYoySureLabel.setBounds(110, 10, 200, 20);
		JLabel warning = new JLabel("Все запущенные активности не сохранятся!");
		warning.setBounds(10, 30, 300, 20);

		JButton yesButton = new JButton("Да");
		yesButton.setBounds(20, 60, 100, 50);

		yesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TrackingWindow.tabbedPane.remove(a);
				dialog.dispose();
				
				TrackingWindow.tabbedPane.removeTabAt(TrackingWindow.tabbedPane.getTabCount() - 1);
				RunWindow.trackingWindow.addWelcomeTab();
			}
		});

		JButton noButton = new JButton("Нет");
		noButton.setBounds(180, 60, 100, 50);

		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});

		// реализация клавиш///////////////
		KeyEventDispatcher keyEvent = new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(final KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					noButton.doClick();
				}
				if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ENTER) {
					yesButton.doClick();
				}
				return false;
			}
		};
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEvent);

		dialog.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEvent);
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEvent);
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

		panel.add(areYoySureLabel);
		panel.add(warning);
		panel.add(yesButton);
		panel.add(noButton);

		dialog.setBounds(0, 0, 300, 150);
		dialog.setResizable(false);
		dialog.getContentPane().add(panel);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

	}

}
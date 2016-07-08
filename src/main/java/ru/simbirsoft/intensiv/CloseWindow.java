package ru.simbirsoft.intensiv;

import javax.swing.*;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

@SuppressWarnings("serial")
public class CloseWindow extends JFrame {

	static JButton yesButton;
	static JButton noButton;

	private JPanel a;

	public CloseWindow(JPanel a) {
		this.a = a;


		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Закрытие вкладки");
		setSize(300, 150);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setVisible(true);
		setResizable(false);

		JLabel areYoySureLabel = new JLabel("Вы уверены?");
		areYoySureLabel.setBounds(110, 10, 200, 20);
		JLabel warning = new JLabel("Все запущенные активности не сохранятся!");
		warning.setBounds(20, 30, 300, 20);

		yesButton = new JButton("Да");
		yesButton.setBounds(20, 60, 100, 50);

		yesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				System.out.println("Delete and Add");
				TrackingWindow.tabbedPane.remove(a);
				ExitButtonEngine.closeWindow.setVisible(false);
				
//				TrackingWindow.tabbedPane.removeTabAt(TrackingWindow.tabbedPane.getTabCount() - 1);
//				RunWindow.trackingWindow.addWelcomeTab();
				
			}
		});

		noButton = new JButton("Нет");
		noButton.setBounds(180, 60, 100, 50);

		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ExitButtonEngine.closeWindow.setVisible(false);
			}
		});

		getContentPane().add(areYoySureLabel);
		getContentPane().add(warning);
		getContentPane().add(yesButton);
		getContentPane().add(noButton);

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

		addWindowListener(new WindowListener() {

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
		////////////////

	}

}
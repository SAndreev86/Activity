package ru.simbirsoft.intensiv;

import ru.simbirsoft.intensiv.workWithDB.WorkWithDB;

import java.awt.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

@SuppressWarnings("serial")
public class AddActivityWindow extends JFrame {

	static JTextField newActivity;
	static JButton addActivityButton;
	private JPanel panel1;
	private JComboBox<String> comboBox;

	public AddActivityWindow(JPanel panel1, JComboBox<String> comboBox) {
		this.panel1 = panel1;
		this.comboBox = comboBox;
	}

	public void showDialog(TrackingWindow trackingWindow) {
		JDialog dialog = new JDialog(trackingWindow, Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dialog.setTitle("Добавить новую активность");

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(300, 130);

		JLabel addItemLabel = new JLabel("Новая активность:");
		addItemLabel.setBounds(30, 10, 200, 10);

		newActivity = new JTextField();
		newActivity.setBounds(20, 30, 200, 40);

		JLabel simpleString = new JLabel("Недопустимые символы. Максимальная длина = 26");
		simpleString.setFont((new Font("Arial", Font.CENTER_BASELINE, 10)));
		simpleString.setVisible(false);
		simpleString.setLayout(null);
		simpleString.setBounds(10, 70, 290, 15);

		JLabel activityAlreadyExist = new JLabel("Активность уже существует!");
		activityAlreadyExist.setFont((new Font("Arial", Font.CENTER_BASELINE, 10)));
		activityAlreadyExist.setVisible(false);
		activityAlreadyExist.setLayout(null);
		activityAlreadyExist.setBounds(10, 70, 250, 15);

		JLabel longString = new JLabel("Не может быть пустой, состоящей из пробелов");
		longString.setFont((new Font("Arial", Font.CENTER_BASELINE, 10)));
		longString.setVisible(false);
		longString.setLayout(null);
		longString.setBounds(10, 70, 250, 15);

		addActivityButton = new JButton("+");
		addActivityButton.setBounds(240, 30, 40, 40);
		addActivityButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tempName = newActivity.getText();
				Pattern p1 = Pattern.compile("^[ ]{0,26}$");
				Matcher m1 = p1.matcher(tempName);
				if (m1.matches()) {
					// что не может быть пустой или состоящей из пробелов
					simpleString.setVisible(false);
					activityAlreadyExist.setVisible(false);
					longString.setVisible(true);
					newActivity.setText(null);

				} else {
					Pattern p = Pattern.compile(
							"^[А-Яа-яA-Za-z0-9!@#%ёЁ^\\-/&*()=_+}:?>\"\\[\\]\\{\\}<№;\\.\\,]{1}" + "[А-Яа-яA-Za-z0-9!@#=%ёЁ^\\-/&*()_+}:?>\"\\[\\]\\{\\}<№;\\.\\, ]{0,24}"
									+ "[А-Яа-яA-Za-z0-9!@#%=ёЁ^\\-/&*()_+}:?>\"\\[\\]\\{\\}<№;\\.\\,]{0,1}$");
					Matcher m = p.matcher(tempName);
					if (m.matches() && tempName.charAt(tempName.length() - 1) != ' ') {
						if (WorkWithDB.isExistActivity(tempName, TrackingWindow.tabbedPane.getSelectedComponent().getName())) {
							WorkWithDB.writeActivity(tempName, TrackingWindow.tabbedPane.getSelectedComponent().getName());
							comboBox.insertItemAt(tempName, 0);
							comboBox.setSelectedItem(tempName);
							//comboBox.addItem(tempName);
							RunWindow.trackingWindow.getContentPane().repaint();
							dialog.dispose();
							TrackingWindow.tabbedPane.repaint();
						} else {
							// что активность уже существует
							simpleString.setVisible(false);
							longString.setVisible(false);
							activityAlreadyExist.setVisible(true);
							newActivity.setText(null);
						}
					} else {
						// выходит надпись что нарушены правила
						simpleString.setVisible(true);
						longString.setVisible(false);
						activityAlreadyExist.setVisible(false);
						newActivity.setText(null);
					}
				}
			}
		});

		// реализация клавиш//////////////////////////////////
		addActivityButton.setFocusable(false);

		KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(final KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dialog.dispose();
				}
				if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ENTER) {
					addActivityButton.doClick();
				}
				return false;
			}
		};
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);

		addWindowListener(new WindowListener() {

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

		panel.add(newActivity);
		panel.add(addActivityButton);
		panel.add(addItemLabel);
		panel.add(simpleString);
		panel.add(activityAlreadyExist);
		panel.add(longString);
		dialog.setBounds(0, 0, 300, 130);
		dialog.setResizable(false);
		dialog.getContentPane().add(panel);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

	}

}
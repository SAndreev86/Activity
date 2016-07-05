package ru.simbirsoft.intensiv;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		JLabel warning = new JLabel("Все ваши данные будут удалены!");
		warning.setBounds(40, 30, 250, 20);

		yesButton = new JButton("Да");
		yesButton.setBounds(20, 60, 100, 50);

		yesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TrackingWindow.tabbedPane.remove(a);
				ExitButtonEngine.closeWindow.setVisible(false);
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

	}

}
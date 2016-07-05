package ru.simbirsoft.intensiv;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


import ru.simbirsoft.intensiv.report.ReportHistoryButton;
import ru.simbirsoft.intensiv.report.StatsButton;
import ru.simbirsoft.intensiv.workWithDB.WorkWithDB;

import static ru.simbirsoft.intensiv.TrackingWindow.name1;

public class RunNewTab extends Thread {

	String tabName;
	JTextField timerField;
	long startTime;
	long currentTime;
	long stopTime;
	long timerTime; // время таймера в секундах
	String selectedItem;
	static JTextArea comment;
	JComboBox<String> comboBox;
	JButton stopButton;

	public static Password customListener;

	Timer timer = new Timer() {

		public void run() {
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					System.out.println("!!!! Какая то ошибка с таймером !!!!!");
				}

				currentTime = System.currentTimeMillis();
				timerTime = (currentTime - startTime) / 1000;

				// запуск метода обновления/отображения окна таймера
				timerField.setText(TimeConverter.convert(timerTime));
				System.out.println(TimeConverter.convert(timerTime) + "  " + timerTime);
			}
		}

	};

	public RunNewTab(String tabName) {
		this.tabName = tabName;
	}

	public void run() {

		// запускается метод по созданию newWindow
		JPanel panel1 = new JPanel();
		panel1.setLayout(null);

		JButton startButton = new JButton("СТАРТ");
		startButton.setBounds(30, 70, 100, 50);
		startButton.setName("start");
		panel1.add(startButton);
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
				comboBox.setEnabled(false);

				startTime = System.currentTimeMillis();
				selectedItem = (String) comboBox.getSelectedItem();
				System.out.println(selectedItem);

				if (!timer.isAlive()) {
					timer = null;
					timer = new Timer() {

						public void run() {
							while (true) {
								try {
									Thread.sleep(1000);
								} catch (Exception e) {
									System.out.println("!!!! Какая то ошибка с таймером !!!!!");
								}

								currentTime = System.currentTimeMillis();
								timerTime = (currentTime - startTime) / 1000;

								// запуск метода обновления/отображения окна
								// таймера
								timerField.setText(TimeConverter.convert(timerTime));
								System.out.println(TimeConverter.convert(timerTime) + "  " + timerTime);
							}
						}

					};
					timer.start();

					timerField.setText(TimeConverter.convert(timerTime));
				}
			}
		});

		stopButton = new JButton("СТОП");
		stopButton.setBounds(340, 70, 100, 50);
		panel1.add(stopButton);
		stopButton.setName("stop");
		stopButton.setEnabled(false);
		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				startButton.setEnabled(true);
				stopButton.setEnabled(false);
				comboBox.setEnabled(true);
				timer.stop();
				stopTime = System.currentTimeMillis();

				AddCommentWindow addCommentWindow = new AddCommentWindow(tabName, selectedItem, timerTime);

				timerTime = 0;

			}
		});

		JLabel timerLabel = new JLabel("ВРЕМЯ");
		timerLabel.setBounds(220, 70, 100, 10);
		panel1.add(timerLabel);

		timerField = new JTextField();
		timerField.setBounds(190, 85, 100, 50);
		timerField.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
		timerField.setHorizontalAlignment(JTextField.CENTER);
		timerField.setEditable(false);
		panel1.add(timerField);

		JLabel chooseActivityLabel = new JLabel("Выберите активность:");
		chooseActivityLabel.setBounds(30, 5, 150, 15);
		panel1.add(chooseActivityLabel);

		String[] items = WorkWithDB.getListActivity("ivan");
		comboBox = new JComboBox<String>(items);
		comboBox.setBounds(20, 20, 170, 30);
		panel1.add(comboBox);

		JButton addItem = new JButton("+ Добавить");
		addItem.setBounds(200, 20, 120, 30);
		panel1.add(addItem);
		AddItemEngine addItemEngine = new AddItemEngine(panel1, comboBox);
		addItem.addActionListener(addItemEngine);

		JButton historyReportButton = new JButton("История отчетов");
		historyReportButton.setBounds(330, 1, 140, 30);
		panel1.add(historyReportButton);
		// Вань, здесь выгружаем из базы отчеты за 3 дня(за сегодня не берется!)
		// ID будет имя вкладки,
		ReportHistoryButton Reporthistorybutton = new ReportHistoryButton(tabName);
		historyReportButton.addActionListener(Reporthistorybutton);

		JButton reportButton = new JButton("Показать отчет");
		reportButton.setBounds(320, 140, 150, 30);
		panel1.add(reportButton);
		StatsButton statsButton = new StatsButton(tabName);
		reportButton.setFocusable(false);
		reportButton.addActionListener(statsButton);

		JButton exitButton = new JButton("Закрыть вкладку");
		exitButton.setBounds(10, 140, 150, 30);
		panel1.add(exitButton);
		ExitButtonEngine exitButtonEngine = new ExitButtonEngine(panel1);
		exitButton.addActionListener(exitButtonEngine);

		panel1.setName(tabName);
		customListener = new Password(name1.getText(), panel1);
		TrackingWindow.tabbedPane.addMouseListener(customListener);

		TrackingWindow.tabbedPane.addTab(TrackingWindow.name1.getText(), panel1);
		TrackingWindow.tabbedPane.removeTabAt(TrackingWindow.tabbedPane.getTabCount() - 2);
		RunWindow.trackingWindow.addWelcomeTab();

	}


}
package ru.simbirsoft.intensiv;


import ru.simbirsoft.intensiv.checkingApplication.CheckRunningApplication;
import ru.simbirsoft.intensiv.workWithDB.WorkWithDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class TrackingWindow extends JFrame {

	static long startTime; // время СТАРТА в миллисекундах
	static JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	static JTextField name1;
	private static TrackingWindow trackingWindow = new TrackingWindow();

	public static TrackingWindow getTrackingWindow() {
		return trackingWindow;
	}

	TrayIcon trayIcon;
	SystemTray tray;

	private TrackingWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		if (SystemTray.isSupported()) {
			tray = SystemTray.getSystemTray();

			Image image = Toolkit.getDefaultToolkit().getImage("./home.png");
			ActionListener exitListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					class Close extends JFrame{
						public Close(){
							setDefaultCloseOperation(DISPOSE_ON_CLOSE);
							setTitle("Закрытие приложения");
							setSize(300, 150);
							setLocationRelativeTo(null);
							getContentPane().setLayout(null);
							setVisible(true);
							setResizable(false);

							JLabel areYoySureLabel = new JLabel("Вы уверены?");
							areYoySureLabel.setBounds(110, 10, 200, 20);
							JLabel warning = new JLabel("Все запущенные активности не сохранятся!");
							warning.setBounds(40, 30, 250, 20);

							JButton yesButton = new JButton("Да");
							yesButton.setBounds(20, 60, 100, 50);

							yesButton.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									System.exit(0);
								}
							});

							JButton noButton = new JButton("Нет");
							noButton.setBounds(180, 60, 100, 50);

							noButton.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									dispose();
								}
							});

							getContentPane().add(areYoySureLabel);
							getContentPane().add(warning);
							getContentPane().add(yesButton);
							getContentPane().add(noButton);
						}
					}
					new Close();
				}
			};
			PopupMenu popup = new PopupMenu();
			MenuItem defaultItem = new MenuItem("Выход");
			defaultItem.addActionListener(exitListener);
			popup.add(defaultItem);
			defaultItem = new MenuItem("Развернуть");
			defaultItem.addActionListener(e -> {
				setVisible(true);
				setExtendedState(JFrame.NORMAL);
			});
			popup.add(defaultItem);
			trayIcon = new TrayIcon(image, "Суточный трекинг активности", popup);
			trayIcon.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(true);
					setExtendedState(JFrame.NORMAL);

				}
			});
			trayIcon.setImageAutoSize(true);
		} else {
		}
		addWindowStateListener(e -> {
			if (e.getNewState() == ICONIFIED) {
				try {
					tray.add(trayIcon);
					setVisible(false);
				} catch (AWTException ex) {
				}
			}
			if (e.getNewState() == 7) {
				setVisible(false);
			}
			if (e.getNewState() == MAXIMIZED_BOTH) {
				tray.remove(trayIcon);
				setVisible(true);
			}
			if (e.getNewState() == NORMAL) {
				tray.remove(trayIcon);
				setVisible(true);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage("./home.png"));

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setTitle("Суточный трекинг активности");
		setSize(500, 250);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent event) {
				Object[] options = { "Нет", "Да" };
				int n = JOptionPane.showOptionDialog(event.getWindow(), "Закрыть приложение?", "Подтверждение",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
				if (n == 1) {
					event.getWindow().setVisible(false);
					CheckRunningApplication.deleteFile();
					System.exit(0);
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

	public void addWelcomeTab() {
		JPanel jPanel = new JPanel();
		jPanel.setLayout(null);

		JLabel name = new JLabel("Имя:");
		name.setLayout(null);
		name.setBounds(150, 40, 50, 20);

		jPanel.add(name);

		JLabel test = new JLabel("Логин не существует или пароль неверен!");
		test.setVisible(false);
		test.setLayout(null);
		test.setBounds(130, 20, 300, 20);

		jPanel.add(test);

		JLabel test1 = new JLabel("Минимальное количество символов в имени = 3, максимальное = 20");
		test1.setVisible(false);
		test1.setLayout(null);
		test1.setBounds(30, 20, 350, 20);

		jPanel.add(test1);

		JLabel test2 = new JLabel("Максимальное количество пользователей = 4");
		test2.setVisible(false);
		test2.setLayout(null);
		test2.setBounds(110, 20, 300, 20);

		jPanel.add(test2);

		JLabel test3 = new JLabel("Пользователь с таким именем уже создан");
		test3.setVisible(false);
		test3.setLayout(null);
		test3.setBounds(30, 20, 300, 20);

		jPanel.add(test3);

		name1 = new JTextField();
		name1.setLayout(null);
		name1.setBounds(200, 40, 100, 20);

		jPanel.add(name1);

		JLabel password = new JLabel("Пароль:");
		password.setLayout(null);
		password.setBounds(150, 70, 60, 20);

		jPanel.add(password);

		JTextField password1 = new JTextField();
		password1.setLayout(null);
		password1.setBounds(200, 70, 100, 20);

		jPanel.add(password1);

		JButton add = new JButton("Вход");
		add.setLayout(null);
		add.setBounds(110, 110, 110, 30);

		jPanel.add(add);

		JButton checkIn = new JButton("Регистрация");
		checkIn.setLayout(null);
		checkIn.setBounds(240, 110, 150, 30);

		jPanel.add(checkIn);

        checkIn.addActionListener(new CheckIn(trackingWindow));

		add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabbedPane.getTabCount() < 5) {
                    Component[] compo1 = tabbedPane.getComponents();
                    boolean is = false;
                    for (Component i : compo1) {
                        if (i.getName() == null) {
                        } else {
                            if (i.getName().equals(name1.getText()))
                                is = true;
                        }
                    }
                    if (is) {
                        // тот же пользователь
                        test.setVisible(false);
						test2.setVisible(false);
                        test3.setVisible(true);
						System.out.println(111);
					} else {
                            if (WorkWithDB.getPassword(name1.getText()).equals(password1.getText())) {
								System.out.println(12);
								Password.doYouNeedCheckPassword = false;
								RunWindow.tabIsStarted.put(name1.getText(), false);
								addPanel(name1.getText());
                            } else {
                                //вы ввели не верный пароль или логин
								System.out.println(13);
								System.out.println(WorkWithDB.getPassword(name1.getText()));
								System.out.println(password1.getText());
								test2.setVisible(false);
                                test3.setVisible(false);
                                test.setVisible(true);
                            }
                    }
                } else {
                    //много пользователей
					System.out.println(14);
					test.setVisible(false);
                    test3.setVisible(false);
                    test2.setVisible(true);
                }
            }
        });

		tabbedPane.setBounds(1, 1, 500, 230);
		getContentPane().add(tabbedPane);
		
		//проверка по количеству вкладок
		if (TrackingWindow.tabbedPane.getTabCount() == 4) {
			test2.setVisible(true);
			add.setVisible(false);
			checkIn.setVisible(false);
			name.setVisible(false);
			password.setVisible(false);
			name1.setVisible(false);
			password1.setVisible(false);
		}
//		System.out.println("Количество вкладок "+ TrackingWindow.tabbedPane.getTabCount() );
		
		tabbedPane.addTab("+", jPanel);

		jPanel.add(checkIn);

	}

	public static void addPanel(String temp) {

		RunNewTab runNewTab = new RunNewTab(temp, trackingWindow);
		runNewTab.start();

	}

}
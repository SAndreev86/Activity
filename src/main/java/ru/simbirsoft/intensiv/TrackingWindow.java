package ru.simbirsoft.intensiv;

import ru.simbirsoft.intensiv.workWithDB.WorkWithDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;


@SuppressWarnings("serial")
public class TrackingWindow extends JFrame {

	static long startTime; // время СТАРТА в миллисекундах
	static JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	static JTextField name1;

	TrayIcon trayIcon;
	SystemTray tray;

	public TrackingWindow() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		if (SystemTray.isSupported()) {
			tray = SystemTray.getSystemTray();

			Image image = Toolkit.getDefaultToolkit().getImage("home.png");
			ActionListener exitListener = e -> System.exit(0);
			PopupMenu popup = new PopupMenu();
			MenuItem defaultItem = new MenuItem("Exit");
			defaultItem.addActionListener(exitListener);
			popup.add(defaultItem);
			defaultItem = new MenuItem("Open");
			defaultItem.addActionListener(e -> {
				setVisible(true);
				setExtendedState(JFrame.NORMAL);
			});
			popup.add(defaultItem);
			trayIcon = new TrayIcon(image, "Суточный трекинг активности", popup);
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
		setIconImage(Toolkit.getDefaultToolkit().getImage("home.png"));

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
                int n = JOptionPane
                        .showOptionDialog(event.getWindow(), "Закрыть приложение?",
                                "Подтверждение", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[1]);
                if (n == 1) {
                    event.getWindow().setVisible(false);
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

		JButton add = new JButton("Добавить пользователя");
		add.setLayout(null);
		add.setBounds(140, 110, 200, 30);

		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabbedPane.getTabCount() < 5) {
					if(WorkWithDB.isExistUser(name1.getText())){
						if(WorkWithDB.getPassword(name1.getText()).getPassword().
								equals(password1.getText())){
							// добро пожаловать снова
							class Welcome extends JFrame{
								public Welcome(){

									setVisible(true);
									setResizable(false);
									setTitle("Do no find");
									setSize(350, 150);
									setLocationRelativeTo(null);

									JPanel jPanel = new JPanel();
									jPanel.setLayout(null);

									JLabel name = new JLabel("Добро пожаловать снова!");
									name.setLayout(null);
									name.setBounds(100, 20, 200, 20);

									jPanel.add(name);

									JButton button = new JButton("Ok");
									button.setLayout(null);
									button.setBounds(120, 60, 100, 30);

									button.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											setVisible(false);
										}
									});
									jPanel.add(button);

									add(jPanel);

								}
							}
							new Welcome();
							addPanel();
						}else{
							//вы ввели не верный пароль
							class DoNotFind extends JFrame{
								public DoNotFind(){

									setVisible(true);
									setResizable(false);
									setTitle("Do no find");
									setSize(350, 150);
									setLocationRelativeTo(null);

									JPanel jPanel = new JPanel();
									jPanel.setLayout(null);

									JLabel name = new JLabel("К сожалению пароль не верен.");
									name.setLayout(null);
									name.setBounds(90, 20, 200, 20);

									jPanel.add(name);

									JButton button = new JButton("Ok");
									button.setLayout(null);
									button.setBounds(120, 70, 100, 30);
									/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//									KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher() {
//							            @Override
//							            public boolean dispatchKeyEvent(final KeyEvent e) {
//							                if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
//							    				dispose();
//							                }
//							                if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ENTER) {
//							                	button.doClick();
//							                	
//							                }
//							                return false;
//							            }
//							        };
//							        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);
//							        
//							        addWindowListener(new WindowListener() {
//
//										@Override
//										public void windowActivated(WindowEvent arg0) { }
//
//										@Override
//										public void windowClosed(WindowEvent arg0) {
//									        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEventDispatcher);	
//										}
//
//										@Override
//										public void windowClosing(WindowEvent arg0) {
//									        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEventDispatcher);	
//										}
//
//										@Override
//										public void windowDeactivated(WindowEvent arg0) { }
//
//										@Override
//										public void windowDeiconified(WindowEvent arg0) { }
//
//										@Override
//										public void windowIconified(WindowEvent arg0) { }
//
//										@Override
//										public void windowOpened(WindowEvent arg0) { }
//							        	
//							        });
							        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
									button.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
//									        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEventDispatcher);	
											setVisible(false);
										}
									});
									jPanel.add(button);

									add(jPanel);

								}
							}
							new DoNotFind();
						}
					}else {
						WorkWithDB.writeNewUser
								(name1.getText(), password1.getText());
						addPanel();
					}
				} else {
					class NewWindow extends JFrame {
						NewWindow() {
							setVisible(true);
							setTitle("Ограничение количества пользователей");
							setSize(350, 150);
							setLocationRelativeTo(null);

							JPanel jPanel = new JPanel();
							jPanel.setLayout(null);

							JLabel name = new JLabel("Максимальное количество пользователей = 4");
							name.setLayout(null);
							name.setBounds(20, 40, 300, 20);

							jPanel.add(name);

							add(jPanel);
						}
					}
					new NewWindow();
				}
			}
		});

		tabbedPane.setBounds(1, 1, 500, 230);
		getContentPane().add(tabbedPane);
		tabbedPane.addTab("+", jPanel);

		jPanel.add(add);

	}

	public static void addPanel() {

		RunNewTab runNewTab = new RunNewTab(TrackingWindow.name1.getText());
		runNewTab.start();

	}

}
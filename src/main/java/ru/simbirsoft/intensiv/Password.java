package ru.simbirsoft.intensiv;

import ru.simbirsoft.intensiv.workWithDB.WorkWithDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import static ru.simbirsoft.intensiv.TrackingWindow.tabbedPane;

public class Password implements MouseListener {

	private String name2;
	private JPanel panel;
	public static JTextField password2;

	public Password(String name, JPanel panel) {
		this.name2 = name;
		this.panel = panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		class Window1 extends JFrame {
			public Window1() {
				setVisible(true);
				setResizable(false);
				setTitle("Проверка пароля");
				setSize(250, 180);
				setLocationRelativeTo(null);

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

				password2 = new JTextField();
				password2.setLayout(null);
				password2.setBounds(110, 40, 80, 20);

				jPanel.add(password2);

				JLabel test = new JLabel("К сожалению пароль неверен!");
				test.setVisible(false);
				test.setLayout(null);
				test.setBounds(20, 60, 180, 20);

				jPanel.add(test);

				JButton button = new JButton("Войти");
				button.setLayout(null);
				button.setBounds(90, 80, 80, 40);
				
				
				//реализация клавиш///////////////////////////////////////////////////////////////////////////////////////////////////
				button.setFocusable(false);
				
				KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher() {
		            @Override
		            public boolean dispatchKeyEvent(final KeyEvent e) {
		                if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		    				dispose();
		                }
		                if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ENTER) {
		                	button.doClick();
		                }
		                return false;
		            }
		        };
		        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);
		        
		        
		        addWindowListener(new WindowListener() {

					@Override
					public void windowActivated(WindowEvent arg0) { }

					@Override
					public void windowClosed(WindowEvent arg0) {
				        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEventDispatcher);				
					}

					@Override
					public void windowClosing(WindowEvent arg0) {
				        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEventDispatcher);
					}

					@Override
					public void windowDeactivated(WindowEvent arg0) { }

					@Override
					public void windowDeiconified(WindowEvent arg0) { }

					@Override
					public void windowIconified(WindowEvent arg0) { }

					@Override
					public void windowOpened(WindowEvent arg0) { }
		        	
		        });
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				

				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (password2.getText().equals(WorkWithDB.getPassword(name2).getPassword())) {
							Component[] compo = panel.getComponents();
							for (Component i : compo) {
								i.setEnabled(true);
								if (i.getName() == null) {
								} else {
									if (i.getName().equals("stop")) {
										i.setEnabled(false);
									}
								}
							}
							setVisible(false);
						} else {
							test.setVisible(true);
						}
					}
				});
				jPanel.add(button);
				add(jPanel);
			}
		}
		if (tabbedPane.getSelectedComponent().getName() == null
				|| !tabbedPane.getSelectedComponent().getName().equals(name2)) {
			System.out.println(2);
		} else {
			new Window1();
			Component[] compo = panel.getComponents();
			for (Component i : compo) {
				i.setEnabled(false);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
package ru.simbirsoft.intensiv;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AddActivityWindow extends JFrame{
	
	static JTextField newActivity;
	static JButton addActivityButton;
	private JPanel panel1;
	private JComboBox<String> comboBox;
	
	public AddActivityWindow(JPanel panel1, JComboBox<String> comboBox) {
		this.panel1 = panel1;
		this.comboBox = comboBox;
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setTitle("Добавить новую активность");
		setSize(300, 130);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setVisible(true);
		setResizable(false);
				
		JLabel addItemLabel = new JLabel("Новая активность:");
		addItemLabel.setBounds(30, 10, 200, 10);
		
		newActivity = new JTextField();
		newActivity.setBounds(20, 30, 200, 40);
		
		addActivityButton = new JButton("+");
		addActivityButton.setBounds(240, 30, 40, 40);
		AddNewActivityEngine addNewActivityEngine = new AddNewActivityEngine(panel1, comboBox);
		addActivityButton.addActionListener(addNewActivityEngine);

		getContentPane().add(newActivity);
		getContentPane().add(addActivityButton);
		getContentPane().add(addItemLabel);
		
		//реализация клавиш///////////////////////////////////////////////////////////////////////////////////////////////////
		addActivityButton.setFocusable(false);
		
		KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(final KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
    				dispose();
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
	}

}
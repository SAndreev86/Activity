package ru.simbirsoft.intensiv;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ru.simbirsoft.intensiv.workWithDB.WorkWithDB;

@SuppressWarnings("serial")
public class AddCommentWindow extends JFrame{
	
	JTextArea comment;
	JButton addCommentButton;
	JButton skipCommentButton;
	
	private long timerTime;
	private String tabName;
	private String selectedItem;
	
	public AddCommentWindow(String tabName, String selectedItem, long timerTime) {
		
		this.timerTime = timerTime;
		this.tabName = tabName;
		this.selectedItem = selectedItem;
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Добавление комментария");
		setSize(300, 150);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setVisible(true);
		setResizable(false);
		
		JLabel commentLabel = new JLabel("Ваш комментарий:");
		commentLabel.setBounds(50, 5, 150, 20);
		getContentPane().add(commentLabel);

		comment = new JTextArea();
		comment.setBounds(10, 10, 280, 50);
		comment.setLineWrap(true);
		JScrollPane scroll = new JScrollPane(comment);
		scroll.setBounds(10, 30, 280, 50);
		getContentPane().add(scroll);
		
		addCommentButton = new JButton("Oтправить");
		addCommentButton.setBounds(30, 90, 100, 35);
		getContentPane().add(addCommentButton);
		addCommentButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// метод добавления записи в БД
				
				WorkWithDB.wtiteStatistic(selectedItem, timerTime, comment.getText(), tabName, new Date());
				
				System.out.println(tabName +" "+ selectedItem +" "+ comment.getText() +" "+ timerTime);
				
				setVisible(false);
			}
		});
		
		
		//реализация клавиш///////////////////////////////////////////////////////////////////////////////////////////////////
		addCommentButton.setFocusable(false);
		
		KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(final KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
    				dispose();
                }
                if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ENTER) {
                	addCommentButton.doClick();
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
		
		skipCommentButton = new JButton("Пропустить");
		skipCommentButton.setBounds(160, 90, 100, 35);
		getContentPane().add(skipCommentButton);
		skipCommentButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				comment.setText("");
				// метод добавления записи в БД
				System.out.println(tabName +" "+ selectedItem +" "+ comment.getText() +" "+ timerTime);
				
				setVisible(false);
				
			}
		});

	}

}
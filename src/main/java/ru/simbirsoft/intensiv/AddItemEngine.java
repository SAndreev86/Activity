package ru.simbirsoft.intensiv;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddItemEngine implements ActionListener {

	static AddActivityWindow addActivityWindow;
	private JPanel panel1;
	private JComboBox<String> comboBox;
	
	public AddItemEngine(JPanel panel1, JComboBox<String> comboBox) {
		this.panel1 = panel1;
		this.comboBox = comboBox;
	}


	public void actionPerformed(ActionEvent e) {
		addActivityWindow = new AddActivityWindow(panel1, comboBox);
		addActivityWindow.getContentPane().repaint();
	}
	
	

}
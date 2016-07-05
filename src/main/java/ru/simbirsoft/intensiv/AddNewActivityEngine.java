package ru.simbirsoft.intensiv;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import ru.simbirsoft.intensiv.workWithDB.WorkWithDB;

public class AddNewActivityEngine implements ActionListener{

	private JPanel panel1;
	private JComboBox<String> comboBox;
	
	public AddNewActivityEngine(JPanel panel1, JComboBox<String> comboBox) {
		this.panel1 = panel1;
		this.comboBox = comboBox;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		RunWindow.rwactivity.AddItem(AddActivityWindow.newActivity.getText());
		WorkWithDB.writeActivity(AddActivityWindow.newActivity.getText(), "ivan");
		comboBox.addItem(AddActivityWindow.newActivity.getText());
		RunWindow.trackingWindow.getContentPane().repaint();
		AddItemEngine.addActivityWindow.setVisible(false);

		TrackingWindow.tabbedPane.repaint();
	}

}
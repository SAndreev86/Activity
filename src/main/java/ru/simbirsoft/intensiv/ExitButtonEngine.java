package ru.simbirsoft.intensiv;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitButtonEngine implements ActionListener {

	private JPanel a;
	static CloseWindow closeWindow;

	public ExitButtonEngine(JPanel a) {
		this.a = a;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		closeWindow = new CloseWindow(a);

	}

}
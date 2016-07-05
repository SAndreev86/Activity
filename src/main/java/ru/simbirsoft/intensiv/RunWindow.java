package ru.simbirsoft.intensiv;


import ru.simbirsoft.intensiv.workWithDB.WorkWithDB;

public class RunWindow {
	
	static Timer timer;
	static TrackingWindow trackingWindow;

	public static void main(String[] args) {
		WorkWithDB.getPassword("ivan");
		trackingWindow = new TrackingWindow();
		trackingWindow.getContentPane().repaint();
		timer = new Timer();
		trackingWindow.addWelcomeTab();		
		
	}

}
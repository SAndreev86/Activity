package ru.simbirsoft.intensiv;

import java.util.HashMap;

import ru.simbirsoft.intensiv.checkingApplication.CheckRunningApplication;
import ru.simbirsoft.intensiv.workWithDB.WorkWithDB;

public class RunWindow {

	static Timer timer;
	static TrackingWindow trackingWindow;
	static HashMap<String, Boolean> tabIsStarted = new HashMap<>();

	public static void main(String[] args) {
		CheckRunningApplication.check();

		if(!WorkWithDB.checkExistDB()){
			WorkWithDB.createTable();
		}
		trackingWindow = TrackingWindow.getTrackingWindow();
		trackingWindow.getContentPane().repaint();
		timer = new Timer();
		trackingWindow.addWelcomeTab();





	}

}
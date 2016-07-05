package ru.simbirsoft.intensiv;

public class TimeConverter {	// класс конвертирует время из секунд в формат чч:мм:сс
	
	public static String convert(long time){
				
		long seconds = 0;
		long minutes = 0;
		long hours = 0;
		String resultTime;
		
		seconds = time%60;
		minutes = (time/60)%60;
		hours = time/3600;
		
		resultTime = String.valueOf(hours) + ":" + String.valueOf(minutes) + ":" + String.valueOf(seconds);
		return resultTime;
	}
	
}

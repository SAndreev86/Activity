package ru.simbirsoft.intensiv;

public class TimeConverter { // класс конвертирует время из секунд в формат
								// чч:мм:сс

	public static String convert(long time) {

		long seconds = 0;
		long minutes = 0;
		long hours = 0;
		String resultTime;

		seconds = time % 60;
		minutes = (time / 60) % 60;
		hours = time / 3600;

		String stringOfSeconds = String.valueOf(seconds);
		String stringOfMinunes = String.valueOf(minutes);
		String stringOfHours = String.valueOf(hours);

		if (seconds < 10) {
			stringOfSeconds = "0" + stringOfSeconds;
		}

		if (minutes < 10) {
			stringOfMinunes = "0" + stringOfMinunes;
		}
		
		if (hours < 10) {
			stringOfHours = "0" + stringOfHours;
		}

		resultTime = stringOfHours + ":" + stringOfMinunes + ":" + stringOfSeconds;
		return resultTime;
	}

}

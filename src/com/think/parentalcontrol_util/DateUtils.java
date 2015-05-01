package com.think.parentalcontrol_util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

	public static String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy",
				Locale.getDefault());
		String date = dateFormat.format(new Date());
		return date;
	}

	public static String getTime() {
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss",
				Locale.getDefault());
		String format = timeFormat.format(new Date());
		return format;
	}

	public static String getDateFormillies(long value) {
		Date date = new Date(value);
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy",
				Locale.getDefault());
		String dateFromMillies = format.format(date);
		return dateFromMillies;
	}

	public static String getTimeFormillies(long value) {
		Date date = new Date(value);
		SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss",
				Locale.getDefault());
		String dateFromMillies = format.format(date);
		return dateFromMillies;
	}

	public static String getTimeWithAMPMMarker() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a",
				Locale.getDefault());
		String format = dateFormat.format(new Date());
		return format;
	}

	public static String getCaluculatedDuration(String duration) {
		int parseInt = Integer.parseInt(duration);
		int hour = parseInt / 3600;
		int minutes = (parseInt % 3600) / 60;
		int seconds = parseInt % 60;
		return hour + ":" + minutes + ":" + seconds;
	}
}

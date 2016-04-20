package com.jamin.framework.util.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils implements TimeUnit{

	/**
	 * format the sec to time stamp<br>
	 * if is sec stamp，multiple 1000 first. "yyyy-MM-dd HH:mm:ss"<br>
	 * 
	 * @method FormatTimeForm
	 * @param time
	 * @return
	 * @throws
	 * @since v1.0
	 */
	public static String timeStamp2Date(long time, String format) {
		return new SimpleDateFormat(format).format(new Date(time));
	}

	/**
	 * format the date to time stamp<br>
	 * "yyyy-MM-dd HH:mm:ss"<br>
	 * 
	 * @method FormatTimeForm
	 * @param date
	 * @param format
	 * @return
	 */
	public static long date2TimeStamp(String date, String format) {
		try {
			return new SimpleDateFormat(format).parse(date).getTime();
		} catch (ParseException e) {
			return 0;
		}
	}

	public static long date2TimeStamp(Date date) {
		return date.getTime();
	}

	/**
	 * get today time stamp
	 * 
	 * @return
	 */
	public static long todayTimeStamp() {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(
					timeStamp2Date(System.currentTimeMillis(), "yyyy-MM-dd"))
					.getTime();
		} catch (ParseException e) {
			return 0;
		}
	}

	/**
	 * get tomorrow time stamp
	 * 
	 * @return
	 */
	public static long tomorrowTimeStamp() {
		return todayTimeStamp() + TimeUnit.DAY;
	}


}

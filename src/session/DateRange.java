package session;

import java.util.Scanner;
import java.io.PrintStream;

/**
 * Represents a range of time on a specific date.
 * Can be constructed using a date, start, and end times, or a date, start time and a duration in minutes.
 * 
 */
public class DateRange{
	/**
	 * Constructs a DateRange with a star and end time on a given date.
	 * @param date 				the date of the time range in YYYY-MM-DD format
	 * @param startTime  		the start time in HH:MM format
	 * @param endTime  			the end time in HH:MM format
	 */
	public DateRange(String date, String startTime, String endTime){
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * constructs a DateRange with a start time and duration in minutes on a given date
	 * @param date  			the date of the time range in YYYY-MM_DD format
	 * @param startTime  		the start time in HH:MM format
	 * @param duration  		the duration of the session in minutes
	 */
	public DateRange(String date, String startTime, long duration){
		this.date = date;
		this.startTime = startTime;
		int index = startTime.indexOf(":");
		long hour = (Integer.parseInt(startTime.substring(0,index))) + (duration/60);
		long minute = (Integer.parseInt(startTime.substring(index + 1))) + (duration%60);
		if(minute > 60){
		    minute -= 60;
		    ++hour;
		}
		if(hour >= 24){
		    hour -= 24;
		}
		
		this.endTime = String.format("%02d:%02d", hour, minute); 
		

	}

	public DateRange(Scanner in){
		this.date = in.nextLine();
		this.startTime = in.nextLine();
		this.endTime = in.nextLine();
	}

	private String date;
	private String startTime;
	private String endTime;

	/**
	 * returns the duration of the time range in minutes
	 * @return duration in minutes
	 */

	public long duration(){
		String formatStart = startTime.length() == 4 ? "0" + startTime : startTime;
		String formatEnd = endTime.length() == 4 ? "0" + endTime : endTime;
	
		int startHour = Integer.parseInt(formatStart.substring(0,2));
		int startMinute = Integer.parseInt(formatStart.substring(3));
		int endHour = Integer.parseInt(formatEnd.substring(0,2));
		int endMinute = Integer.parseInt(formatEnd.substring(3));

		long durationMinutes = (endHour*60 + endMinute) - (startHour*60 + startMinute);
		return durationMinutes;
	}

	/**
	 * Returns a string representation of the DateRange
	 * Format "YYYY-MM-DD HH:MM - HH:MM (duration minutes)"
	 * @return a human-readable string describing the date, time range, and duration
	 */

	@Override
	public String toString(){
		return date + " " + startTime + " - " + endTime + "(" + duration() + "minutes)";
	}

	public void save(PrintStream out){
		out.println("" + date);
		out.println("" + startTime);
		out.println("" + endTime);
	}
}


package other;

import java.time.*;

public class Time {
	
	private int hours = 0;
	private int minutes = 0;
	private int seconds = 0;
	
	// Constructors
	public Time() {
	}
	
	public Time(int hours, int minutes, int seconds) {
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}
	
	// Getters and setters
	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	
	public static Time hoursToTime(double hours) { /* method to convert a number of hours to an object Time */
		int nbHours = (int) Math.floor(hours);
		int nbMins = (int) Math.floor((hours - nbHours)*60);
		int nbSecs = (int) Math.floor(((hours - nbHours)*60 - nbMins)*60);
		return new Time(nbHours, nbMins, nbSecs);
	}
	
	public double TimeToSeconds() {
		return this.hours*3600 + this.minutes*60 + this.seconds;
	}
	
	@Override
	public String toString() {
		if(seconds>9) {
			if(minutes>9) {
				return hours + ":" + minutes + ":" + seconds +"\r\n";
			}
			else {
				return hours + ":0" + minutes + ":" + seconds +"\r\n";
			}
		}
		else {
			if(minutes>9) {
				return hours + ":" + minutes + ":0" + seconds +"\r\n";
			}
			else {
				return hours + ":0" + minutes + ":0" + seconds +"\r\n";
				
			}
		}
	}
	
	/* method used to get local time as a time object. Gets hour, minutes and seconds attributes but ignores nano attribute */

	
	public static Time getLocalTime() {
		LocalTime t = LocalTime.now();
		int hour = t.getHour();
		int minutes = t.getMinute();
		int secs= t.getSecond();
		return new Time(hour, minutes, secs);
	}
	public void addTime(Time time2) { /* méthode pour ajouter un temps time2 à un temps */
		int new_seconds = (this.getSeconds() + time2.getSeconds())%60;
		int extra_minutes = (this.getSeconds() + time2.getSeconds())/60;
		int new_minutes = (extra_minutes + this.getMinutes() + time2.getMinutes())%60;
		int extra_hours = (extra_minutes + this.getMinutes() + time2.getMinutes())/60;
		int new_hours = (extra_hours + this.getHours() + time2.getHours())%24;
		this.setSeconds(new_seconds);
		this.setMinutes(new_minutes);
		this.setHours(new_hours);
	}
	
	public Time getTimeSum(Time time2) { /* méthode pour ajouter un temps time2 à un temps */
		int new_seconds = (this.getSeconds() + time2.getSeconds())%60;
		int extra_minutes = (this.getSeconds() + time2.getSeconds())/60;
		int new_minutes = (extra_minutes + this.getMinutes() + time2.getMinutes())%60;
		int extra_hours = (extra_minutes + this.getMinutes() + time2.getMinutes())/60;
		int new_hours = (extra_hours + this.getHours() + time2.getHours())%24;
		return new Time(new_hours, new_minutes, new_seconds);
	}
	
	public Time getTimeDiff(Time time2) { /* gives time interval between time 1 and time 2 (time1 - time2) */
		double seconds1 = this.TimeToSeconds();
		double seconds2 = time2.TimeToSeconds();
		double seconds = seconds1-seconds2;
		double hours = seconds/3600;
		return hoursToTime(hours);
	}

	public static void main(String[] args) {
		Time time = new Time();
		Time time2 = new Time(2,35,30);
		Time time3 = new Time(0, 45, 46);
		
		System.out.print(time.toString());
		time.addTime(time2);
		time2.addTime(time3);
		System.out.print(time.toString());
		System.out.print(time2.toString());
		
		System.out.print(hoursToTime(4.26));
		
		
	}
	

}

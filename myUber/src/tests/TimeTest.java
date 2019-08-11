package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import other.Time;

class TimeTest {

	@SuppressWarnings("deprecation")
	@Test
	public final void testToString() {
		Time time = new Time(2,35,30);
		Assert.assertEquals("2:35:30\r\n", time.toString());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public final void testHoursToTime() {
		double hours = 6.5;
		Time time = Time.hoursToTime(hours);
		Assert.assertEquals("6:30:00\r\n", time.toString());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public final void testTimeSum() {
		Time time = new Time(2,35,30);
		Time time2 = new Time(0, 45, 46);
		time.addTime(time2);
		Assert.assertEquals("3:21:16\r\n", time.toString());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public final void testgetTimeSum() {
		Time time1 = new Time(2,35,30);
		Time time2 = new Time(0, 45, 46);
		Time sum = time1.getTimeSum(time2);
		Assert.assertEquals("3:21:16\r\n", sum.toString());
	}

}

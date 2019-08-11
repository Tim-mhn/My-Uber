package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import other.GPS;
import priceCalculator.TrafficCalculator;
import rides.Ride;

class TrafficCalculatorTest {

	@SuppressWarnings("deprecation")
	@Test
	public final void testCalculateDuration() {
		GPS departure = new GPS(8.2,14.5);
		GPS destination = new GPS(8.5, 14.3);
		Ride r = new Ride(departure, destination);
		TrafficCalculator c = new TrafficCalculator(45);
		Assert.assertEquals(87, Math.round(c.calculateDuration(r)*100));
	}
	

}

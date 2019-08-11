package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import other.GPS;
import rides.Ride;

class RideTest {

	@SuppressWarnings("deprecation")
	@Test
	public final void testCalculateLength(){
		Ride r = new Ride();
		r.setDeparture(new GPS(40, 45));
		r.setDestination(new GPS(30,35));
		double length = r.calculateLength();
		Assert.assertEquals(1400, Math.round(length));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public final void testEnoughSeats() {
		Ride r1 = new Ride();
		r1.setNbPassengers(5);
		Ride r2 = new Ride();
		r2.setNbPassengers(4);
		Assert.assertEquals(false, r1.enoughSeats(r2));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public final void testIsInClosePerimeter() {
		Ride r1 = new Ride(new GPS(30,28), new GPS(38,39));
		Ride r2 = new Ride(new GPS(29.5,27.9), new GPS(37.8, 38.9));
		Assert.assertEquals(true, r1.isInClosePerimeter(r2, 5000));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public final void testIsCompatible() {
		Ride r1 = new Ride(new GPS(30,28), new GPS(38,39));
		Ride r2 = new Ride(new GPS(29.5,27.9), new GPS(37.8, 38.9));
		r1.setNbPassengers(2);
		r2.setNbPassengers(2);
		Assert.assertEquals(true, r1.isCompatible(r2, 5000));

	}

}

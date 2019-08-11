package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import sorters.DriverOccupationSorter;
import users.Driver;

class DriverOccupationSorterTest {

	@Test
	public final void getMostOccupiedDriverTest() {
		Driver d1 = new Driver("A", "A");
		Driver d2 = new Driver("B", "B");
		Driver d3 = new Driver("C", "C");
		d1.getBalance().setRatio(0.7);
		d2.getBalance().setRatio(0.4);
		d3.getBalance().setRatio(0.9);
		ArrayList<Driver> drivers =new ArrayList<Driver>();
		drivers.add(d1);
		drivers.add(d2);
		drivers.add(d3);
		Assert.assertEquals(d3, DriverOccupationSorter.getMostOccupiedDriver(drivers));
	}

}

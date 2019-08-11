package tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.util.ArrayList;


import junit.framework.Assert;
import sorters.DriverAppreciationSorter;
import users.*;

class DriverAppreciationSorterTest {

	@SuppressWarnings("deprecation")
	@Test
	public final void getMostAppreciatedDriverTest() {
		Driver d1 = new Driver("A", "A");
		Driver d2 = new Driver("B", "B");
		Driver d3 = new Driver("C", "C");
		d1.getBalance().setAvgMark(3.5);
		d2.getBalance().setAvgMark(4.5);
		d3.getBalance().setAvgMark(2.1);
		ArrayList<Driver> drivers =new ArrayList<Driver>();
		drivers.add(d1);
		drivers.add(d2);
		drivers.add(d3);
		Assert.assertEquals(d2, DriverAppreciationSorter.getMostAppreciatedDriver(drivers));
	}

}

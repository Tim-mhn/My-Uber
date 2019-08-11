package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import other.GPS;

class GPSTest {

	@SuppressWarnings("deprecation")
	@Test
	public final void testCalculateDistance() {
		GPS gps1 = new GPS(50 ,55);
		GPS gps2 = new GPS(45, 40);
		Assert.assertEquals(1710, Math.round(gps1.calculateDistance(gps2)));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public final void testGetClosestPosition() {
		GPS gps0 = new GPS(40, 55);
		GPS gps1 = new GPS(10,5);
		GPS gps2 = new GPS(42,51);
		GPS gps3 = new GPS(8,85);
		ArrayList<GPS> positionList = new ArrayList<GPS>();
		positionList.add(gps1);
		positionList.add(gps2);
		positionList.add(gps3);
		Assert.assertEquals(gps2, gps0.getClosestPosition(positionList));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public final void testPossibleStops() {
		GPS d1 = new GPS(20,35);
		GPS d2 = new GPS(25,32);
		GPS d3 = new GPS(35,32);
		
		ArrayList<GPS> departures = new ArrayList<GPS>();
		departures.add(d1);
		departures.add(d2);
		departures.add(d3);
		
		boolean[] depIndex = new boolean[3];
		depIndex[0] = false;
		depIndex[1] = false;
		depIndex[2] = false;
		boolean[] destIndex = new boolean[3];
		destIndex[0] = false;
		destIndex[1] = false;
		destIndex[2] = false;
		
		GPS a1 = new GPS(22,38);
		GPS a2 = new GPS(19,35);
		GPS a3 = new GPS(38,35);
		ArrayList<GPS> arrivals = new ArrayList<GPS>();
		arrivals.add(a1);
		arrivals.add(a2);
		arrivals.add(a3);
		
		ArrayList<GPS> possibleStops = GPS.possibleStops(departures, arrivals, depIndex, destIndex);
		
		Assert.assertEquals(true, possibleStops.get(0).equals(d1) && possibleStops.get(1).equals(d2));
	}
	
	/*
	@SuppressWarnings("deprecation")
	public final void testComputeBestPath() {
		GPS d1 = new GPS(20,22);
		GPS d2 = new GPS(20,23);
		GPS d3 = new GPS(20,24);
		
		ArrayList<GPS> departures = new ArrayList<GPS>();
		departures.add(d1);
		departures.add(d2);
		departures.add(d3);
		
		GPS a1 = new GPS(20,29);
		GPS a2 = new GPS(20,28);
		GPS a3 = new GPS(20,27);
		
		ArrayList<GPS> arrivals = new ArrayList<GPS>();
		arrivals.add(a1);
		arrivals.add(a2);
		arrivals.add(a3);
		
		ArrayList<GPS> expectedPath = new ArrayList<GPS>();
		expectedPath.add(d1);
		expectedPath.add(d2);
		expectedPath.add(d3);
		expectedPath.add(a3);
		expectedPath.add(a2);
		expectedPath.add(a1);
		
		Assert.assertEquals(true, GPS.computeBestPath(departures, arrivals).get(0).equals(d1) && GPS.computeBestPath(departures, arrivals).get(3).equals(a3));
	}*/

}

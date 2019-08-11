package sorters;

import java.util.*;

import users.*;
import users.Observer;
import factories.*;

public class DriverAppreciationSorter extends DriverSorter {

	public DriverAppreciationSorter() {
	}
	
	public static Driver getMostAppreciatedDriver(ArrayList<Driver> drivers) {
		Driver bestDriver = drivers.get(0);
		double bestMark = bestDriver.getAvgMark();

		for(int i = 1; i<drivers.size(); i++) {
			Driver d = drivers.get(i);
			if(bestMark<d.getAvgMark()) {
				bestMark = d.getAvgMark();
				bestDriver = d;
			}
		}
		return(bestDriver);
	}
	
	@Override 
	public ArrayList<Observer> visit(VisitableFactory factory){ 
		DriverFactory driverFactory = (DriverFactory) factory;
		ArrayList<Driver> drivers = driverFactory.getDrivers();
		ArrayList<Driver> clone = (ArrayList<Driver>) drivers.clone();
		ArrayList<Driver> clonedDrivers = clone;
		ArrayList<Observer> sortedDrivers = new ArrayList<Observer>();
		
		while(clonedDrivers.size()>1) {
			Driver closestDriver = getMostAppreciatedDriver(clonedDrivers);
			sortedDrivers.add((Observer) closestDriver);
			clonedDrivers.remove(closestDriver);
		}
		
		sortedDrivers.add(clonedDrivers.get(0));
		return sortedDrivers;
		
	}
	
	public static void main(String[] args) {
		
		DriverFactory df = DriverFactory.getInstance();
		
		Driver d1 = df.newDriver("A", "A");
		Driver d2 = df.newDriver("B", "B");
		Driver d3 = df.newDriver("C", "C");
		
		d1.getBalance().setAvgMark(3.2);
		d2.getBalance().setAvgMark(4.5);
		d3.getBalance().setAvgMark(2.2);
		
		
		System.out.print(SystemSorter.getSortedDriversByAppreciation()); /* bug because the drivers' car are null which raises the null pointer exception */
	}
}

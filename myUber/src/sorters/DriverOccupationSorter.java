package sorters;

import java.util.ArrayList;

import factories.DriverFactory;
import factories.VisitableFactory;
import users.Driver;
import users.Observer;

public class DriverOccupationSorter extends DriverSorter{
	
	/* Attention à penser à changer le getSorter2 du driver balance */
	
	public DriverOccupationSorter() {
	}
	
	public static Driver getMostOccupiedDriver(ArrayList<Driver> drivers) {
		Driver bestDriver = drivers.get(0);
		double bestRatio = bestDriver.getRatio();

		for(int i = 1; i<drivers.size(); i++) {
			Driver d = drivers.get(i);
			if(bestRatio<d.getRatio()) {
				bestRatio = d.getRatio();
				bestDriver = d;
			}
		}
		return(bestDriver);
	}
	
	public ArrayList<Observer> visit(VisitableFactory factory){ 
		DriverFactory driverFactory = (DriverFactory) factory;
		ArrayList<Driver> drivers = driverFactory.getDrivers();
		ArrayList<Driver> clone = (ArrayList<Driver>) drivers.clone();
		ArrayList<Driver> clonedDrivers = clone;
		ArrayList<Observer> sortedDrivers = new ArrayList<Observer>();
		
		while(clonedDrivers.size()>1) {
			Driver closestDriver = getMostOccupiedDriver(clonedDrivers);
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
		d1.getBalance().setRatio(0.2);
		d2.getBalance().setAvgMark(4.5);
		d2.getBalance().setRatio(0.96);
		d3.getBalance().setAvgMark(2.2);
		d3.getBalance().setRatio(0.95);
		
		
		System.out.print(SystemSorter.getSortedDriversByOccupation()); 
	}

}

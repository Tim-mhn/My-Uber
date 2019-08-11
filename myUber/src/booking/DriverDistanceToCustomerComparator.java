package booking;

import users.*;
import java.util.*;
import other.GPS;
import cars.*;


public class DriverDistanceToCustomerComparator {

	
	public static Driver getClosestDriver(ArrayList<Driver> drivers, Customer c) {
		Driver closestDriver = drivers.get(0);
		double minDistance = closestDriver.distanceToCustomer(c);
			
		for(int i = 1; i<drivers.size(); i++) {
			Driver d = drivers.get(i);
			if(minDistance>d.distanceToCustomer(c)) {
				minDistance = d.distanceToCustomer(c);
				closestDriver = d;
			}
		}
		return(closestDriver);
	}
	
	public static ArrayList<Driver> sortDriverByDistanceToCustomer(Booking b){
		ArrayList<Driver> drivers = b.getDrivers();
		ArrayList<Driver> clone = (ArrayList<Driver>) drivers.clone();
		ArrayList<Driver> clonedDrivers = clone;
		
		ArrayList<Driver> sortedDrivers = new ArrayList<Driver>();
		
		while(clonedDrivers.size()>1) {
			Driver closestDriver = getClosestDriver(clonedDrivers, b.getCustomer());
			sortedDrivers.add(closestDriver);
			clonedDrivers.remove(closestDriver);
		}
		
		sortedDrivers.add(clonedDrivers.get(0));
		return sortedDrivers;
		
	}
	
	
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		
		Customer c = new Customer("J", "M", new GPS(45,23));
		
		Car c1 = new Car(new GPS(45,25), 4);
		Car c2 = new Car(new GPS(25,24), 4);
		Car c3 = new Car(new GPS(28,25), 4);
		
		Driver d1 = new Driver("H","H", true, c1);
		Driver d2 = new Driver("H","I", true, c2);
		Driver d3 = new Driver("H","J", true, c3);
		
		ArrayList<Driver> drivers = new ArrayList<Driver>();
		drivers.add(d1);
		drivers.add(d2);
		drivers.add(d3);
		
		Booking b = new Booking(c, 4, drivers);
		
		System.out.print(sortDriverByDistanceToCustomer(b));
		
	}
	
	

}

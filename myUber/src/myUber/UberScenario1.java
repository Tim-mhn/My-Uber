package myUber;

import cars.*;
import factories.CarFactory;
import factories.DriverFactory;
import users.*;
import other.*;
import java.util.*;

public class UberScenario1 {
	
	/* this class will suggest a use scenario for the booking of a UberX Ride 
	 * Drivers are created in the main method. Please follow the description in the file 
	 */
	
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, InterruptedException {
		
		DriverFactory df = DriverFactory.getInstance();
		CarFactory cf = CarFactory.getInstance();
		
		Standard s1 = (Standard) cf.newCar("Standard", 4, 49.2, 65);
		Standard s2 = (Standard) cf.newCar("Standard", 4, 49.5, 64);
		Berline b1 = (Berline) cf.newCar("Berline", 4, 48.9, 64.5);
		
		Driver d1 = df.newDriver("Albert", "Einstein", s1);
		Driver d2 = df.newDriver("Isaac", "Newton", s2);
		Driver d3 = df.newDriver("Henri", "Poincarre", b1);
		
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Hello, welcome to myUber ! Please enter your name\r\n");
		String name = scan.next();
		System.out.print("Surname:\r\n");
		String surname = scan.next();
		
		System.out.print("-- Coordinates-- \r\nLatitude:\r\n");
		double latitude = scan.nextDouble();
		System.out.print("Longitude:\r\n");
		double longitude = scan.nextDouble();
		
		Customer c = new Customer(name, surname, new GPS(latitude, longitude));
		
		System.out.print("Account successfully created. Do you wish to request a ride?\r\n");
		
		String answer = scan.next();
		
		if(answer.equalsIgnoreCase("yes")) {
			System.out.print("How many are you?\r\n");
			int nbPassengers = scan.nextInt();
			System.out.print("-- Destination coordinates -- \r\nLatitude:\r\n");
			double latitude2 = scan.nextDouble();
			System.out.print("Longitude: \r\n");
			double longitude2 = scan.nextDouble();
			
			
			c.requestRide(c.getGps(), new GPS(latitude2, longitude2), nbPassengers, 10000);
			
			System.out.print("Which option do you want?");
			int option = scan.nextInt();
			
			c.treatRequest(option, 10000);
		}
		
		
		
		
	}

}

package factories;
import java.util.*;
import balances.*;
import factories.*;
import rides.*;
import users.*;
import cars.*;
import other.*;

public class CarFactory extends AbstractFactory {

	private static CarFactory instance = new CarFactory();
	private ArrayList<Car> cars = new ArrayList<Car>();
	private ArrayList<Berline> berlines = new ArrayList<Berline>();
	private ArrayList<Van> vans = new ArrayList<Van>();
	private ArrayList<Standard> standards = new ArrayList<Standard>();
	
	// Getters and setters 
	
		public ArrayList<Car> getCars() {
			return cars;
		}

		public void setCars(ArrayList<Car> cars) {
			this.cars = cars;
		}

		public ArrayList<Berline> getBerlines() {
			return berlines;
		}

		public void setBerlines(ArrayList<Berline> berlines) {
			this.berlines = berlines;
		}

		public ArrayList<Van> getVans() {
			return vans;
		}

		public void setVans(ArrayList<Van> vans) {
			this.vans = vans;
		}

		public ArrayList<Standard> getStandards() {
			return standards;
		}

		public void setStandards(ArrayList<Standard> standards) {
			this.standards = standards;
		}

		public static CarFactory getInstance() {
			return instance;
		}
		
		public Car getLastCar() {
			
			int n = this.cars.size();
			return this.cars.get(n-1);
		}
		
		public boolean carExist(String targetID){
			boolean carExists = false;
			for(Car car : this.cars) {
				if(car.getID().equalsIgnoreCase(targetID)) {
					carExists = true;
				}
			}
			return carExists;
		}
		public Car getCar(String targetID) {
			Car targetCar = null;
			String id = "";
			int i = 0;
			for(Car car : this.cars) {
				if(car.getID().equalsIgnoreCase(targetID)) {
					targetCar = car;
				}
			}
			return targetCar;
		}
		// Constructor
		
		public Car newCar(String type, int nbPassengers, double latitude,double longitude) {
			GPS gps = new GPS(longitude, latitude);
			if (type.equalsIgnoreCase("Berline")) {	
				Berline b = new Berline(gps, nbPassengers) ;
				this.berlines.add(b) ;
				this.cars.add((Car) b);
				return b;
			}
			
			else if (type.equalsIgnoreCase("Van")) {
				Van v = new Van(gps, nbPassengers) ;
				this.vans.add(v) ;
				this.cars.add((Car) v);
				return v;
			}
			
			else if (type.equalsIgnoreCase("Standard")) {
				Standard s = new Standard(gps, nbPassengers) ;
				this.standards.add(s) ;
				this.cars.add((Car) s);
				return s;
			}
			
			else {
				return null;
			}
			/*else {
				System.out.print("Erreur de choix");
				return new Car();
			}*/
			
		}
		
		public Car newCar(String type, int nbPassengers) {
			double longitude = GPS.getRandomCoordinate(10, 10.5);
			double latitude = GPS.getRandomCoordinate(10, 10.5);
			return newCar(type, nbPassengers, longitude, latitude);
		}
		
		public Car newCar(String type) {
			double longitude = GPS.getRandomCoordinate(10, 10.5);
			double latitude = GPS.getRandomCoordinate(10, 10.5);
			int nbPassengers = 0;
			if(type.equalsIgnoreCase("standard")) {
				nbPassengers = 4;
			}
			else if(type.equalsIgnoreCase("van")) {
				nbPassengers = 7;
			}
			else if(type.equalsIgnoreCase("berline") || type.equalsIgnoreCase("berlin")) {
				nbPassengers = 4;
			}
			return newCar(type, nbPassengers, longitude, latitude);
		}
		
		
		@Override
		public Customer newCustomer() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Driver newDriver() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Ride newRide(String rideType) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		Car newCar() {
			// TODO Auto-generated method stub
			return null;
		}	
	

}

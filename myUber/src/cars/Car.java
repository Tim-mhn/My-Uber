package cars;
import java.util.*;
import balances.*;
import factories.*;
import rides.*;
import users.*;
import other.GPS;

public class Car {
	
	protected String ID = "";
	protected String carType = "";
	protected GPS gps;
	protected int nbPassengers = 0;
	protected int nbSeats;
	protected ArrayList<Driver> drivers = new ArrayList<Driver>();
	private boolean availability = true;
	private Driver currentDriver = null;
	
	public Car() {
		this.gps = null;
		this.nbSeats = -1;
	}
	
	public Car(GPS gPS, int nbSeats, ArrayList<Driver> drivers) {
		this.gps = gPS;
		this.nbSeats = nbSeats;
		this.drivers = drivers;
	}

	public Car(String ID, GPS gps, int nbSeats, ArrayList<Driver> drivers) {
		this.ID = ID;
		this.gps = gps;
		this.nbSeats = nbSeats;
		this.drivers = drivers;
	}
	
	

	public Car(GPS gps, int nbSeats) {
		this.gps = gps;
		this.nbSeats = nbSeats;
	}

	// Getters and setters
	
	public int getNbSeats() {
		return nbSeats;
	}

	public void setNbSeats(int nbSeats) {
		this.nbSeats = nbSeats;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public Driver getCurrentDriver() {
		return currentDriver;
	}

	public void setCurrentDriver(Driver currentDriver) {
		this.currentDriver = currentDriver;
		this.availability = false;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public GPS getGps() {
		return gps;
	}

	public void setGps(GPS gps) {
		this.gps = gps;
	}

	public boolean isAvailable() {
		return availability;
	}
	
	public void setAvailability(boolean available) {
		this.availability = available;
	}
	
	public int getNbPassengers() {
		return nbPassengers;
	}

	public void setNbPassengers(int nbPassengers) {
		this.nbPassengers = nbPassengers;
	}

	public ArrayList<Driver> getDrivers() {
		return drivers;
	}

	public void setDrivers(ArrayList<Driver> drivers) {
		this.drivers = drivers;
	}

	// Methods to compute compatibility of ride and car
	
	// Method to check is the ride type is compatible with the car type
	
	public boolean compatibleWithRideType(Ride ride) {
			return(ride.getCompatibleCars().contains(this.carType));
			
	}
	
	// Method to check compatibiltiy between passengers and seats available
	
	public boolean enoughSeatsAvailable(Ride r) {
		return(r.getNbPassengers()<=(this.nbSeats-this.nbPassengers));
	}
	@Override
	public String toString() {
		return "Car: " + this.carType + ", ID: " + ID + ", Nb of seats:" + this.nbSeats;
	}
	
	public static void main(String[] args) {
		
		UberX u = new UberX();
		Berline b = new Berline(new GPS(), 4);
		Car c = (Car) b;
		u.setCar(c);
		System.out.print(c.compatibleWithRideType(u));
		System.out.print(c.enoughSeatsAvailable(u));
		
	}

}

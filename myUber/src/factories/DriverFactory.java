package factories;
import java.util.*;
import balances.*;
import factories.*;
import rides.*;
import sorters.Sorter;
import users.*;
import users.Observer;
import cars.*;
import other.*;
import users.*;

public class DriverFactory extends AbstractFactory implements VisitableFactory { /* Singleton Pattern applied to have unique Driver Factroy */
	
	private static DriverFactory instance = new DriverFactory();
	private ArrayList<Driver> drivers = new ArrayList<Driver>();
	
	public static DriverFactory getInstance() {
		return instance;
	}
	
	public ArrayList<Driver> getDrivers() {
		return drivers;
	}

	public void setDrivers(ArrayList<Driver> drivers) {
		this.drivers = drivers;
	}
	
	public void addDriver(Driver d) {
		this.drivers.add(d);
	}



	@Override
	public Car newCar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer newCustomer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	// a supprimer
	public Driver newDriver() {
	Scanner scan = new Scanner(System.in) ; 
		
		System.out.println("Nom : ") ;
		String nom = scan.next() ;
		
		System.out.println("Prénom : ") ;
		String prenom = scan.next() ;
		
		scan.close();
		
		System.out.print("Successful creation. \r\nYour Customer ID is:" + Driver.getCompteur() + "\r\n");
		Driver d = new Driver(nom, prenom);
		this.drivers.add(d);
		return d;
	}
	
	public Driver newDriver(String prenom, String nom) {
		Driver d = new Driver(nom, prenom);
		this.drivers.add(d);
		return d;
	}
	
	public Driver newDriver(String prenom, String nom, Car c) {
		if(c.isAvailable()) {
			Driver d = new Driver(prenom, nom, c);
			this.drivers.add(d);
			return d;
		}
		else {
			Driver d = new Driver(prenom,nom);
			this.drivers.add(d);
			return d;
		}
	}

	public boolean driverExists(String name, String surname) {
		boolean driverExists = false;
		int i = 0;
		while(driverExists == false && i<this.drivers.size()) {
			Driver d = this.drivers.get(i);
			if(d.getName().equalsIgnoreCase(name) && d.getSurname().equalsIgnoreCase(surname)) {
				driverExists = true;
			}
			i += 1;
		}
		return driverExists;
	}
	
	public Driver getDriver(String name, String surname) {
		Driver targetDriver = null;
		for(Driver d : this.drivers) {
			if(d.getName().equalsIgnoreCase(name) && d.getSurname().equalsIgnoreCase(surname)) {
				targetDriver = d;
			}
		}
		return targetDriver;
	}
	
	public String displayDrivers() {
		String display = "";
		for(Driver d : this.drivers) {
			display += d.toString() + "\r\n";
		}
		return display;
	}
	@Override
	public Ride newRide(String rideType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Observer> accept(Sorter sorter) {
		return sorter.visit(this);
	}

}

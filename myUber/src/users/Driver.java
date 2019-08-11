package users;

import cars.*;
import priceCalculator.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;
import balances.*;
import booking.BookOfRides;
import factories.*;
import other.*;
import priceCalculator.TrafficCalculatorGenerator;
import rides.*;
import other.*;
public class Driver implements Observer  {
	
	private static int compteur = 1;
	private int id;
	private String name = "name";
	private String surname = "surname";
	private boolean state = true; /* state = true if driver is on-duty */
	private boolean onARide = false; /* true if the driver is taking a customer on a ride or driving towards a customer after a request */
	private Car currentCar = null;
	private DriverBalance balance;
	private MessageBox messageBox = new MessageBox();
	private Ride currentRide = null;
	private GPS gps = new GPS();
	
	// Constructors
	
	public Driver() {
		this.id = Driver.getCompteur();
		Driver.compteur += 1;
		this.balance = new DriverBalance(this);
	}
	public Driver(String name, String surname) {
		this.id = Driver.getCompteur();
		Driver.compteur += 1;
		this.name = name;
		this.surname = surname;
		this.balance = new DriverBalance(this);
	}
	
	public Driver(String name, String surname, boolean state, Car currentCar) {
		this.id = Driver.getCompteur();
		Driver.compteur += 1;
		this.name = name;
		this.surname = surname;
		this.state = state;
		this.currentCar = currentCar;
		this.balance = new DriverBalance(this);
		currentCar.setCurrentDriver(this);
	}
	
	public Driver(String name, String surname, Car currentCar) {
		this.id = Driver.getCompteur();
		Driver.compteur += 1;
		this.name = name;
		this.surname = surname;
		this.currentCar = currentCar;
		this.balance = new DriverBalance(this);
		currentCar.setCurrentDriver(this);
	}
	
	// Getters and setters
	
	public static int getCompteur() {
		return compteur;
	}

	public static void setCompteur(int compteur) {
		Driver.compteur = compteur;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOnARide() {
		return onARide;
	}

	public void setOnARide(boolean onARide) {
		this.onARide = onARide;
	}

	public Ride getCurrentRide() {
		return currentRide;
	}

	public void setCurrentRide(Ride currentRide) {
		this.currentRide = currentRide;
	}

	public GPS getGps() {
		return gps;
	}

	public void setGps(GPS gps) {
		this.gps = gps;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public Car getCurrentCar() {
		return currentCar;
	}

	public void setCurrentCar(Car currentCar) {
		if(currentCar.isAvailable()) {
			this.currentCar = currentCar;
			this.currentCar.setCurrentDriver(this);
			this.gps = currentCar.getGps();
			this.currentCar.setAvailability(false);
		}
		else {
			System.out.print("Car is unavailable \r\n");
		}
		
	}

	public DriverBalance getBalance() {
		return balance;
	}

	public void setBalance(DriverBalance balance) {
		this.balance = balance;
	}
	
	public MessageBox getMessageBox() {
		return messageBox;
	}

	public void setMessageBox(MessageBox messageBox) {
		this.messageBox = messageBox;
	}

	public void sendMessage(String message) {
		this.getMessageBox().addMessage(message);
	}
	
	// Getter methods which require the driver's balance
	
	public double getAvgMark() {
		DriverBalance b = this.balance;
		return b.getAvgMark();
	}
	
	public double getRatio() {
		DriverBalance b = this.balance;
		return b.getRatio2();
	}
	
	public double distanceToCustomer(Customer customer) {
		GPS customerGPS = customer.getGps();
		return customerGPS.calculateDistance(this.getCurrentCar().getGps());
		
	}

	// toString method
	
	public String toString() {
		return(this.surname + "  " + this.name + ", Car: " + this.currentCar.getID() + "\r\n" + this.balance.toString());
	}
	
	/* Methods used to edit the driver's on-off duty state and on-a-ride state
	 * It changes the driver's boolean attributes and also gets the state change time in order to calculate the on-duty rate of driving KPI
	 */
	
	public void updateDutyState() {
		this.balance.updateDutyState();
	}
	
	public void updateRideState() {
		this.balance.updateRideState();
	}
	
/* Method used to check if a driver can accept a request. Possible to modify it according to the need */
	
	public boolean canAcceptRequest(Ride r) {
		if(this.getCurrentCar().compatibleWithRideType(r) && this.getCurrentCar().enoughSeatsAvailable(r)) {
			return true;
		}
		else {
			return false;
		}
	}

	public void acceptRequest(Ride r) throws InterruptedException {
					
			if(r.getState().equalsIgnoreCase("unconfirmed")) {
				r.getCustomer().setUpcomingRide(r);
				r.setDriver(this);
				r.setChange("confirmed");
				this.pickUpCustomer(r);
			}
			else {
				this.sendMessage("Ride " + r.toString() + " has already been confirmed by another driver.");
			}	
						
	}	

	public void pickUpCustomer(Ride r) throws InterruptedException {
		
		Customer c = r.getCustomer();
		
		
		String trafficCondition = r.getTrafficCondition();
		PriceCalculator pc = TrafficCalculatorGenerator.getTrafficCalculator(trafficCondition);
		TrafficCalculator tc = (TrafficCalculator) pc;
		
		double pickUpDuration = tc.calculateDuration(this.currentCar.getGps(), r.getDeparture()); // duration in hours for the driver to reach the customer 
		Time pickUpDurationTime = Time.hoursToTime(pickUpDuration);
		c.sendMessage("Driver will arrive approximatively at : " + r.getReservationTime().getTimeSum(pickUpDurationTime).toString());
		long timeout = (long) pickUpDurationTime.TimeToSeconds();
		TimeUnit.SECONDS.sleep(timeout/10000);
		
		if(r.getState().equalsIgnoreCase("confirmed")) {
			this.getCurrentCar().setGps(c.getGps()); /* driver "teleports" to customer departure */
			r.setChange("ongoing");
			this.driveCustomerToDestination(r);
		}
		
		
	}
	
	public void driveCustomerToDestination(Ride r) throws InterruptedException {
		
		r.setPickUpTime(Time.getLocalTime()); /* sets pick up time when driver reaches customer */
		
		Customer c = r.getCustomer();
		String trafficCondition = r.getTrafficCondition();
		PriceCalculator pc = TrafficCalculatorGenerator.getTrafficCalculator(trafficCondition);
		TrafficCalculator tc = (TrafficCalculator) pc;
		double rideDuration = tc.calculateDuration(r); /* duration in hours */
		
		Time rideDurationTime = Time.hoursToTime(rideDuration);
		
		Time arrivalTime = r.getPickUpTime().getTimeSum(rideDurationTime);
		r.setArrivalTime(arrivalTime);
		c.sendMessage("Estimated Arrival Time : " + arrivalTime.toString());
		r.setDuration(rideDurationTime);
		long rideTime = (long) rideDurationTime.TimeToSeconds();
		TimeUnit.SECONDS.sleep(rideTime/10000); /* "sleep" while driver takes customer to his destination */
		this.currentCar.setGps(r.getDestination()); /* once the ride is over, customer and car are "teleported" at the destination */
		c.setGps(r.getDestination()); 
		r.setChange("completed"); /* ride is completed and method will ask customer to rate driver and ride */
	}
	
	@Override
	public void addCompletedRide(Ride r) {
		this.balance.addDriverRide(r);
	}
	
	@Override
	public void update(Observable o, String state) throws InterruptedException {
		Ride r = (Ride) o ;
		if(state.equalsIgnoreCase("unconfirmed")) {
			this.sendMessage("\r\nNew request from customer: \r\n"
					+ "Ride : " + "\r\n");
			this.acceptRequest(r); /* pour choisir si le driver accepte ou non */
		}
		else if(state.equalsIgnoreCase("confirmed")) {
			this.updateRideState();
			this.sendMessage("The ride " + r.toString() + " has been confirmed.\r\n");
			
		}
		else if(state.equalsIgnoreCase("canceled")) {
			this.updateRideState();
			this.sendMessage("The ride " + r.toString() + " has been canceled by customer.\r\n");
		}
		else if(state.equalsIgnoreCase("ongoing")) {
			
		}
		else if(state.equalsIgnoreCase("completed")) {
			this.addCompletedRide(r);
			this.sendMessage("The ride " + r.toString() + " has been completed.\r\n");
		}
	}
	
	public double distanceToCar(Car car) {
		GPS carGPS =  car.getGps();
		return carGPS.calculateDistance(this.gps) ;
	}

	// Trier les voitures disponibles 
	
		public void sortCarsAvailable(double maxDistance, ArrayList<Car> carsAvailable) {
			ArrayList<Car> aux = new ArrayList<Car>() ;
			int index = 0;
			for (Car c : carsAvailable) {
				if (this.distanceToCar(c)> this.distanceToCar(aux.get(index))) {
					aux.add(index, c);
					index++;
				}
			}
		}
		
		// chercher une voiture disponible
		
		public void rentCar(String type, double maxDistance) {
			ArrayList<Car> CarsAvailable = new ArrayList<Car>() ;
			if (type.equalsIgnoreCase("Van")) {
				for(Car c : CarFactory.getInstance().getVans()) {
					if (c.isAvailable()) {
						CarsAvailable.add(c) ;
					}
				}
			}
			else if (type.equalsIgnoreCase("Berline")) {
				for(Car c : CarFactory.getInstance().getBerlines()) {
					if (c.isAvailable()) {
						CarsAvailable.add(c) ;
					}
				}
			}
			else if (type.equalsIgnoreCase("Standard")) {
				for(Car c : CarFactory.getInstance().getStandards()) {
					if (c.isAvailable()) {
						CarsAvailable.add(c) ;
					}
				}
			}
			else {
				System.out.println("Error : this type " + type + " doesn't exist ");
			}
			this.sortCarsAvailable(maxDistance, CarsAvailable); 
			this.setCurrentCar(CarsAvailable.get(0));
			CarsAvailable.get(0).setCurrentDriver(this);
		}
		
		public static void main(String[] args) {
			/*
			Standard s = new Standard(new GPS(19, 18), 4);
			Driver d = new Driver("A", "A");
			d.setCurrentCar(s);
			
			UberPool up = new UberPool(new GPS(0,0), new GPS(4,4));
			
			System.out.print(d.canAcceptRequest(up));
			*/
			
			Driver d = new Driver("A", "A");
			d.updateDutyState();
			System.out.print(d.isState());
		}
	
		
}

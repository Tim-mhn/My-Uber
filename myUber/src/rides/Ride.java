package rides;

import cars.Car;
import booking.*;
import factories.RideFactory;
import myUber.myUber;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import booking.BookOfRides;
import other.GPS;
import priceCalculator.*;
import users.Customer;
import users.Driver;
import users.Observer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import other.Time;
import booking.*;

public class Ride implements Observable, Visitable {
	
	private Customer customer;
	private Driver driver = null;
	private Car car = null;
	private String rideType = "";
	private int nbPassengers = 1;
	private GPS departure = new GPS();
	private GPS destination = new GPS();
	private Time reservationTime = new Time();
	private Time pickUpTime = new Time();
	private Time cancelTime = null;
	private Time arrivalTime = new Time();
	private String state = null;
	private int driverMark = -1;
	private int rideMark = -1;
	private Time duration = null;
	private ArrayList<Double> costs = new ArrayList<Double>();
	private String trafficCondition = "";	
	private final double[] trafficRates = {-1, -1, -2};
	private final double[] basicRates = {-1, -1, -1, -2};
	private boolean changed = false;
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	protected ArrayList<String> compatibleCars = new ArrayList<String>();
	private ArrayList<Ride> ridesCompatible = null;;
	private ArrayList<Customer> customers = new ArrayList<Customer>();
	private ArrayList<Driver> driverList = new ArrayList<Driver>();
	private double length = -1;
	
	// Constructors
	public Ride() {
		super();
	}
	
	public Ride(Customer customer, GPS departure, GPS destination) { /* constructeur à utiliser pour la requete d'un client à envoyer aux drivers */
		super();
		this.customer = customer;
		this.departure = departure;
		this.destination = destination;
		this.state = "unconfirmed";
	}

	public Ride(int nbPassengers) {
		this.nbPassengers = nbPassengers;
	}
	
	

	public Ride(GPS departure, GPS destination) { /* constructeur utile pour tester les méthodes de calcul de distance et de prix */
		super();
		this.customer = new Customer();
		this.departure = departure;
		this.destination = destination;
	}
	
	public Ride(Customer customer, GPS departure, GPS destination, int nbPassengers) {
		this.customer = customer;
		this.departure = departure;
		this.destination = destination;
		this.nbPassengers = nbPassengers;
	}

	
	// Getters and setters 
	public Customer getCustomer() {
		return customer;
	}


	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(int indice, Customer customer) {
		this.customers.add(indice, customer);
		this.registerObserver(customer);
	}
	
	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ArrayList<Observer> getObservers() {
		return observers;
	}

	public void setObservers(ArrayList<Observer> observers) {
		this.observers = observers;
	}

	public void setLength(double length) {
		this.length = length;
	}
	
	public double getLength() {
		if(length == -1) {
			this.length = departure.calculateDistance(destination);
		}
		
		return this.length;
	}

	public Time getPickUpTime() {
		return pickUpTime;
	}

	public void setPickUpTime(Time pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	public void addCustomer(Customer customer) {
		if(this.customers.equals(null)) {
			ArrayList<Customer> customerList = new ArrayList<Customer>();
			customerList.add(customer);
			customers = customerList;
		}
		else {
			if(!customers.contains(customer)) {
				customers.add(customer);
			}
			
		}
		if(!this.observers.contains(customer)) {
			this.registerObserver(customer);
		}
		
	}
	public Time getReservationTime() {
		return reservationTime;
	}

	public void setReservationTime(Time reservationTime) {
		this.reservationTime = reservationTime;
		this.setTrafficCondition(TrafficCalculatorGenerator.estimateTrafficState(reservationTime));
	}
	
	
	public Time getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Time arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Time getDuration() {
		return duration;
	}

	public void setDuration(Time duration) {
		this.duration = duration;
	}

	public Driver getDriver() {
		return driver;
	}


	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public int getNbPassengers() {
		return nbPassengers;
	}

	public void setNbPassengers(int nbPassengers) {
		this.nbPassengers = nbPassengers;
	}

	public Car getCar() {
		return car;
	}


	public void setCar(Car car) {
		this.car = car;
	}


	public ArrayList<Driver> getDriverList() {
		return driverList;
	}

	public void setDriverList(ArrayList<Driver> driverList) {
		this.driverList = driverList;
	}

	public GPS getDeparture() {
		return departure;
	}


	public void setDeparture(GPS departure) {
		this.departure = departure;
	}

	public ArrayList<Ride> getRidesCompatible() {
		if(ridesCompatible.equals(null)) {
			ArrayList<Ride> compatibleRides = new ArrayList<Ride>();
			for(Ride r : BookingUberpool.getListRequest()) {
				
				/* 
				 * If compatible rides have never been calculated before (ie, equals null),
				 * The system looks for all compatible rides without any max distance (ie, max distance = infinity)
				 */
				if(!(r.equals(this)) && this.isCompatible(r, Double.POSITIVE_INFINITY)) {
					compatibleRides.add(r);
				}
			}
			this.ridesCompatible = compatibleRides;
		}
		
		return ridesCompatible;
	}

	public void setRidesCompatible(ArrayList<Ride> ridesCompatible) {
		this.ridesCompatible = ridesCompatible;
	}

	public GPS getDestination() {
		return destination;
	}


	public void setDestination(GPS destination) {
		this.destination = destination;
	}
	

	public String getTrafficCondition() {
		return trafficCondition;
	}

	public void setTrafficCondition(String trafficCondition) {
		this.trafficCondition = trafficCondition;
	}

	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}

	public int getDriverMark() {
		return driverMark;
	}
	
	
	public void setCompatibleCars(ArrayList<String> compatibleCars) {
		this.compatibleCars = compatibleCars;
	}

	public ArrayList<String> getCompatibleCars() {
		return compatibleCars;
	}

	public void setDriverMark(int driverMark) {
		this.driverMark = driverMark;
	}


	public int getRideMark() {
		return rideMark;
	}

	public void setRideMark(int rideMark) {
		this.rideMark = rideMark;
	}

	public double getCost() {
		return costs.get(0);
	}

	public double getCost(int index) {
		return costs.get(index);
	}
	public void setCost(double cost) {
		this.costs.add(cost);
	}
	
	public ArrayList<Double> getCosts() {
		return costs;
	}

	public void setCosts(ArrayList<Double> costs) {
		this.costs = costs;
	}
	public void addCost(double cost) {
		this.costs.add(cost);
	}

	public String getRideType() {
		return rideType;
	}

	public void setRideType(String rideType) {
		this.rideType = rideType;
	}

	public double[] getBasicrates() {
		return this.basicRates;
	}

	public double[] getTrafficrates() {
		return this.trafficRates;
	}

	public double getBasicRate(int index) {
		return this.getBasicrates()[index];
	}
	
	public double getTrafficRate(int index) {
		return this.getTrafficrates()[index];
	}	
	
	public Time getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Time cancelTime) {
		this.cancelTime = cancelTime;
	}
	

	/* Calculate distance between departure and destination of ride */
	public double calculateLength() {
		return this.departure.calculateDistance(this.destination); /* distance entre départ et arrivée, fonction dans classe GPS */
	}


	// Method for visitor pattern (with calculators = visitors)
	@Override
	public double accept(PriceCalculator calculator) {
		// TODO Auto-generated method stub
		return calculator.visit(this);
	}

	// Methods for Observer pattern
	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
		
	}

	@Override
	public void notifyObservers() throws InterruptedException {
		if(this.changed) {
			for(Observer o : this.observers) {
				o.update(this, this.state);
			}
		}
		this.changed = false;
		
	}

	@Override
	public void removeObservers() {
		observers = new ArrayList<Observer>();
	}
	
	@Override
	public void setChange(String state) throws InterruptedException {
		this.changed = true;
		this.state = state;
		
		if(state.equalsIgnoreCase("unconfirmed")) { /* lorsque le client choisit son type de trajet (passe le ride à l'état un confirmed, on notifie tous les observateurs */
			this.notifyObservers();
			
		}
		else if(state.equalsIgnoreCase("confirmed")){ /* lorsqu'un driver valide le ride, on ne notifie que le driver et le client */
			this.removeObservers();
			for(Customer c : this.customers) {
				this.registerObserver(c);
			}
			this.registerObserver(this.driver);
			this.notifyObservers();
		}
		else if (state.equalsIgnoreCase("ongoing")) {
			this.notifyObservers();
		}
		else if(state.equalsIgnoreCase("completed")) { /* lorsque le ride est terminé, on notifie le driver et le client avant de les retirer des observateurs */
			BookOfRides.addRide(this); /* add ride to the book of ridesonce it is completed */
			this.askRideMarks();
			this.notifyObservers();
			this.removeObservers();
			
			
		}
		else if(state.equalsIgnoreCase("canceled")) { /* lorsque le ride est annulé, on notifie le driver et le client avant de les retirer des observateurs */
			this.notifyObservers();
			this.removeObservers();
		}
	}
	
	// Methods for UberPool
	
	/* 
	 * sortCompatibleRides uses the compatible rides list and sorts them according to the distance between customers 
	 */
	
	public void sortCompatibleRides() {
		ArrayList<Ride> aux = new ArrayList<Ride>() ;
		int i = 1;
		for (Ride r : this.getRidesCompatible()) {
			while (i<aux.size() && r.getCustomer().distanceTo(this.getCustomer())>aux.get(i).getCustomer().distanceTo(this.getCustomer())) {
				i++;
			}
			if (i<aux.size()) {
				aux.add(i-1,r);
			}
			else {
				aux.add(r);
			}
		}
		this.setRidesCompatible(aux) ;
	}
	// Methods to get the lists of departures and destinations of a ride list (for UberPool)
	public static ArrayList<GPS> getDepartures(ArrayList<Ride> rides){
		ArrayList<GPS> departures = new ArrayList<GPS>();
		for(Ride r : rides) {
			departures.add(r.getDeparture());
		}
		return departures;
	}
	
	public static ArrayList<GPS> getDestinations(ArrayList<Ride> rides){
		ArrayList<GPS> destinations = new ArrayList<GPS>();
		for(Ride r : rides) {
			destinations.add(r.getDestination());
		}
		return destinations;
	}

	@Override
	public String toString() {
		return "Ride [Ride-Type = " + "Departure = " + departure.toString() 
				+ "; Destination = " + destination.toString() + "; Cost = " + costs +"]";
	}
	
	// Second toString method used in the book of rides
	public String toStringCompleted() {
		if(this.state.equalsIgnoreCase("completed")) {
			return "Driver: " + this.driver.toString() + "\r\nCar: " + this.car.toString() + "\r\nCustomer: " + this.customer.toString() + "\r\nDeparture from " + this.departure.toString()
			+ " at " + this.pickUpTime.toString() + "\r\nArrival at" + this.destination.toString() + " at " + this.arrivalTime.toString();
		}
		else {
			return this.state; /* to be completed: check if all former attributes can be called when a ride is ongoing or canceled */
		}
		
				
	}

	
	// methods to ask the customer the marks of ride once the ride is completed 
	
	public void askRideMarks() {
		Scanner scan = new Scanner(System.in);
		double globalRideMark = 0;
		double globalDriverMark = 0;
		if(myUber.getMode().equals("CLUI")) {
			for(Customer c : this.customers) {
				int rideMark = c.askRideMark(this);
				int driverMark = c.askDriverMark(this);
				globalRideMark += rideMark;
				globalDriverMark += driverMark;
			}
			int n = this.customers.size();
			
			this.rideMark = (int) globalRideMark/n;
			this.driverMark = (int) globalDriverMark/n;
		}
		
		
		
		
		
	}
	
	
	// Methods for UberPool Booking
	
	public boolean enoughSeats(Ride r2) {
		return this.nbPassengers + r2.getNbPassengers() < 5;
	}
	
	public boolean isInClosePerimeter(Ride r2, double maxDistance) {
		ArrayList<Ride> ridesDuo = new ArrayList<Ride>();
		ridesDuo.add(this);
		ridesDuo.add(r2);
		double distance = this.calculateLength();
		return GPS.calculateMinPossibleDistancePath(ridesDuo) < Math.max(distance * 2, maxDistance);
	}
	
	public boolean isCompatible(Ride r2, double maxDistance) {
		return this.enoughSeats(r2) && this.isInClosePerimeter(r2, maxDistance);
	}
	
	// Methods to generate quickly rides list. Very useful in the uberpool process 
	public ArrayList<Ride> generateRideList(Ride r2){
		ArrayList<Ride> rides = new ArrayList<Ride>();
		rides.add(this);
		rides.add(r2);
		
		return rides;
	}
	
	public ArrayList<Ride> generateRideList(Ride r2, Ride r3){
		ArrayList<Ride> rides = new ArrayList<Ride>();
		rides.add(this);
		rides.add(r2);
		rides.add(r3);
		return rides;
	}
	
	
	public static void main(String[] args) {
		
		/* Customer c1 = new Customer("J", "J", new GPS(), 1);
		Ride r = new Ride();
		r.registerObserver(c1);
		r.setChange("unconfirmed"); */
		
		Ride r1 = new Ride(new GPS(30,28), new GPS(38,39));
		Ride r2 = new Ride(new GPS(29.5,27.9), new GPS(37.8, 38.9));
		System.out.print(r1.isInClosePerimeter(r2, 60));
		
		
	}
	
}

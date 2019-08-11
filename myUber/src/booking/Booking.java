package booking;

import balances.*;
import cars.*;
import factories.*;
import other.*;
import priceCalculator.*;
import rides.*;
import users.*;
import java.util.ArrayList;
import java.util.Comparator;
import users.*;
// Pour chaque connexion d'un client et demadne de ride, on créé un nouvel objet Booking 

public class Booking { 
	
	private ArrayList<Ride> rideTypes = new ArrayList<Ride>(); /* une liste vide oùva récupérer un ride de chaque classe, utile pour le calcul des prix */
	protected Customer customer = null; /* le client qui effectue la demande */
	private int nbPassengers;
	protected Ride ride;
	private ArrayList<Driver> drivers = new ArrayList<Driver>();
	private ArrayList<Driver> sortedDriverList = new ArrayList<Driver>(); 
	private Driver driver;
	
	// Constructors
	
	// constructeur de base où on entre que le client. 
	// On remplit la liste des rides grâce à la ride factory
	// On ajoute tous les drivers grâce à la driver factory 
	
	public Booking(Customer customer, int nbPassengers) throws InstantiationException, IllegalAccessException { 
		this.customer = customer;
		this.nbPassengers = nbPassengers;
		customer.setBooking(this);
	}
	
	public Booking(Customer customer, int nbPassengers, ArrayList<Driver> drivers) throws InstantiationException, IllegalAccessException {
		/*for(Ride r : RideFactory.getInstance().getRideTypes()) {
			this.rideTypes.add(r.getClass().newInstance());
		}*/
		this.customer=customer;
		this.nbPassengers= nbPassengers;
		this.drivers = drivers;
		customer.setBooking(this);
	}
	// constructeur qui tient compte d'une distance maximale entre le client et les drivers 
	// afin de ne pas envoyer la requête du client à tous les drivers
	
	public Booking(double maxDistance, Customer customer, int nbPassengers) throws InstantiationException, IllegalAccessException { 
		this.customer = customer;
		this.nbPassengers = nbPassengers;
		customer.setBooking(this);
		for(Ride r : RideFactory.getInstance().getRideTypes()) {
			this.rideTypes.add(r.getClass().newInstance());
		}
		for(Driver d : DriverFactory.getInstance().getDrivers()) { /* constructeur qui ajoute seulement les drivers les plus proches afin de ne pas envoyer la requête à tous les drivers */
			if(d.distanceToCustomer(this.customer) < maxDistance) {
				drivers.add(d);
			}
		}
		
	}
	// Getters and setters
	
	public ArrayList<Ride> getRideTypes() {
		return rideTypes;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ArrayList<Driver> getDrivers() {
		return drivers;
	}

	public void setDrivers(ArrayList<Driver> drivers) {
		this.drivers = drivers;
	}

	public void setRideTypes(ArrayList<Ride> rideTypes) {
		this.rideTypes = rideTypes;
	}
	
	public ArrayList<Driver> getSortedDriverList() {
		return sortedDriverList;
	}

	public void setSortedDriverList(ArrayList<Driver> sortedDriverList) {
		this.sortedDriverList = sortedDriverList;
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

	public Ride getRide() {
		return ride;
	}

	public void setRide(Ride ride) {
		if(this instanceof BookingUberpool) {
			BookingUberpool.listRequests.add(ride);
		}
		this.ride = ride;
	}

	
	// Useful Methods
	
	/* Method to request a rideaccording to a GPS departure and GPS destination
	 * Allows customer to get the prices for the different types of ride thanks to getRidePrices Method
	 * Traffic Condition is generated thank toa traffic calculator generator, which depends on the current time
	 */
	public ArrayList<String> request(Customer customer,int nbPassengers, GPS departure, GPS destination, double maxDistance) throws InstantiationException, IllegalAccessException {
		this.searchRide(maxDistance, departure, destination);
		String trafficCondition = TrafficCalculatorGenerator.estimateCurrentTraffic();
		return this.getRidePrices(customer, departure, destination, trafficCondition);
	}
	
	/* SearchRide Method is used to add the different attributes such as customer, nbPassengers to the list of rides among which one will be picked
	 * It also filters the drivers which will be likely to accept the request
	 * */
	public void searchRide(double maxDistance, GPS departure, GPS destination) throws InstantiationException, IllegalAccessException {
		for(Ride r : RideFactory.getInstance().getRideTypes()) {
			Ride ride = r.getClass().newInstance() ;
			ride.addCustomer(customer);
			ride.setCustomer(customer);
			ride.setNbPassengers(nbPassengers);
			ride.setDeparture(departure);
			ride.setDestination(destination);
			this.rideTypes.add(ride);
		}
		
		for(Driver d : DriverFactory.getInstance().getDrivers()) {  /*constructeur qui ajoute seulement les drivers les plus proches afin de ne pas envoyer la requête à tous les drivers */
			if(d.distanceToCustomer(this.customer) < maxDistance && d.getCurrentCar().enoughSeatsAvailable(this.rideTypes.get(0))) {
					this.drivers.add(d);
			}
		}
	}
	
	/* Method used to send to the customer the prices of the different ride types according to his journey + traffic conditions */
	public ArrayList<String> getRidePrices(Customer customer, GPS departure, GPS destination, String trafficCondition) {
		
		PriceCalculator c = TrafficCalculatorGenerator.getTrafficCalculator(trafficCondition); /* on génère un calculateur en fonction des conditions de circulation */
		int i = 1;
		double distance = departure.calculateDistance(destination);
		customer.sendMessage("Ride distance: " + Math.round(distance) + "km \r\n");
		ArrayList<String> options = new ArrayList<String>();
		for(Ride r : rideTypes) {
			
			r.setDeparture(departure);
			r.setDestination(destination);
			r.addCustomer(customer);
			r.setTrafficCondition(trafficCondition);
			r.setReservationTime(Time.getLocalTime());
			double cost = r.accept(c);
			r.setCost(Math.round(cost));
			options.add("Option " + i + " : " + r.getClass().getSimpleName() + " : " + Math.round(cost) +"€\r\n");
			customer.sendMessage(options.get(i-1));
			i +=1;
		}
		return options;
		
	}
	
	
	/* Other version of getRidePrices method which generates insidethe code the traffic condition
	 * This version is not yet used
	 */
	public ArrayList<String> getRidePrices(Customer customer, GPS departure, GPS destination) {
			
			String trafficCondition = TrafficCalculatorGenerator.estimateCurrentTraffic();
			PriceCalculator c = TrafficCalculatorGenerator.getTrafficCalculator(trafficCondition); /* on génère un calculateur en fonction des conditions de circulation */
			int i = 1;
			ArrayList<String> options = new ArrayList<String>();
			for(Ride r : rideTypes) {
				r.setDeparture(departure);
				r.setDestination(destination);
				r.getCustomers().add(customer);
				r.setTrafficCondition(trafficCondition);
				double cost = r.accept(c);
				r.setCost(Math.round(cost));
				String message = "Option " + i + " : " + r.getClass().getSimpleName() + " : " + Math.round(cost) +"€\r\n";
				options.add(message);
				customer.sendMessage(message);
				i +=1;
			}
			return options;
			
		}
	
	
	
	/* Once the different prices are displayed, the user can choose his ride type with optionNum
	 * This will activate the second phase of booking (ie research of drivers, validation of ride and pick up of customer
	 */
	public void chooseRideType(int optionNum, double maxDistance) throws InterruptedException { /*customer chooses his ride according to option number shown in his message box */
			this.ride = rideTypes.get(optionNum-1);
			this.getRide().setNbPassengers(this.nbPassengers);
			this.getRide().registerObserver(this.customer);
			this.ride.setChange("unconfirmed");
			this.ride.setCustomer(this.customer);
			for(Driver d : this.drivers) {
				if(d.canAcceptRequest(this.ride) && d.distanceToCustomer(this.customer)<maxDistance) {
					this.ride.registerObserver((Observer) d);
					this.ride.getDriverList().add(d);
				}
				 /* une fois que le client choisit le type de trajet qu'il souhaite, on ne s'intéresse qu'au ride correspondant */
												/* et on lui ajoute les bons paramètres: nombre de passagers et les observateurs */
			}
	}
	
	
	public void treating(int optionNum, double maxDistance) throws InterruptedException, InstantiationException, IllegalAccessException {
		this.chooseRideType(optionNum, maxDistance);
		if (this.ride.getRideType()== "UberPool") {
			BookingUberpool up = new BookingUberpool(customer, nbPassengers) ;
			up.setRide(this.ride);
			up.setDrivers(this.ride.getDriverList());
			up.treat(maxDistance);
			if(up.searchRidesCompatible(maxDistance, this.ride).size()>0) {
				up.sortDrivers();
				up.selectDriver(maxDistance);
				System.out.print("OBSERVERS : " + this.ride.getObservers());
			}
			else {
				BookingUberpool.getListRequest().add(this.ride);
			}	
			}
		else {
			ClassicBooking b = new ClassicBooking(customer, nbPassengers);
			b.setRide(this.ride);
			b.setDrivers(this.ride.getDriverList());
			b.sortDrivers();
			b.selectDriver(maxDistance);
			
		}
	}	
		

	
	
	/* Method used to order drivers according their distance from the customer
	 * The closest compatible and available driver will then be picked to pick up the customer
	 */
	
	public void sortDrivers() {
		ArrayList<Driver> clone = (ArrayList<Driver>) drivers.clone();
		ArrayList<Driver> clonedDrivers = clone;
		
		ArrayList<Driver> sortedDrivers = new ArrayList<Driver>();
		
		while(clonedDrivers.size()>1) {
			Driver closestDriver = DriverDistanceToCustomerComparator.getClosestDriver(clonedDrivers, this.customer);
			sortedDrivers.add(closestDriver);
			clonedDrivers.remove(closestDriver);
		}
		if(clonedDrivers.size()>0) {
			sortedDrivers.add(clonedDrivers.get(0));
			sortedDriverList = sortedDrivers;
		}
		
	}
	
	/* Closest compatible available driver is chosen by the system */
	public void selectDriver(double maxDistance) throws InterruptedException {
		int n = this.ride.getDriverList().size();
		if(n>0) {
			this.sortDrivers();
			Driver d = this.sortedDriverList.get(0);
			d.acceptRequest(this.ride);
			this.driver = d;
		}
		
		else {
			this.customer.sendMessage("No driver available for this ride type. Please choose another ride type or try later.");
		}
	}
		
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, InterruptedException {
		
		/*SystemBalance system = SystemBalance.getInstance();
		
		Standard s = new Standard(new GPS(20,21), 4);
		DriverFactory df = DriverFactory.getInstance();
		Driver d = df.newDriver("A", "B");
		d.setCurrentCar(s);
		
		CustomerFactory cF = CustomerFactory.getInstance();
		Customer c = cF.newCustomer("A", "A");
		c.setGps(new GPS(19,19));
		Customer c2 = cF.newCustomer("B", "B3");
		//Customer c = new Customer("A", "A", new GPS(19,19));
		
		c.requestRide(new GPS(19,19), new GPS(19.08,18.95), 3, 5000);
		System.out.print(c.getBooking().getNbPassengers()+"\r\n");
		c.treatRequest(4, 5000);
		
		System.out.print(d.getBalance());
		System.out.print(d.getBalance().getAvgMark());
		System.out.print(d.getBalance().getNbRides());
		System.out.print(system.getMostChargedCustomer() + "\r\n");
		System.out.print(system.getMostAppreciatedDriver());
		System.out.print("\r\n" + c.getCustomerBalance().toString());*/
		
		Customer c = new Customer("A", "B");
		Booking b = new Booking(c, 1);
		System.out.print("prices: " + b.getRidePrices(c, new GPS(10,10), new GPS(11,11), "low"));

		
		
	}

}


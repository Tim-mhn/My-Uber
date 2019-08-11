package users;

import java.util.*;

import balances.*;
import booking.Booking;
import factories.*;
import rides.*;
import rides.Observable;
import users.*;
import cars.*;
import other.*;
import other.*;

public class Customer implements Observer {
	
	private String nom ;
	private String prenom;
	private int id;
	private static int compteur = 1; /* utile pour créer des id uniques */
	private GPS gps = new GPS();
	private int cardNumber;
	private MessageBox messageBox = new MessageBox();
	private CustomerBalance customerBalance = new CustomerBalance();
	private Ride upcomingRide = null;
	private Booking booking;
	
	// Constructors 
	
	public Customer() { /* test */
		
		this.nom = "Jean";
		this.prenom = "Michel";
		this.id = Customer.compteur;
		Customer.compteur +=1;
		this.gps = new GPS();
		this.cardNumber = 0;
		
	}
	
	public Customer(String nom, String prenom) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.id = Customer.compteur;
		Customer.compteur +=1;
	}



	public Customer(String nom, String prenom, GPS gps) {
		this.nom = nom;
		this.prenom = prenom;
		this.id = Customer.compteur;
		this.gps = gps;
		Customer.compteur += 1;
	}
	public Customer(String nom, String prenom, GPS gps, int cardNumber) { /* constructeur utile */
		this.nom = nom;
		this.prenom = prenom;
		this.id = Customer.compteur;
		this.gps = gps;
		this.cardNumber = cardNumber;
		Customer.compteur += 1;
	}
	
	public Customer(String nom, String prenom, int id, GPS gps, int cardNumber, CustomerBalance customerBalance) {
		this.nom = nom;
		this.prenom = prenom;
		this.id = id;
		this.cardNumber = cardNumber;
		this.customerBalance = customerBalance;
		Customer.compteur += 1;
	}
	
	public void cancelUpcomingRide() {
		this.upcomingRide.setState("canceled");
		this.upcomingRide = null;
	}
	

	public Ride getUpcomingRide() {
		return upcomingRide;
	}

	public void setUpcomingRide(Ride upcomingRide) {
		this.upcomingRide = upcomingRide;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public int getNbRides() {
		Balance b = this.getCustomerBalance();
		return b.getNbRides();
	}
	public static int getCompteur() {
		return compteur;
	}
	public static void setCompteur(int compteur) {
		Customer.compteur = compteur;
	}
	public int getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public GPS getGps() {
		return gps;
	}
	public void setGps(GPS gps) {
		this.gps = gps;
	}
	public MessageBox getMessageBox() {
		return messageBox;
	}
	public void setMessageBox(MessageBox messageBox) {
		this.messageBox = messageBox;
	}
	public double getCharge() {
		CustomerBalance b = this.customerBalance;
		return b.getTotalAmount();
	}
	public void sendMessage(String message) {
		this.getMessageBox().addMessage(message);
		System.out.print(message);
	}
	public CustomerBalance getCustomerBalance() {
		return customerBalance;
	}
	public void setCustomerBalance(CustomerBalance customerBalance) {
		this.customerBalance = customerBalance;
	}
	
	@Override
	public String toString() {
		return "Customer: " + nom + " " + prenom + ", ID: " + id + "\r\n" + this.customerBalance.toString();
	}

	@Override
	public void addCompletedRide(Ride r) {
		CustomerBalance b = this.customerBalance;
		b.addCustomerRide(r, this); /* adds the cost,duration and increments total number of rides */
	}

	public ArrayList<String> requestRide(GPS departure, GPS arrival, int NbPassengers, double maxDistance,int optionNum) throws InstantiationException, IllegalAccessException, InterruptedException {
		this.booking = new Booking(this, NbPassengers);
		return this.booking.request(this,  NbPassengers, departure, arrival, maxDistance);
	}
	
	/* Customer chooses his ride option */
	public void treatRequest(int optionNum, double maxDistance) throws InstantiationException, IllegalAccessException, InterruptedException {
		this.booking.treating(optionNum,maxDistance);
	}
	
	public ArrayList<String> requestRide(GPS departure, GPS destination, int nbPassengers, int maxDistance) throws InstantiationException, IllegalAccessException {
		this.booking = new Booking(this, nbPassengers);
		return this.booking.request(this,  nbPassengers, departure, destination, maxDistance);
	}
	
	/* method to ask customer the ride's marks */
	
	public int askRideMark(Ride r) {
		Scanner scan = new Scanner(System.in);
		this.sendMessage("Please rate your ride. \r\nRide mark: \r\n");
		double rideMark = scan.nextDouble();
		
		while(!(0<=rideMark) || !(rideMark<=5) || rideMark != (int) rideMark) {
			this.sendMessage("Please rate between 0 and 5 \r\n");
			rideMark = scan.nextDouble();
		}
		
		return (int) rideMark;
	}
	
	public int askDriverMark(Ride r) {
		Scanner scan = new Scanner(System.in);
		this.sendMessage("Please rate the driver. \r\nDriver mark: \r\n");
		double driverMark = scan.nextDouble();
		
		while(!(0<=driverMark) || !(driverMark<=5) || driverMark != (int) driverMark) {
			this.sendMessage("Please rate between 0 and 5 \r\n");
			driverMark = scan.nextDouble();
		}
		
		return (int) driverMark;
	}
	
	@Override
	public void update(Observable o, String state) throws InterruptedException {
		Ride r = (Ride) o;
		if(state.equalsIgnoreCase("confirmed")) {
			this.sendMessage("Ride confirmed by driver: " + r.getDriver().toString() +"\r\n");
		}
		if(state.equalsIgnoreCase("ongoing")) {
			this.sendMessage("Driver has just arrived. Please enter the car\r\n");
		}
		if(state.equalsIgnoreCase("completed")) {
			this.addCompletedRide(r); /* adds ride to his balance (duration + cost + nb of rides) */
		}
	}

	
	public int getCustomerIndex(Ride r) {
		int i = 0;
		ArrayList<Customer> customers = r.getCustomers();
		while(i<customers.size() && !(customers.get(i).equals(this))) {
			i+=1;
		}
		
		return i;
	}
	public double distanceTo(Customer customer) {
		return customer.getGps().calculateDistance(this.getGps());
	}
	
	public static void main(String[] args) {
		
		Customer c = new Customer();
		Ride r = new Ride();
		c.askDriverMark(r);
		
		//CustomerFactory cF = CustomerFactory.getInstance();
		//Customer c = new Customer();
	}
		
		
	}



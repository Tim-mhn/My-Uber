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

public class CustomerFactory extends AbstractFactory implements VisitableFactory { /* singleton pattern */
	
	private ArrayList<Customer> customers = new ArrayList<Customer>();
	private static CustomerFactory instance = new CustomerFactory();

	public static CustomerFactory getInstance() {
		return instance;
	}
	@Override
	public Customer newCustomer() {
		
		Scanner scan = new Scanner(System.in) ; 
		
		System.out.println("Nom : ") ;
		String nom = scan.next() ;
		
		System.out.println("Prénom : ") ;
		String prenom = scan.next() ;
		
		System.out.println("Position GPS latitude : ") ;
		float latitude = scan.nextFloat() ;
		System.out.println("Position GPS longitude : ") ; 
		float longitude = scan.nextFloat() ;
		GPS gps = new GPS(latitude, longitude);
		
		System.out.println("N° CB : ") ;
		
		int CB = scan.nextInt() ; 
		scan.close();
		
		System.out.print("Successful creation. \r\nYour Customer ID is:" + Customer.getCompteur());
		Customer c = new Customer(nom, prenom, gps, CB);
		this.customers.add(c);
		return c;
		
	}
	
	public ArrayList<Customer> getCustomers(){
		return this.customers;
	}
	public Car newCar() {
		return null ; 
	
	}
	
	public Driver newDriver() {
		return null ;
		
	}

	public Ride newRide(String rideType) {
		return null ;
		
	}
	
	public Customer newCustomer(String nom, String prenom) {
		Customer c = new Customer(nom, prenom);
		this.customers.add(c);
		return c;
	}
	
	public Customer newCustomer(String nom, String prenom, GPS position) {
		Customer c = new Customer(nom, prenom, position);
		this.customers.add(c);
		return c;
	}

	public String displayCustomers() {
		String display = "";
		for(Customer c : this.customers) {
			display += c.toString() + "\r\n";
		}
		return display;
	}
	public boolean customerExists(int targetID ) {
		boolean customerExists = false;
		int i = 0;
		while(i<this.customers.size() && customerExists == false) {
			Customer c = this.customers.get(i);
			if(c.getId() == targetID) {
				customerExists = true;
			}
			i += 1;
		}
		return customerExists;
	}
	
	public Customer getCustomer(int targetID) {
		Customer targetCustomer = null;
		for(Customer c : this.customers) {
			if(c.getId() == targetID) {
				targetCustomer = c;
			}
		}
		return targetCustomer;
	}
	@Override
	public ArrayList<Observer> accept(Sorter sorter) {
		// TODO Auto-generated method stub
		return sorter.visit(this);
	}


}
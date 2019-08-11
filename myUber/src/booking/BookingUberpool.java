package booking ;

import java.util.ArrayList;

import balances.SystemBalance;
import cars.Standard;
import factories.CustomerFactory;
import factories.DriverFactory;
import factories.RideFactory;
import other.GPS;
import rides.Ride;
import rides.UberPool;
import users.Customer;
import users.Driver;

//Pour chaque connexion d'un client et demande de ride, on créé un nouvel objet Booking 

public class BookingUberpool extends Booking {
	
	
	// Attributes
	
	protected static ArrayList<Ride> listRequests = new ArrayList<Ride>();
		
	// Constructors

	public BookingUberpool(Customer customer, int nbPassengers) throws InstantiationException, IllegalAccessException, InterruptedException { 
		super(customer, nbPassengers) ;
	}
		
	
		
	// Get & Set
		
	public void setListRequests(ArrayList<Ride> requestList) {
		listRequests = requestList ;
	}
		
	public static ArrayList<Ride> getListRequest() {
		return listRequests ;
	}





	
		
	// constructeur qui tient compte d'une distance maximale entre le client et les drivers 
	// afin de ne pas envoyer la requête du client à tous les drivers
		
	public ArrayList<Ride> searchRidesCompatible(double maxDistance, Ride notThisRide) {
		ArrayList<Ride> compatibleRides = new ArrayList<Ride>() ;
		if(listRequests.size()>0) {
			
			for (Ride request : listRequests) {
				if (!(notThisRide.equals(request)) && this.ride.isCompatible(request, maxDistance)) {    // fonction dans ride qui dit si 2 rides sont compatibles (distance < maxDistance)
					compatibleRides.add(request);
				}
			}
		}
		
		else {
			System.out.print("alone");	
		}
		
		return compatibleRides ;
	}
		
	public void treat(double maxDistance) {
		if(this.searchRidesCompatible(maxDistance, this.ride).size() == 0) {
			System.out.print("please wait for another uberpool");
			listRequests.add(this.ride);
		}
		else {
			this.ride.setRidesCompatible(this.searchRidesCompatible(maxDistance, this.ride)) ;
			this.ride.sortCompatibleRides() ;
			for (Ride r : this.ride.getRidesCompatible()) {
				
				/* For all the rides compatible with the new UberPool request, their compatible rides list is updated and sorted */
				r.setRidesCompatible(this.searchRidesCompatible(maxDistance, r));
				r.sortCompatibleRides();
			}
			
			if (this.getRide().getRidesCompatible().size() < 0) {
				System.out.println("Error");
				listRequests.remove(this.ride);
			}
			else {
				ArrayList<Ride> rides = this.selectCustomers(maxDistance);
				for (Ride r : rides) {
					listRequests.remove(r);
				}
			}
		}
		
	}

	public ArrayList<Ride> selectCustomers(double maxDistance) {
		int compteur = 0 ;
		
		while (compteur<this.ride.getRidesCompatible().size() && this.ride.getRidesCompatible().get(compteur).getRidesCompatible().size()<=1) {
			
			/* If Request r1 is made before request r2 and that r1 and r2 are compatible,
			 * r1 will belong to r2's RidesCompatible list but r2 will not belong to r1's RidesCompatible list as it has been calculated before r2 existed
			 */
			
			compteur += 1 ;
		}
		if (compteur==this.ride.getRidesCompatible().size()) {
			
			/* Condition verified is there are only two UberPool compatible requests
			 * If the system never found a compatible trio, then compteur is incremented until compteur = ride.getRidesCompatible().size() */
			
			Ride ride2 = this.ride.getRidesCompatible().get(0);
			this.ride.addCustomer(this.customer); 		// cas ou il n'y a que 2 rides compatibles
			this.ride.addCustomer(ride2.getCustomer());
			ArrayList<Ride> rides = new ArrayList<Ride>();
			rides.add(this.ride);
			rides.add(ride2);
			this.ride.addCost(ride2.getCost());
			return rides;
		}
		else {	
			
			/* If precedent condition is not verified, then there is a trio of compatible UberPool Requests
			 * The UberPool Booking system prioritizes trio of requests compared to duos even though it is longer for customers
			 */
			ArrayList<Integer> indices = this.chooseCustomers(maxDistance);
			Ride ride2 = this.ride.getRidesCompatible().get(indices.get(0)) ;
			Ride ride3 = this.ride.getRidesCompatible().get(indices.get(1));
			this.ride.addCustomer(this.customer);		// cas ou il y a 3 rides compatibles
			this.ride.addCustomer(ride2.getCustomer());
			this.ride.addCustomer(ride3.getCustomer());	
			ArrayList<Ride> rides = new ArrayList<Ride>();
			rides.add(this.ride);
			this.ride.addCost(ride2.getCost());
			this.ride.addCost(ride3.getCost());
			rides.add(ride2);
			rides.add(ride3);
			return rides;
		}
	}
	
	/*
	 * chooseCustomers is useful for a UberPool Request when there are is at least 1 compatible trio of requests. 
	 * It loops on all compatible trios and compares the distance the driver has to drive from the first stop to the last stop.
	 * It returns the indexes of the best two requests compatible with the BookingUberpool object's request.
	 */
	public ArrayList<Integer> chooseCustomers(double maxDistance) {
		ArrayList<Integer> indices = new ArrayList<Integer>() ;
		int i = 0;
		int j = 0;
		int minIndexI = 0;
		int minIndexJ = 0;
		double minDistance = Double.POSITIVE_INFINITY;
		
		for (Ride r2 : this.ride.getRidesCompatible()) {
			for (Ride r3 : r2.getRidesCompatible()) {
				ArrayList<Ride> trio = this.ride.generateRideList(r2, r3);
				double length = GPS.calculateMinPossibleDistancePath(trio);
				
				if(length<minDistance) {
					minDistance = length;
					minIndexI = i;
					minIndexJ = j;
				}
				j++;
			}
			i++;
		}
		indices.add(minIndexI);
		indices.add(minIndexJ);
		return indices;
	}

		
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, InterruptedException {
		
		SystemBalance system = SystemBalance.getInstance();
		
		CustomerFactory cF = CustomerFactory.getInstance();
		Standard s = new Standard(new GPS(19.2,19.5), 4);
		DriverFactory df = DriverFactory.getInstance();
		Driver d = df.newDriver("A", "B");
		d.setCurrentCar(s);
		
		Customer c1 = cF.newCustomer("A", "A", new GPS(19,19));
		GPS c1Dest = new GPS(18.84, 19.25);
		
		Customer c3 = cF.newCustomer("C", "C", new GPS(18.8, 19.1));
		UberPool r3 = new UberPool(c3, c3.getGps(), new GPS(19.3, 18.8));
		Customer c2 = cF.newCustomer("B", "B", new GPS(19.5, 19.5));
		UberPool r1 = new UberPool(c1, c1.getGps(), c1Dest, 1);
		Customer c4 = cF.newCustomer("D", "D3", new GPS(18.85, 18.52));
		UberPool r4 = new UberPool(c4, c4.getGps(), new GPS(19.2, 18.65));
		
		c1.requestRide(new GPS(19.4, 18.95), new GPS(19.1, 19.08), 1, 5000);
		c1.treatRequest(2, 5000);
		System.out.print(c2.requestRide(new GPS(19.2,19), new GPS(18.94,19.3), 2, 5000));
		c2.treatRequest(2, 5000);
		
		//System.out.print("\r\n\r\n new list requests " + listRequests);
		
		System.out.print("\r\nDriver balance: " + d.getBalance());

		System.out.print("\r\n" + system.getMostChargedCustomer());
		
		System.out.print("\r\nC1: " + c1.getCustomerBalance().getTotalAmount());
		System.out.print("\r\nC2: " + c2.getCustomerBalance().getTotalAmount());
		System.out.print("\r\nC3: " + c3.getCustomerBalance().getTotalAmount());
		System.out.print("\r\nC4: " + c4.getCustomerBalance().getTotalAmount());
		
		//System.out.print("\r\n" + r3.getCost());
		
		//System.out.print("\r\n" + c2.getCustomerBalance());
	
	}
	
}



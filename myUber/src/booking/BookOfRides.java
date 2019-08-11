package booking;

import java.util.ArrayList;

import other.GPS;
import rides.*;
import balances.*;
public class BookOfRides {
	
	
	private static BookOfRides instance = new BookOfRides();
	private ArrayList<Ride> bookOfRides= new ArrayList<Ride>();

	public static BookOfRides getInstance() {
		return instance;
	}
	
	/* adds ride to the SystemBalance number of rides and adds the ride to the book of rides */
	public static void addRide(Ride r) {
		SystemBalance systemBalance = SystemBalance.getInstance();
		systemBalance.addNbRide();
		BookOfRides instance = getInstance();
		instance.bookOfRides.add(r);
	}

	@Override
	public String toString() {
		String ridesDetails = "";
		for(Ride r : this.bookOfRides) {
			ridesDetails += r.toStringCompleted();
		}
		return ridesDetails;
	}
	
	
}

package booking;

import rides.Ride;
import users.Customer;

public class ClassicBooking extends Booking {

	public ClassicBooking(Customer customer, int nbPassengers) throws InstantiationException, IllegalAccessException, InterruptedException {
		super(customer, nbPassengers);
	}
}




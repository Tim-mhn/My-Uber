package users;


import rides.Observable;
import rides.*;

public interface Observer { /* observer pattern for users: customer and drivers */
	
	public void update(Observable o, String state) throws InterruptedException;
	public void addCompletedRide(Ride r);

}

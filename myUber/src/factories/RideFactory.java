package factories;
import java.util.*;
import balances.*;
import factories.*;
import rides.*;
import users.*;
import cars.*;
import other.*;

public class RideFactory extends AbstractFactory { /* Singleton pattern in order to have only one unique ride factory */

	private static RideFactory instance = new RideFactory();
	private Ride[] rideTypes = new Ride[] {new UberBlack(), new UberPool(), new UberVan(), new UberX()};
	
	// Constructor 

	public static RideFactory getInstance() {
		return instance;
	}
	public Ride[] getRideTypes() {
		return rideTypes;
	}

	public void setRideTypes(Ride[] rideTypes) {
		this.rideTypes = rideTypes;
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
	public Driver newDriver() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ride newRide(String rideType) {
		// TODO Auto-generated method stub
		if(rideType.equalsIgnoreCase("uberblack")) {
			return new UberBlack();
		}
		if(rideType.equalsIgnoreCase("uberpool")) {
			return new UberPool();
		}
		if(rideType.equalsIgnoreCase("ubervan")) {
			return new UberVan();
		}
		if(rideType.equalsIgnoreCase("uberx")) {
			return new UberX();
		}
		else {
			System.out.print("Error choice");
			return new Ride();
		}
		
	}

}

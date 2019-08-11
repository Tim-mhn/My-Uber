package rides;
import users.*;

import java.util.ArrayList;
import java.util.Arrays;

import other.*;

public class UberX extends Ride {
	
	private final double[] basicRates = {3.3, 4.2, 1.91, 1.5};
	private final double[] trafficRates = {1, 1.1, 1.5};
	private final ArrayList<String> c = new ArrayList<String>(Arrays.asList("Standard"));

	// Constructors 
	
	public UberX() {
		super();
		super.setRideType("UberX");
		super.setCompatibleCars(c);
	}
	
	public UberX(GPS departure, GPS destination) {
		super(departure, destination);
		super.setRideType("UberX");
		super.setCompatibleCars(c);
	}
	public UberX(Customer customer, GPS departure, GPS destination) {
		super(customer, departure, destination);
		super.setRideType("UberX");
		super.setCompatibleCars(c);
	}

	@Override
	public double[] getBasicrates() {
		return this.basicRates;
	}


	public double[] getTrafficrates() {
		return this.trafficRates;
	}
	
	@Override
	public double getBasicRate(int index) {
		return this.getBasicrates()[index];
	}
	
	public double getTrafficRate(int index) {
		return this.getTrafficrates()[index];
	}
	/* @Override
	public float calculateprice() {
		// TODO Auto-generated method stub
		return 0;
	} */
	
	
}

package rides;
import java.util.ArrayList;
import java.util.Arrays;

import other.GPS;
import users.Customer;

public class UberVan extends Ride {
	
	private final double[] basicRates = {6.2, 7.7, 3.25, 2.6};
	private final double[] trafficRates = {1, 1.5, 1.8};
	private final ArrayList<String> c = new ArrayList<String>(Arrays.asList("Van"));

	// Constructors
	
	public UberVan() {
		super();
		super.setRideType("UberVan");
		super.setCompatibleCars(this.c);
	}
	
	public UberVan(Customer customer, GPS departure, GPS destination) {
		super(customer, departure, destination);
		super.setRideType("UberVan");
		super.setCompatibleCars(this.c);
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
	/*@Override
	public float calculateprice() {
		
		return 0;
	}*/
	

}

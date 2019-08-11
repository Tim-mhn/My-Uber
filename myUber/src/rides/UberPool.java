package rides;
import java.util.ArrayList;
import java.util.Arrays;
import other.GPS;
import users.Customer;

public class UberPool extends Ride {
	
	private final double[] basicRates = {2.4, 3, 1.3, 1.1};
	private final double[] trafficRates = {1, 1.1, 1.2};
	private final ArrayList<String> c = new ArrayList<String>(Arrays.asList("Standard"));
	

	public UberPool() {
		super();
		super.setRideType("UberPool");
		super.setCompatibleCars(this.c);
	}
	
	public UberPool(GPS departure, GPS destination) {
		super(departure, destination);
		super.setRideType("UberPool");
		super.setCompatibleCars(this.c);
	}
	public UberPool(Customer customer, GPS departure, GPS destination) {
		super(customer, departure, destination);
		super.setRideType("UberPool");
		super.setCompatibleCars(this.c);
	}
	public UberPool(Customer customer, GPS departure, GPS destination, int nbPassengers) {
		super(customer, departure, destination, nbPassengers);
		super.setRideType("UberPool");
		super.setCompatibleCars(this.c);
	}
	

	@Override
	public double[] getBasicrates() {
		return this.basicRates;
	}


	public double[] getTrafficrates() {
		return this.trafficRates;
	}
	
	
	public ArrayList<String> getCompatibleCars() {
		return compatibleCars;
	}

	@Override
	public double getBasicRate(int index) {
		return this.getBasicrates()[index];
	}
	
	public double getTrafficRate(int index) {
		return this.getTrafficrates()[index];
	}

}

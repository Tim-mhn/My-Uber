package priceCalculator;

import rides.Ride;

public class MediumTrafficCalculator extends TrafficCalculator implements PriceCalculator{
	
	// Constructor
	
	public MediumTrafficCalculator() {
		super(50); /* average speed in km/H */
	}
	
	
	// Methods
	

	@Override
	public double visit(Ride ride) {
		double length = ride.calculateLength();
		double trafficRate = ride.getTrafficRate(1); /* traffic rate for heavy traffic conditions */
		int lengthIndex = (int) Math.min(Math.round(length/5), 3);
		double basicRate = ride.getBasicRate(lengthIndex);
		return length*basicRate*trafficRate;
	}
	
}

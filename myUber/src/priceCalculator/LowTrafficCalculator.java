package priceCalculator;

import rides.Ride;

public class LowTrafficCalculator extends TrafficCalculator implements PriceCalculator{
	
	// constructor 
	public LowTrafficCalculator() {
		super(60); /* average speed in km/h */
	}
	

	@Override
	public double visit(Ride ride) {
		double length = ride.calculateLength();
		double trafficRate = ride.getTrafficRate(0); /* traffic rate for low traffic conditions */
		int lengthIndex = (int) Math.min(Math.round(length/5), 3);
		double basicRate = ride.getBasicRate(lengthIndex);
		return length*basicRate*trafficRate;
	}

}

package priceCalculator;

import rides.Ride;
import other.*;

public class HeavyTrafficCalculator extends TrafficCalculator implements PriceCalculator { /* Visitor Pattern, calculator visits rides of all ride types in order to calculate all prices */ 
	 
	
	public HeavyTrafficCalculator() {
		super(35); /* average speed in km/h */
	}



	@Override
	public double visit(Ride ride) {
		double length = ride.calculateLength();
		double trafficRate = ride.getTrafficRate(2); /* traffic rate for heavy traffic conditions */
		int lengthIndex = (int) Math.min(Math.round(length/5), 3);
		double basicRate = ride.getBasicRate(lengthIndex);
		return length*basicRate*trafficRate;
	}
	
	public static void main(String[] args) {
		
		HeavyTrafficCalculator c = new HeavyTrafficCalculator();
		TrafficCalculator t = (TrafficCalculator) c;
		Ride r = new Ride();
		r.setDeparture(new GPS(45.2, 48.9));
		r.setDestination(new GPS(45.3, 48.7));
		System.out.print(t.calculateDuration(r)+"\r\n");
		System.out.print(r.calculateLength());
	}

	

	

}

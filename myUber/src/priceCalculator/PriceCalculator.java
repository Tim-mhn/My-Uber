package priceCalculator;
import rides.Ride;

public interface PriceCalculator { // "Visitor" interface
	
	public double visit(Ride ride);

}

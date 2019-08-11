package rides;

import priceCalculator.PriceCalculator;

public interface Visitable {
	
	public double accept(PriceCalculator calculator);

}

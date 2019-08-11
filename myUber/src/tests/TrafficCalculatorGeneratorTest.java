package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import priceCalculator.TrafficCalculatorGenerator;

class TrafficCalculatorGeneratorTest {

	@SuppressWarnings("deprecation")
	@Test
	public final void testEstimateTrafficState() {
		int compteur = 0;
		for(int i = 0; i<100; i++) {
			if(TrafficCalculatorGenerator.estimateTrafficState(9) == "heavy") {
				compteur +=1;
			}
		}
		
		Assert.assertEquals(true, 50<compteur && compteur<98);
	}

}

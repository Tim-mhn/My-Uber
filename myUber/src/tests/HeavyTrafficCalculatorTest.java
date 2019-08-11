package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import other.*;
import priceCalculator.HeavyTrafficCalculator;
import priceCalculator.PriceCalculator;
import rides.Ride;
import rides.UberBlack;

class HeavyTrafficCalculatorTest {

	@SuppressWarnings("deprecation")
	@Test
	public final void testPriceCalculator() {
		UberBlack r = new UberBlack(new GPS(28,27.5), new GPS(29.2,28.4));
		PriceCalculator c = new HeavyTrafficCalculator();
		Assert.assertEquals(644, Math.round(c.visit(r)));
	}

}

package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import other.GPS;
import priceCalculator.LowTrafficCalculator;
import priceCalculator.PriceCalculator;
import rides.UberBlack;

class LowTrafficCalculatorTest {

	@SuppressWarnings("deprecation")
	@Test
	public final void testPriceCalculator() {
		UberBlack r = new UberBlack(new GPS(28,27.5), new GPS(29.2,28.4));
		PriceCalculator c = new LowTrafficCalculator();
		Assert.assertEquals(402, Math.round(c.visit(r)));
	}

}

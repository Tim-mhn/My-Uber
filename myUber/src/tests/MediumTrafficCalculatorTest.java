package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import other.GPS;
import priceCalculator.MediumTrafficCalculator;
import priceCalculator.PriceCalculator;
import rides.UberBlack;

class MediumTrafficCalculatorTest {

	@SuppressWarnings("deprecation")
	@Test
	public final void testPriceCalculator() {
		UberBlack r = new UberBlack(new GPS(28,27.5), new GPS(29.2,28.4));
		PriceCalculator c = new MediumTrafficCalculator();
		Assert.assertEquals(523, Math.round(c.visit(r)));
	}

}

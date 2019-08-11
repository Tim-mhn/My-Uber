package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import users.Driver;

class DriverBalanceUpdateStatesTest {

	@Test
	public final void updateDriverStateTest() {
		
		Driver d = new Driver("A", "B");
		d.updateDutyState();
		
		Assert.assertEquals(false,d.isState());
	}
	
	@Test
	public final void updateDriverRatioTest() throws InterruptedException {
		Driver d = new Driver();
		
		d.updateRideState();
		Thread.sleep(10000);
		d.updateRideState();
		Thread.sleep(5000);
		d.updateDutyState();
		
		Assert.assertEquals(true, d.getBalance().getRatio()>0.66 && d.getBalance().getRatio()<0.67);
	}

}

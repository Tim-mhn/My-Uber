package balances;

import factories.CustomerFactory;
import factories.DriverFactory;
import sorters.*;
import users.*;
public class SystemBalance extends Balance{ /* implements the singleton Pattern -> unique System Balance */

	
	/* SystemBalance is a sub-class of Balance. The attributes nbRides and totalAmount correspond here to the total number of rides
	 * and to the total amount cashed in by all the drivers
	 */
	
	private static SystemBalance instance = new SystemBalance();
	
	public static SystemBalance getInstance() {
		return instance;
	}
	
	public Customer getMostChargedCustomer() {
		CustomerFactory cF = CustomerFactory.getInstance();
		return CustomerChargeSorter.getMostChargedCustomer(cF.getCustomers());
	}
	
	public Customer getMostFrequentCustomer() {
		CustomerFactory cF = CustomerFactory.getInstance();
		return CustomerFrequencySorter.getMostFrequentCustomer(cF.getCustomers());
	}
	
	public Driver getMostOccupiedDriver() {
		DriverFactory dF = DriverFactory.getInstance();
		return DriverOccupationSorter.getMostOccupiedDriver(dF.getDrivers());
	}
	
	public Driver getMostAppreciatedDriver() {
		DriverFactory dF = DriverFactory.getInstance();
		return DriverAppreciationSorter.getMostAppreciatedDriver(dF.getDrivers());
	}
	
	
}

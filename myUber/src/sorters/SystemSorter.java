package sorters;

import java.util.ArrayList;

import factories.CustomerFactory;
import factories.DriverFactory;
import users.Observer;

public class SystemSorter {

	public static ArrayList<Observer> getSortedDriversByAppreciation() {
		DriverAppreciationSorter sorter = new DriverAppreciationSorter();
		DriverFactory factory = DriverFactory.getInstance();
		return factory.accept(sorter);
	}
	
	public static ArrayList<Observer> getSortedDriversByOccupation(){
		DriverOccupationSorter sorter = new DriverOccupationSorter();
		DriverFactory factory = DriverFactory.getInstance();
		return factory.accept(sorter);
	}
	
	public static ArrayList<Observer> getSortedCustomersByFrequency(){
		CustomerFrequencySorter sorter = new CustomerFrequencySorter();
		CustomerFactory factory = CustomerFactory.getInstance();
		return factory.accept(sorter);
	}
	
	public static ArrayList<Observer> getSortedCustomersByCharge(){
		CustomerChargeSorter sorter = new CustomerChargeSorter();
		CustomerFactory factory = CustomerFactory.getInstance();
		return factory.accept(sorter);
	}
}

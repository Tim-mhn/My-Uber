package sorters;

import java.util.ArrayList;

import balances.CustomerBalance;
import factories.*;
import factories.VisitableFactory;
import users.*;


public class CustomerFrequencySorter extends CustomerSorter{

	public CustomerFrequencySorter() {
	}
	
	public static Customer getMostFrequentCustomer(ArrayList<Customer> customers) {
		Customer bestCustomer = customers.get(0);
		double bestNb = bestCustomer.getNbRides();

		for(int i = 1; i<customers.size(); i++) {
			Customer c = customers.get(i);
			if(bestNb<c.getNbRides()) {
				bestNb = c.getNbRides();
				bestCustomer = c;
			}
		}
		return(bestCustomer);
	}
	
	@Override 
	public ArrayList<Observer> visit(VisitableFactory factory){ 
		CustomerFactory customerFactory = (CustomerFactory) factory;
		ArrayList<Customer> customers = customerFactory.getCustomers();
		ArrayList<Customer> clone = (ArrayList<Customer>) customers.clone();
		ArrayList<Customer> clonedCustomers = clone;
		ArrayList<Observer> sortedCustomers = new ArrayList<Observer>();
		
		while(clonedCustomers.size()>1) {
			Customer bestCustomer = getMostFrequentCustomer(clonedCustomers);
			sortedCustomers.add((Observer) bestCustomer);
			clonedCustomers.remove(bestCustomer);
		}
		
		sortedCustomers.add(clonedCustomers.get(0));
		return sortedCustomers;
		
	}
	
	public static void main(String[] args) {
		
		CustomerFactory factory = CustomerFactory.getInstance();
		
		Customer c1 = factory.newCustomer("A", "B");
		Customer c2 = factory.newCustomer("B", "B");
		Customer c3 = factory.newCustomer("C", "C");
		
		CustomerBalance b1 = new CustomerBalance();
		b1.setNbRides(10);

		
		CustomerBalance b2 = new CustomerBalance();
		b2.setNbRides(20);
		CustomerBalance b3 = new CustomerBalance();
		b3.setNbRides(2);
		
		c1.setCustomerBalance(b1);
		c2.setCustomerBalance(b2);
		c3.setCustomerBalance(b3);
		
		System.out.print(SystemSorter.getSortedCustomersByFrequency());
		
		
	}
}

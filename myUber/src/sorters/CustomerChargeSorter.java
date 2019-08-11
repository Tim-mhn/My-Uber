package sorters;

import java.util.ArrayList;

import balances.CustomerBalance;
import factories.CustomerFactory;
import factories.VisitableFactory;
import users.Customer;
import users.Observer;

public class CustomerChargeSorter extends CustomerSorter{
	
	public CustomerChargeSorter() {
	}
	
	public static Customer getMostChargedCustomer(ArrayList<Customer> customers) {
		Customer bestCustomer = customers.get(0);
		double highestCharge = bestCustomer.getCharge();

		for(int i = 1; i<customers.size(); i++) {
			Customer c = customers.get(i);
			if(highestCharge<c.getCharge()) {
				highestCharge = c.getCharge();
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
			Customer bestCustomer = getMostChargedCustomer(clonedCustomers);
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
		b1.setTotalAmount(550);
		CustomerBalance b2 = new CustomerBalance();
		b2.setTotalAmount(410);
		CustomerBalance b3 = new CustomerBalance();
		b3.setTotalAmount(1400);
		
		c1.setCustomerBalance(b1);
		c2.setCustomerBalance(b2);
		c3.setCustomerBalance(b3);
		
		System.out.print(SystemSorter.getSortedCustomersByCharge());
		
		
	}

}

package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import sorters.CustomerFrequencySorter;
import users.*;
import java.util.ArrayList;

class CustomerFrequencySorterTest {

	@Test
	public final void getMostFrequentCustomerTest() {
		Customer c1 = new Customer("A","A");
		Customer c2 = new Customer("A","A");
		Customer c3 = new Customer("A","A");
		c1.getCustomerBalance().setNbRides(55);
		c2.getCustomerBalance().setNbRides(45);
		c3.getCustomerBalance().setNbRides(52);
		ArrayList<Customer> customers = new ArrayList<Customer>();
		customers.add(c1);
		customers.add(c2);
		customers.add(c3);
		Assert.assertEquals(c1, CustomerFrequencySorter.getMostFrequentCustomer(customers));
		
	}

}

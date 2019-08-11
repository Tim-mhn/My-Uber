package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import sorters.CustomerChargeSorter;
import users.Customer;

class CustomerChargeSorterTest {

	@SuppressWarnings("deprecation")
	@Test
	public final void getMostChargedCustomerTest() {
		Customer c1 = new Customer("A","A");
		Customer c2 = new Customer("A","A");
		Customer c3 = new Customer("A","A");
		c1.getCustomerBalance().setTotalAmount(550);
		c2.getCustomerBalance().setTotalAmount(400);
		c3.getCustomerBalance().setTotalAmount(680);
		ArrayList<Customer> customers = new ArrayList<Customer>();
		customers.add(c1);
		customers.add(c2);
		customers.add(c3);
		Assert.assertEquals(c3, CustomerChargeSorter.getMostChargedCustomer(customers));
	}

}

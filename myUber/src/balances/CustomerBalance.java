package balances;

import other.Time;
import java.util.*;
import factories.*;
import rides.*;
import users.*;
import cars.*;

public class CustomerBalance extends Balance{
	
	private Time totalTime = new Time();

	public CustomerBalance() {
		super();
	}
	
	public Time getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Time totalTime) {
		this.totalTime = totalTime;
	}
	
	public void addTime(Time time) { // adds the ride time to total ride time 
		this.totalTime = this.totalTime.getTimeSum(time);
	}
	
	public void addCustomerRide(Ride r, Customer c) {
		
		int index = c.getCustomerIndex(r);
		this.addTime(r.getDuration());
		this.addNbRide();
		this.addAmount(r.getCost(index));
		
	}
	
	
	@Override
	public String toString() {
		return "Balance: \r\n-- Number of rides: " + super.getNbRides() +"\r\n-- Total charges: " + super.getTotalAmount() 
		+"\r\n-- Total time: " + this.getTotalTime().toString();
	}
	
	public static void main(String[] args) {
		
		CustomerBalance b = new CustomerBalance();
		System.out.print(b.toString());
	}

}

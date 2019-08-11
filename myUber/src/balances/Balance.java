package balances;

import java.util.*;
import balances.*;
import factories.*;
import rides.*;
import users.*;
import cars.*;

public class Balance {
	
	private int nbRides = 0;
	private double totalAmount = 0; /* correspond � l'argent d�pens� pour le client / argent gagn� pour le driver / argent total r�cup�r� pour le syst�me */
	
	
	public Balance() {
	}
	
	public int getNbRides() {
		return nbRides;
	}
	public void setNbRides(int nbRides) {
		this.nbRides = nbRides;
	}
	
	public void addNbRide() {
		this.nbRides = this.nbRides + 1;
	}
	
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public void addAmount(double amount) {
		this.totalAmount += amount;
	}

	@Override
	public String toString() {
		return "Balance [nbRides=" + nbRides + ", totalAmount=" + totalAmount + "]";
	}

	
}

package cars;
import java.util.*;
import balances.*;
import factories.*;
import rides.*;
import users.*;
import cars.*;
import other.GPS;

public class Standard extends Car {

	private static int compteur = 0;
	
	public Standard (int nbPassengers, GPS gps, ArrayList<Driver> drivers) {
		super(gps, nbPassengers, drivers);
		compteur += 1;
		String id = "S" + Integer.toString(compteur);
		super.ID = id;
		super.carType = "Standard";
	}
	
	public Standard(GPS gps, int nbPassengers) {
		super(gps, nbPassengers);
		compteur += 1;
		String id = "S" + Integer.toString(compteur);
		super.ID = id;
		super.carType = "Standard";
		
	}


	
	public static int getCompteur() {
		return compteur;
	}

	public void rent() {
		
		System.out.println("renting a car") ;
		
	}
	
}


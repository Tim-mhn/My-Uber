package cars;
import java.util.*;
import balances.*;
import factories.*;
import rides.*;
import users.*;
import other.GPS;

public class Van extends Car{

	
	private static int compteur = 0;
	
	
	public Van (int nbPassengers, GPS gps, ArrayList<Driver> drivers) {
		super(gps, nbPassengers, drivers);
		compteur += 1;
		String id = "V" + Integer.toString(compteur);
		super.ID = id;
		super.carType = "Van";
	}
	
	public Van(GPS gps, int nbPassengers) {
		super(gps, nbPassengers);
		compteur += 1;
		String id = "V" + Integer.toString(compteur);
		super.ID = id;
		super.carType = "Van";
		
	}


	
	public static int getCompteur() {
		return compteur;
	}

	public void rent() {
		
		System.out.println("renting a car") ;
		
	}
}

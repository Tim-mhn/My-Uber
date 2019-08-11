package cars;
import java.util.*;
import balances.*;
import factories.*;
import rides.*;
import users.*;
import cars.*;
import other.GPS;

public class Berline extends Car {   // il n'y a pas encore de main
	
	private static int compteur = 0; /* permet d'obtenir un id unique pour les voitures de ce type */
	
	
	public Berline (int nbPassengers, GPS gps, ArrayList<Driver> drivers) {
		super(gps, nbPassengers, drivers);
		compteur += 1;
		String id = "B" + Integer.toString(compteur);
		super.ID = id;
		super.carType = "Berline";
	}
	
	public Berline(GPS gps, int nbPassengers) {
		super(gps, nbPassengers);
		compteur += 1;
		String id = "B" + Integer.toString(compteur);
		super.ID = id;
		super.carType = "Berline";
	}
	
	
	
	public static int getCompteur() {
		return compteur;
	}

	public static void setCompteur(int compteur) {
		Berline.compteur = compteur;
	}

	public void rent() {
		
		System.out.println("renting a car") ;
		
	}

	

	
	

}

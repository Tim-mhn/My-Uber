package rides;

import balances.*;
import java.util.ArrayList;
import java.util.Arrays;
import cars.*;
import factories.*;
import other.*;
import users.*;

public class UberBlack extends Ride {

	private final double[] basicRates = {6.2, 5.5, 3.25, 2.6};
	private final double[] trafficRates = {1, 1.3, 1.6};
	private final ArrayList<String> c = new ArrayList<String>(Arrays.asList("Berline"));
	
	
	public UberBlack() {
		super();
		super.setRideType("UberBlack");
		super.setCompatibleCars(this.c);
	}
	public UberBlack(Customer customer, GPS departure, GPS destination) {
		super(customer, departure, destination);
		super.setRideType("UberBlack");
		super.setCompatibleCars(this.c);
	}
	
	public UberBlack(GPS departure, GPS destination) {
		super(departure, destination);
		super.setRideType("UberBlack");
		super.setCompatibleCars(this.c);
		
	}
	
	public double[] getBasicrates() {
		return this.basicRates;
	}

	public double[] getTrafficrates() {
		return this.trafficRates;
	}
	

	public double getBasicRate(int index) {
		return this.getBasicrates()[index];
	}
	
	public double getTrafficRate(int index) {
		return this.getTrafficrates()[index];
	}
	
	/* @Override
	public float calculateprice() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float calculatelength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public function convertRad() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public function Distance() {
		// TODO Auto-generated method stub
		return null;
	} */
	
	public static void main(String[] args) {
		
		Ride blackR = new UberBlack();
		System.out.print(blackR.getBasicRate(1));
		
	}

}

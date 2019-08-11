package priceCalculator;

import other.Time;

public class TrafficCalculatorGenerator {
	public String trafficState ;
	public static double[][] tableTimeTraffic = {{5,20,75},{15,70,15},{1,4,95},{95,4,1}};;
	public static double[] tableTime = {7,11,17,22};
	public static String[] tableTraffic = {"low","medium","heavy"};
	
	public String getTrafficState() {
		return trafficState ;
	}
	
	public void setTrafficState(String s) {
		this.trafficState = s ;
	}
	
	public static void setTableTimeTraffic(double[][] A) {
		tableTimeTraffic = A ;
	}
	
	public double[][] getTableTimeTraffic() {
		return tableTimeTraffic ;
	}
	
	public void setTableTime(double[] Hours) {
		tableTime = Hours ;
	}
	
	public double[] getTableTime() {
		return tableTime ;
	}
	
	public static void setTableTraffic(String[] TypeTraffic) {
		tableTraffic = TypeTraffic ;
	}
	
	public String[] getTableTraffic() {
		return tableTraffic ;
	}
	
	public static String estimateTrafficState(Time time) {
		double hours = (double) time.getHours();
		return estimateTrafficState(hours);
	}
	public static String estimateTrafficState(double hour) {
		int i = 0 ;
		int j = 0 ;
		double compteur = 0 ; 
		while (i < TrafficCalculatorGenerator.tableTime.length && hour >= TrafficCalculatorGenerator.tableTime[i]) {
			 i++ ;
		}
		if (i==0) {
			i = TrafficCalculatorGenerator.tableTime.length ;
		}
		double X = Math.random() ;
		compteur = TrafficCalculatorGenerator.tableTimeTraffic[i-1][0] ;
		while (j < TrafficCalculatorGenerator.tableTraffic.length && compteur < X*100) {			 
			 j++;
			 compteur = compteur + TrafficCalculatorGenerator.tableTimeTraffic[i-1][j] ;
		 }
		if (j==0) {
			return TrafficCalculatorGenerator.tableTraffic[j] ;
		}
		else {
			return TrafficCalculatorGenerator.tableTraffic[j] ;
		}
		
	}
	
	/* generate price calculator according to the traffic condition */
	public static PriceCalculator getTrafficCalculator(String trafficCondition) {
		if(trafficCondition.equalsIgnoreCase("medium")) {
			return new MediumTrafficCalculator();
		}
		else if (trafficCondition.equalsIgnoreCase("heavy")) {
			return new HeavyTrafficCalculator();
		}
		else if (trafficCondition.equalsIgnoreCase("low")) {
			return new LowTrafficCalculator();
		}
		else {
			return null;
		}
	}
	
	// Generate price calculator according to the reservation time 
	/* uses the static estimateTrafficState method to get the traffic condition and apply the getTrafficCalculator(trafficCondition) method */
	
	public static PriceCalculator getTrafficCalculator(Time time) {
		double hours = (double) time.getHours();
		String trafficCondition = estimateTrafficState(hours);
		return getTrafficCalculator(trafficCondition);
	}
	
	public static String estimateCurrentTraffic() {
		Time time = Time.getLocalTime();
		double hours = time.getHours();
		return estimateTrafficState(hours);
	}
	
	// Generate according to current time the right price calculator 
	public static PriceCalculator generateCurrentTrafficCalculator() {
		String trafficState = estimateCurrentTraffic();
		return getTrafficCalculator(trafficState);
	}

	public static void main (String[] args) {

		for(int i = 0; i<15; i++) {
			System.out.println(estimateTrafficState(9));
		}
		
	}
}

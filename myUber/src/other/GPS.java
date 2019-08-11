package other;
import java.util.*;

import rides.Ride;
import users.Driver;

public class GPS {

	private double longitude;
	private double latitude;
	
	
	public GPS(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public GPS() {
		this.longitude = 0;
		this.latitude = 0;
	}

	// Getters and setters
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	// Methods to convert degrees + calculate length
	 
	@Override
	public String toString() {
		return "GPS [longitude=" + longitude + ", latitude=" + latitude + "]";
	}

	public double calculateDistance(GPS gps2){ /* method to calculate distance between 2 GPS positions in km */
	     
	    double R = 6378; //Rayon de la terre en km
	 
	    double lat = Math.toRadians(this.getLatitude()); /* conversion des latitudes / longitudes en radians */
	    double lon = Math.toRadians(this.getLongitude());
	    double lat2 = Math.toRadians(gps2.getLatitude());
	    double lon2 = Math.toRadians(gps2.getLongitude());
	     
	    double d = R * Math.acos(Math.sin(lat)*Math.sin(lat2) + Math.cos(lat)*Math.cos(lat2)*Math.cos(lon-lon2));
	    return d;
	}
	
	// Get center between two GPS positions
	
	public GPS getCenter(GPS gps2) {
		double lat2 = gps2.getLatitude();
		double lon2 = gps2.getLongitude();
		
		return new GPS((this.latitude + lat2)/2, (this.longitude + lon2)/2);
	}
	
	// Method which returns the closest GPS position to the instance of GPS from a list of different GPS positions 
	public GPS getClosestPosition(ArrayList<GPS> positions) {
		
		if(positions.size() == 0) { // condition which solves a bug when there is an excedent getClosestPosition loop with position ={}
			return this;
		}
		else {
			double distance = this.calculateDistance(positions.get(0));
			GPS closestPosition = positions.get(0);
			
			for(GPS position : positions) {
				if(distance>this.calculateDistance(position)) {
					closestPosition = position;
					distance = this.calculateDistance(position);
				}
			}
			
			return closestPosition;
		}
		
	}
	
	// Computes the path according to the driver's current position and the list of departures / destinations of customers for UberPool 
	public static ArrayList<GPS> computePath(Driver d, ArrayList<GPS> departures, ArrayList<GPS> destinations){
		
		int n = departures.size();
		GPS firstStop = d.getCurrentCar().getGps().getClosestPosition(departures);
		int index = departures.lastIndexOf(firstStop);
		
		ArrayList<GPS> driverPath = new ArrayList<GPS>();
		driverPath.add(d.getCurrentCar().getGps());
		
		ArrayList<GPS> path = computePath2(departures, destinations, index);
		
		for(GPS stop : path) {
			driverPath.add(stop);
		}
		
		return driverPath;
		
		
	}
	
	// Path computer with customers' departure and destinations. OptionNumber corresponds to the number of the starting point among the departures 
	@SuppressWarnings("unlikely-arg-type")
	public static ArrayList<GPS> computePath2(ArrayList<GPS> departures, ArrayList<GPS> destinations, int optionNum){
		
		int compteur = 0;
		ArrayList<GPS> path = new ArrayList<GPS>();
		int n = departures.size();
		boolean[] depIndex = new boolean[n];
		Arrays.fill(depIndex, false);
		boolean[] destIndex = new boolean[n];
		Arrays.fill(destIndex, false);
		GPS stop = departures.get(optionNum);
		path.add(stop);
		depIndex[optionNum] = true;
		ArrayList<GPS> possibleStops = new ArrayList<GPS>();
		
		while(compteur<2*n-1) {
			possibleStops = possibleStops(departures,destinations, depIndex, destIndex);
			stop = stop.getClosestPosition(possibleStops);
			path.add(stop);
			compteur += 1;
			for(int i = 0; i<n; i++) {
				if(departures.get(i).equals(stop)) {
					depIndex[i] = true;
				}
				if(destinations.get(i).equals(stop)) {
					destIndex[i] = true;
				}
			}
			
		}
		return path;
	}
	
	public static ArrayList<GPS> computeBestPath(ArrayList<GPS> departures, ArrayList<GPS> destinations){
		int n = departures.size();
		ArrayList<GPS> path = computePath2(departures, destinations, 0);
		double distance = calculatePathTotalDistance(path);
		if(n>1) {
			for(int i = 1; i<n; i++) {
				ArrayList<GPS> new_path = computePath2(departures,destinations, i);
				double new_distance = calculatePathTotalDistance(new_path);
				if(distance>new_distance) {
					path = new_path;
					distance = new_distance;
				}
			}
		}
		
		return path;
	}
	
	public static ArrayList<GPS> possibleStops(ArrayList<GPS> departures, ArrayList<GPS> destinations, boolean[] depIndex, boolean[] destIndex){
		ArrayList<GPS> stops = new ArrayList<GPS>();
		for(int i = 0; i<departures.size(); i++) {
			if(!depIndex[i]) {
				stops.add(departures.get(i));
			}
			if(depIndex[i] && !destIndex[i]) {
				stops.add(destinations.get(i));
			}
		}
		
		return stops;
	}
	
	// Calculates a path total distance -> useful to choose best path in UberPool booking
	public static double calculatePathTotalDistance(ArrayList<GPS> path) {
		double distance = 0;
		for(int i = 0; i<path.size()-1; i++) {
			distance += path.get(i).calculateDistance(path.get(i+1));
		}
		return distance;
	}
	
	// Calculates the minimum distance to satisfy the rides' departure and destination. Will be used by UberPool booking to group request
	public static double calculateMinPossibleDistancePath(ArrayList<Ride> rides) {
		
		ArrayList<GPS> departures = Ride.getDepartures(rides);
		ArrayList<GPS> destinations = Ride.getDestinations(rides);
		
		ArrayList<GPS> path = computeBestPath(departures, destinations);
		double distance = calculatePathTotalDistance(path);
		
		return distance;
	}
	
	public static double getRandomCoordinate(double min, double max) {
		return min +(max-min)*Math.random();
	}
	public static GPS getRandomGPS(double minLongitude, double maxLongitude, double minLatitude, double maxLatitude) {
		double longitude = minLongitude + (maxLongitude - minLongitude)*Math.random();
		double latitude = minLatitude + (maxLatitude - minLatitude)*Math.random();
		return new GPS(longitude, latitude);
	}
	

	public static void main(String[] args) {
		/*double length = new GPS(50,55).calculateDistance(new GPS(40,45));
		System.out.print(length +"\r\n");
		System.out.print(Math.min(Math.round(length/5), 3));*/
		
		// Test possibleStops method
		ArrayList<GPS> stops = new ArrayList<GPS>();
		GPS d1 = new GPS(20,35);
		GPS d2 = new GPS(25,32);
		GPS d3 = new GPS(35,32);
		
		ArrayList<GPS> departures = new ArrayList<GPS>();
		departures.add(d1);
		departures.add(d2);
		departures.add(d3);
		stops.add(d1);
		stops.add(d2);
		stops.add(d3);
		
		GPS a1 = new GPS(22,38);
		GPS a2 = new GPS(19,35);
		GPS a3 = new GPS(38,35);
		ArrayList<GPS> arrivals = new ArrayList<GPS>();
		arrivals.add(a1);
		stops.add(a1);
		stops.add(a2);
		stops.add(a3);
		arrivals.add(a2);
		arrivals.add(a3);
		
		Ride r1 = new Ride(d1, a1);
		Ride r2 = new Ride(d2, a2);
		Ride r3 = new Ride(d3, a3);
		
		ArrayList<Ride> rides = new ArrayList<Ride>();
		rides.add(r1);
		rides.add(r2);
		/*System.out.print(possibleStops(departures,arrivals));*/
		/*System.out.print(possibleStops(departures, arrivals));*/
		System.out.print(" -- DEPARTURES -- " + departures +"\r\n\r\n");
		System.out.print(" -- ARRIVALS -- " + arrivals + "\r\n\r\n");
		System.out.print("Min Distance === " + Math.round(calculateMinPossibleDistancePath(rides)) + "km");
	}
	
}

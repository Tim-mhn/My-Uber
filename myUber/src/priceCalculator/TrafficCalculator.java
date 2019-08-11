package priceCalculator;

import other.Time;
import rides.Ride;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import other.GPS;

public class TrafficCalculator {
	
	private double avgSpeed;

	public TrafficCalculator() {
		this.avgSpeed = 0;
	}
	
	public TrafficCalculator(double avgSpeed) {
		super();
		this.avgSpeed = avgSpeed;
	}
	
	public double calculateDuration(Ride r) {
		GPS departure = r.getDeparture();
		GPS destination = r.getDestination();
		double distance = departure.calculateDistance(destination);
		double hours = distance/avgSpeed;
		return hours;
	}
	
	public double calculateDuration(GPS departure, GPS destination) {
		double distance = departure.calculateDistance(destination);
		double hours = distance/avgSpeed;
		return hours;
	}
	
	public static void main(String[] args) {
		
		GPS departure = new GPS(8.2,14.5);
		GPS destination = new GPS(8.5, 14.3);
		Ride r = new Ride(departure, destination);
		TrafficCalculator c = new TrafficCalculator(45);
		System.out.print(c.calculateDuration(r));
	}
	

}

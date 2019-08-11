package scanners;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerMethods {
	
	public static double scanDouble() {
		
		Scanner scan = new Scanner(System.in);
		double number = 0;
		
		boolean error = true;
		while(error) {
			try {
				
				number = scan.nextDouble();
				error = false;
			} catch (InputMismatchException e) {
				String badInput = scan.next();
				System.out.print("Please enter a number:\r\n");
				continue;
			}
		}
		
		return number;
	}
	
	public static double scanDouble(double min, double max) {
		double num = scanDouble();
		while(num<min || num>max) {
			System.out.print("Error. Choose between " + min + " and " + max);
			num = scanDouble();
		}
		return num;
	}
	
	public static int scanInt() {

		int number = 0;
		Scanner scan = new Scanner(System.in);
		
		boolean error = true;
		while(error) {
			try {
				number = scan.nextInt();
				error = false;
			} catch (InputMismatchException e) {
				System.out.print("Please enter an integer:\r\n");
				String badInput = scan.next();
				continue;
			}
		}
		
		return number;
	}
	
	public static int scanInt(int min, int max) {
		int num = scanInt();
		while(num<min || num>max) {
			System.out.print("Error. Choose between " + min + " and " + max);
			num = scanInt();
		}
		return num;
	}
	
	
	public static void main(String[] args) {
		//scanDouble();
		//scanInt();
		scanInt(1,5);
	}
}



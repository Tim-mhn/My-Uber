package myUber;

import java.util.*;

import cars.Car;
import factories.CarFactory;
import factories.CustomerFactory;
import factories.DriverFactory;
import factories.RideFactory;
import other.GPS;
import users.Customer;
import users.Driver;
import sorters.*;
import balances.*;
import scanners.ScannerMethods;
public class MyUberCLUI {
	
	public static Customer getCustomerWithId() {
		CustomerFactory customerF = CustomerFactory.getInstance();
		Scanner scan = new Scanner(System.in);
		System.out.print("Customer ID: \r\n");
		int id = ScannerMethods.scanInt();
		
		boolean customerExists = customerF.customerExists(id);
		boolean wishToCreateNewCustomer = false; /* a complter */
		
		while(!customerExists && !wishToCreateNewCustomer) {
			System.out.print("Customer cannot be found.\r\nPlease choose between: " + customerF.getCustomers() + "\r\n");
			id = ScannerMethods.scanInt();
			customerExists = customerF.customerExists(id);
		}
		
		Customer customer = customerF.getCustomer(id);
		return customer;
	}
	
	public static int getRideType() {
		Scanner scan = new Scanner(System.in);
		String rideType = "";
		while(!(rideType.equalsIgnoreCase("uberpool")) && !(rideType.equalsIgnoreCase("uberx")) && !(rideType.equalsIgnoreCase("ubervan")) && !(rideType.equalsIgnoreCase("uberblack"))) {
			System.out.print("Choose between:\r\n- UberBlack\r\n- UberPool\r\n- UberVan\r\n- UberX\r\n");
			rideType = scan.next();
		}
		if(rideType.equalsIgnoreCase("uberpool")) {
			return 0;
		}
		else if(rideType.equalsIgnoreCase("uberblack")) {
			return 1;
		}
		else if(rideType.equalsIgnoreCase("ubervan")) {
			return 2;
		}
		else {
			return 3;
		}
	}
	
	public static void setup(int standardCarsNb, int berlineCarsNb, int vanCarsNb, int customersNb) {
		CustomerFactory custF = CustomerFactory.getInstance();
		CarFactory cF = CarFactory.getInstance();
		DriverFactory dF = DriverFactory.getInstance();
		
		for(int i = 0; i<standardCarsNb; i++) {
			Car c = cF.newCar("standard", 4);
			Driver d = dF.newDriver("driver"+(i+1)+"name", "driver"+(i+1)+"surname",c);
		}
		for(int i = 0; i<vanCarsNb; i++) {
			Car c = cF.newCar("van", 7);
			Driver d = dF.newDriver("driver"+(standardCarsNb+i+1)+"name", "driver"+(standardCarsNb+i+1)+"surname",c);
		}
		for(int i = 0; i<berlineCarsNb; i++) {
			Car c = cF.newCar("berline", 4);
			Driver d = dF.newDriver("driver"+(standardCarsNb+vanCarsNb+i+1)+"name", "driver"+(standardCarsNb+vanCarsNb+i+1)+"surname",c);
		}
		for(int i = 0; i<customersNb; i++) {
			GPS position = GPS.getRandomGPS(10, 11, 10, 11);
			Customer c = custF.newCustomer("customer" + (i+1) + "name", "customer" + (i+1) + "surname", position);
		}
	}
	public static void setup() {
		CarFactory cF = CarFactory.getInstance();
		DriverFactory dF = DriverFactory.getInstance();
		CustomerFactory custF = CustomerFactory.getInstance();
		Scanner scan = new Scanner(System.in);
		System.out.print("Number of standard cars:\r\n");
		int standardCarsNum = ScannerMethods.scanInt();
		System.out.print("Number of van cars:\r\n");
		int vanCarsNum = ScannerMethods.scanInt();
		System.out.print("Number of berline cars:\r\n");
		int berlineCarsNum = ScannerMethods.scanInt();
		System.out.print("Number of customers:\r\n");
		int customersNum = ScannerMethods.scanInt();
		
		for(int i = 0; i<standardCarsNum; i++) {
			Car c = cF.newCar("standard", 4);
			Driver d = dF.newDriver("driver"+(i+1)+"name", "driver"+(i+1)+"surname",c);
		}
		for(int i = 0; i<vanCarsNum; i++) {
			Car c = cF.newCar("van", 7);
			Driver d = dF.newDriver("driver"+(standardCarsNum+i+1)+"name", "driver"+(standardCarsNum+i+1)+"surname",c);
		}
		for(int i = 0; i<berlineCarsNum; i++) {
			Car c = cF.newCar("berline", 4);
			Driver d = dF.newDriver("driver"+(standardCarsNum+vanCarsNum+i+1)+"name", "driver"+(standardCarsNum+vanCarsNum+i+1)+"surname",c);
		}
		for(int i = 0; i<customersNum; i++) {
			GPS position = GPS.getRandomGPS(10, 11, 10, 11);
			Customer c = custF.newCustomer("customer" + (i+1) + "name", "customer" + (i+1) + "surname", position);
		}
		
		
	}
	public static void displayState() {
		CarFactory carF = CarFactory.getInstance();
		DriverFactory driverF = DriverFactory.getInstance();
		CustomerFactory customerF = CustomerFactory.getInstance();
		ArrayList<Car> cars = carF.getCars();
		ArrayList<Driver> drivers = driverF.getDrivers();
		ArrayList<Customer> customers = customerF.getCustomers();
		System.out.print("\r\n-- State System -- \r\n\r\n-- Cars -- \r\n");
		System.out.print("Total number: " + cars.size()+"\r\n");
		System.out.print("Standard: " + carF.getStandards().size()+"\r\n");
		System.out.print("Berline: " + carF.getBerlines().size()+"\r\n");
		System.out.print("Van: " + carF.getVans().size()+"\r\n\r\nList of cars: \r\n");
		for(Car c : cars) {
			System.out.print(c.toString()+"\r\n");
		}
		System.out.print("\r\n\r\n-- Drivers --\r\nTotal number: " + drivers.size() + "\r\n\r\nList of drivers: \r\n");
		for(Driver d : drivers) {
			System.out.print(d.toString() + "\r\n\r\n");
		}
		System.out.print("\r\n-- Customers -- \r\nTotal number: " + customers.size() + "\r\n\r\nList of customers: \r\n");
		for(Customer c : customers) {
			System.out.print(c.toString() + "\r\n");
		}
	}
	
	public static void displayDrivers() {
		Scanner scan = new Scanner(System.in);
		String sortPolicy = "";
		ArrayList<users.Observer> sortedDriverList = new ArrayList<users.Observer>();
		while(!(sortPolicy.equalsIgnoreCase("most_appreciated")) && !(sortPolicy.equalsIgnoreCase("most_occupied"))){
			System.out.print("Choose a sort policy.\r\n- most_appreciated\r\n- most_occupied\r\n");
			sortPolicy = scan.next();
		}
		if(sortPolicy.equalsIgnoreCase("most_appreciated")) {
			sortedDriverList = SystemSorter.getSortedDriversByAppreciation();
		}
		else {
			sortedDriverList = SystemSorter.getSortedDriversByOccupation();
		}
		for(int i = 0; i<sortedDriverList.size(); i++) {
			System.out.print("N°" + (i+1) + ": " + sortedDriverList.get(i).toString() + "\r\n\r\n");
		}
	}
	
	public static void displayCustomers() {
		Scanner scan = new Scanner(System.in);
		String sortPolicy = "";
		while(!(sortPolicy.equalsIgnoreCase("most_charged")) && !(sortPolicy.equalsIgnoreCase("most_frequent"))){
			System.out.print("Choose a sort policy.\r\n- most_frequent\r\n- most_charged\r\n");
			sortPolicy = scan.next();
		}
		if(sortPolicy.equalsIgnoreCase("most_frequent")) {
			System.out.print(SystemSorter.getSortedCustomersByFrequency());
		}
		else {
			System.out.print(SystemSorter.getSortedCustomersByCharge());
		}
	}
	
	public static void displayTotalCashed() {
		System.out.print("Total amount cashed by drivers: " + SystemBalance.getInstance().getTotalAmount() + "€");
	}
	
	public static void main(String[] args) {
		
		myUber.setMode("CLUI");
		CustomerFactory customerF = CustomerFactory.getInstance();
		DriverFactory driverF = DriverFactory.getInstance();
		CarFactory carF = CarFactory.getInstance();
		RideFactory rideF = RideFactory.getInstance();
		
		Scanner scan = new Scanner(System.in);
		
		/*customerF.newCustomer("A", "A", new GPS(45.2, 68.52));
		customerF.newCustomer("B", "B", new GPS(45.205, 68.495));
		customerF.newCustomer("C", "C", new GPS(45.04, 68.519));
		customerF.newCustomer("D", "D", new GPS(45.209, 68.5204));*/
		
		
		String nextCommand = "";
		
		while(!nextCommand.equalsIgnoreCase("end")) {
			
			System.out.print("\r\nnext command:");
			
			nextCommand = scan.next();
			
			if(nextCommand.equalsIgnoreCase("setup")) {
				setup();
			}
			if(nextCommand.equalsIgnoreCase("addCustomer")) {
				
				System.out.print("Customer name:\r\n");
				String name = scan.next();
				System.out.print("Customer surname:\r\n");
				String surname = scan.next();
				customerF.newCustomer(name, surname);
				System.out.print(customerF.displayCustomers() + "\r\n");
			}
			
			if(nextCommand.equalsIgnoreCase("addCarDriver")) {
				System.out.print("Driver name:\r\n");
				String name = scan.next();
				System.out.print("Driver surname:\r\n");
				String surname = scan.next();
				
				System.out.print("Car-type:\r\n");
				String carType = scan.next();
				
				while(!carType.equalsIgnoreCase("standard") && !carType.equalsIgnoreCase("berline") && !carType.equalsIgnoreCase("van")) {
					System.out.print("Wrong input.\r\nChoose between Standard, Berline or Van. \r\n");
					carType = scan.next();
				}
				
				System.out.print("Number of passengers: \r\n");
				int nbPassengers = ScannerMethods.scanInt(3,9);
				
				carF.newCar(carType, nbPassengers, 0, 0);
				driverF.newDriver(name, surname, carF.getLastCar());
				System.out.print("List of cars: \r\n" + carF.getCars()+"\r\n");
				System.out.print("\r\nList of drivers: \r\n" + driverF.getDrivers()+"\r\n");
			}
			
			if(nextCommand.equalsIgnoreCase("addDriver")) {
				System.out.print("Driver name: \r\n");
				String name = scan.next();
				System.out.print("Driver surname: \r\n");
				String surname = scan.next();
				System.out.print("Car ID: \r\n");
				String id = scan.next();
				
				boolean carExists = carF.carExist(id);
				
				while(!carExists) {
					System.out.print("Car cannot be found.\r\nPlease choose between: " + carF.getCars() + "\r\n");
					id = scan.next();
					carExists = carF.carExist(id);
				}
				
				Car c = carF.getCar(id);
				if(c.isAvailable()) {
					driverF.newDriver(name, surname,c);
					System.out.print("List of cars: \r\n" + carF.getCars()+"\r\n");
					System.out.print("\r\nList of drivers: \r\n" + driverF.getDrivers()+"\r\n");
				}
				else {
					driverF.newDriver(name, surname);
					System.out.print("List of cars: \r\n" + carF.getCars()+"\r\n");
					System.out.print("\r\nList of drivers: \r\n" + driverF.getDrivers()+"\r\n");
				}
				
			}
			
			if(nextCommand.equalsIgnoreCase("addCar")) {
				
				System.out.print("Car-type:\r\n");
				String carType = scan.next();
				
				while(!carType.equalsIgnoreCase("standard") && !carType.equalsIgnoreCase("berline") && !carType.equalsIgnoreCase("van")) {
					System.out.print("Wrong input.\r\nChoose between Standard, Berline or Van. \r\n");
					carType = scan.next();
				}
				
				System.out.print("Number of passengers: \r\n");
				int nbPassengers = ScannerMethods.scanInt();
				
				carF.newCar(carType, nbPassengers, GPS.getRandomCoordinate(10, 11), GPS.getRandomCoordinate(10, 11));
				System.out.print("List of cars: " + carF.getCars()+"\r\n");
			}
			
			if(nextCommand.equalsIgnoreCase("setDriverStatus")) {
				System.out.print(">Driver name: \r\n");
				String name = scan.next();
				System.out.print("Driver surname: \r\n");
				String surname = scan.next();
				while(!driverF.driverExists(name, surname)) {
					System.out.print("Driver cannot be found.\r\nPlease choose between: " + driverF.getDrivers() + "\r\n");
					System.out.print("Driver name: \r\n");
					name = scan.next();
					System.out.print("Driver surname: \r\n");
					surname = scan.next();
				}
				
				Driver d = driverF.getDriver(name, surname);
				d.setState(!d.isState());
				if(d.isState()) {
					System.out.print("Driver new status: on-duty" + "\r\n");
				}
				else {
					System.out.print("Driver new status: off-duty" + "\r\n");
				}
			}
			
			if(nextCommand.equalsIgnoreCase("moveCar")) {
				System.out.print("Car ID: \r\n");
				String id = scan.next();
				
				boolean carExists = carF.carExist(id);
				
				while(!carExists) {
					System.out.print("Car cannot be found.\r\nPlease choose between: " + carF.getCars() + "\r\n");
					id = scan.next();
					carExists = carF.carExist(id);
				}
				
				Car c = carF.getCar(id);
				System.out.print("Current Position: " + c.getGps() + "\r\n");
				System.out.print("New latitude:\r\n");
				double latitude = ScannerMethods.scanDouble(0,360);
				System.out.print("New longitude:\r\n");
				double longitude = ScannerMethods.scanDouble(0,360);
				c.setGps(new GPS(longitude, latitude));
				System.out.print("List of cars: " + carF.getCars()+"\r\n");
			}
			
			else if(nextCommand.equalsIgnoreCase("moveCustomer")) {
				Customer customer = getCustomerWithId();
				System.out.print("Current Position: " + customer.getGps() + "\r\n");
				System.out.print("New latitude:\r\n");
				double latitude = ScannerMethods.scanDouble(0,360);
				System.out.print("New longitude:\r\n");
				double longitude = ScannerMethods.scanDouble(0,360);
				customer.setGps(new GPS(longitude, latitude));
				System.out.print("List of customers: " + customerF.getCustomers()+"\r\n");
			}
			
			if(nextCommand.equalsIgnoreCase("displayState")) {
				displayState();
			}
			
			if(nextCommand.equalsIgnoreCase("ask4price")) {
				Customer c = getCustomerWithId();
				System.out.print("-- Customer current position --\r\nLongitude: " + c.getGps().getLongitude() + "\r\nLatitude: " + c.getGps().getLatitude());
				System.out.print("\r\n\r\n-- Destination --\r\nLongitude:\r\n");
				double longitude = ScannerMethods.scanDouble(0,360);
				System.out.print("Latitude:\r\n");
				double latitude = ScannerMethods.scanDouble(0,360);
				GPS destination = new GPS(longitude, latitude);
				try {
					c.requestRide(c.getGps(), destination, 1, 5000);
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			if(nextCommand.equalsIgnoreCase("simRide")) {
				Customer c = getCustomerWithId();
				System.out.print("-- Customer current position --\r\nLongitude: " + c.getGps().getLongitude() + "\r\nLatitude: " + c.getGps().getLatitude());
				System.out.print("\r\n\r\n-- Destination --\r\nLongitude:\r\n");
				double longitude = ScannerMethods.scanDouble();
				System.out.print("Latitude:\r\n");
				double latitude = ScannerMethods.scanDouble();
				GPS destination = new GPS(longitude, latitude);
				int choice = getRideType();
				try {
					c.requestRide(c.getGps(), destination, 1, 5000);
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					c.treatRequest(choice, 5000);
				} catch (InstantiationException | IllegalAccessException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			if(nextCommand.equalsIgnoreCase("simRide_i")) {
				Customer c = getCustomerWithId();
				System.out.print("-- Customer current position --\r\nLongitude: " + c.getGps().getLongitude() + "\r\nLatitude: " + c.getGps().getLatitude());
				System.out.print("\r\n\r\n-- Destination --\r\nLongitude:\r\n");
				double longitude = ScannerMethods.scanDouble();
				System.out.print("Latitude:\r\n");
				double latitude = ScannerMethods.scanDouble();
				GPS destination = new GPS(longitude, latitude);
				System.out.print("How many are you?\r\n");
				int nbPassengers = ScannerMethods.scanInt(1,9);
				try {
					c.requestRide(c.getGps(), destination, nbPassengers, 5000);
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.print("Choose an option:\r\n");
				int choice = ScannerMethods.scanInt(1,4);
				try {
					c.treatRequest(choice, 5000);
				} catch (InstantiationException | IllegalAccessException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				displayState();
				
			}
			
			else if(nextCommand.equalsIgnoreCase("displayDrivers")) {
				displayDrivers();
			}
			
			else if(nextCommand.equalsIgnoreCase("displayCustomers")) {
				displayCustomers();
			}
			
			
			
	}
	}

}

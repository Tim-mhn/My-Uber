package factories;
import java.util.*;
import balances.*;
import factories.*;
import rides.*;
import users.*;
import cars.*;
import other.*;

public abstract class AbstractFactory {

	abstract Car newCar() ;
	abstract Customer newCustomer() ;
	abstract Driver newDriver() ;
	abstract Ride newRide(String rideType) ;
	
	
}

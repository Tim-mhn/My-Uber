# My-Uber

Uber-inspired java app with a CLUI and a GUI.

As a user of this app, you can create users (customers + drivers) and cars, move the latter, ask for the price of rides depending on the type of ride, book ride and simulate the moving of users.

Go to MyUberCLUI or MyUberGUI class in the myUber package and run the main method.

Commands for the CLUI are the following:
- setup > initializes the system with drivers, customers and cars ;
- displayState > gives feedback on the state of the system ;
- displayCustomer + displayDriver > gives the list of customers (or drivers) sorted by total charge, frequency, occupation or popularity according to what you choose (CLUI asks you to choose) ;
- displayTotalCashed > displays the total amount of money spent on ride ;
- addCustomer > adds a custmer with its name / surname 
- addCarDriver > adds a driver and allocates it to a chosen car ;
- addDriver > same as before without allocating it to a car ;
- setDriverStatus > switch a driver status to on / off duty ;
- moveCar (and moveCustomer) > moves the car (or customer) to chosen GPS position ;
- ask4price > choose destination position and retrieves price for different types of ride (pool, uberblack, etc.) according to what time it is ;
- simRide_i > simulates the entire book a ride process  ;
- simRide > same as the previous one except that the ride type is entered before knowing the price ;
- exit > exit the system.

For the GUI, run the main method and the GUI will open up.

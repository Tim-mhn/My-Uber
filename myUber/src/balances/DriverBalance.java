package balances;
import other.Time;
import java.util.*;
import balances.*;
import factories.*;
import rides.*;
import users.*;
import cars.*;

public class DriverBalance extends Balance{

	private Driver driver;
	private double ratio = 0;
	private boolean lastState = false; /* gives the driver's state (on or off-duty) at the last activity */
	private Time lastActivityTime = null;
	private Time drivingTime = new Time();
	private Time onDutyTime = new Time();
	private double avgMark = 0;
	
	public DriverBalance(Driver driver) {
		super();
		this.driver = driver;
	}

	public boolean getLastState() {
		return lastState;
	}

	public void setLastState(boolean lastState) {
		this.lastState = lastState;
	}

	public double getRatio() { /* each time the method is called, it calculates the updated value of ratio and returns it */
		double onDutySecs = this.onDutyTime.TimeToSeconds();
		double drivingSecs = this.drivingTime.TimeToSeconds();
		if(onDutySecs != 0) {
			this.setRatio((double) drivingSecs/onDutySecs);
		}
		
		return this.ratio;
	}
	
	public double getRatio2() {
		return this.ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public Time getLastActivityTime() {
		if(this.lastActivityTime == null) {
			this.lastActivityTime = Time.getLocalTime();
		}
		return this.lastActivityTime;
	}

	public void setLastActivityTime(Time newActivityTime) {
		
		if(this.lastState) { /* adds up the on duty time only if the driver was on duty between the two last activities */
			Time duration = newActivityTime.getTimeDiff(this.lastActivityTime);
			this.addOnDutyTime(duration);
		}
		this.lastActivityTime = newActivityTime;
	}

	public Time getDrivingTime() {
		return drivingTime;
	}

	public void setDrivingTime(Time drivingTime) {
		this.drivingTime = drivingTime;
	}
	
	public void addDrivingTime(Time time) {
		this.drivingTime.addTime(time);
	}

	public Time getOnDutyTime() {
		return onDutyTime;
	}

	public void setOnDutyTime(Time onDutyTime) {
		this.onDutyTime = onDutyTime;
	}
	
	public void addOnDutyTime(Time time) {
		this.onDutyTime.addTime(time);
	}

	public double getAvgMark() {
		return avgMark;
	}

	public void setAvgMark(double avgMark) {
		this.avgMark = avgMark;
	}
	
	public void addMark(double mark) {
		this.avgMark = (super.getNbRides()*this.avgMark + mark)/(super.getNbRides()+1);
	}

	/* Methods used to edit the driver's on-off duty state and on-a-ride state
	 * It changes the driver's boolean attributes and also gets the state change time in order to calculate the on-duty rate of driving KPI
	 */
	
	public void updateDutyState() {
		Time previousActivityTime = this.getLastActivityTime();
		Time newTime = Time.getLocalTime();
		if(this.driver.isState()) {
			this.onDutyTime.addTime(newTime.getTimeDiff(previousActivityTime));
		}
		driver.setState(!driver.isState());
		this.lastActivityTime = newTime;
	}
	
	public void updateRideState() {
		Time previousActivityTime = this.getLastActivityTime();
		Time newTime = Time.getLocalTime();
		if(this.driver.isOnARide()) {
			this.drivingTime.addTime(newTime.getTimeDiff(previousActivityTime));
			this.onDutyTime.addTime(newTime.getTimeDiff(previousActivityTime));
		}
		driver.setOnARide(!driver.isOnARide());
		this.lastActivityTime = newTime;
	}
	
	public void addDriverRide(Ride r) {
		this.updateRideState();
		this.addNbRide();
		this.addMark(r.getDriverMark());
		super.addAmount(r.getCost());
	}
	@Override
	public String toString() {
		return "-- Number of rides: " + super.getNbRides() + "\r\n-- AvgMark=" + avgMark + "\r\n-- Total cashed: " + super.getTotalAmount() + "\r\n-- Ratio: " + this.getRatio() + "\r\n-- LastActivityTime=" + lastActivityTime
				+ "\r\n-- DrivingTime: " + drivingTime + "-- OnDutyTime: " + onDutyTime + "\r\n";
	}
	
	
}

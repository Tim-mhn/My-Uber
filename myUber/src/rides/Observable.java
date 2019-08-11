package rides;

import users.*;
public interface Observable {
	
	public void registerObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObservers() throws InterruptedException;
	public void removeObservers();
	public void setChange(String state) throws InterruptedException;

}

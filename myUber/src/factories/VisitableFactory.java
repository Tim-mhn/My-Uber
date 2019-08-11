package factories;

import java.util.ArrayList;
import sorters.Sorter;
import users.Observer;

public interface VisitableFactory {

	public ArrayList<Observer> accept(Sorter sorter);
}

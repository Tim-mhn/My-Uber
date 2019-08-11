package sorters;

import factories.VisitableFactory;
import users.*;
import factories.*;
import java.util.ArrayList;

public interface Sorter { /* "Visitor" interface */
	
	public ArrayList<Observer> visit(VisitableFactory factory);

}

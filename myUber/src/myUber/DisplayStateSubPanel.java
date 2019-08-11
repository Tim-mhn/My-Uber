package myUber;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import cars.Car;
import factories.CarFactory;
import factories.CustomerFactory;
import factories.DriverFactory;
import other.GPS;
import other.Time;
import users.Customer;
import users.Driver;

@SuppressWarnings("serial")
public class DisplayStateSubPanel extends JPanel implements ActionListener, SubPanels{

	private String output = "";
	//private JTextArea textArea = new JTextArea("", 5, 10);
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DisplayStateSubPanel() {
	
	    //add(textArea);
	    //JScrollPane scroll = new JScrollPane(textArea);
	    //this.add(scroll);
	    
	    
  }

  
  
	  public String getOutput() {
		return output;
	  }



	public void setOutput(String output) {
		this.output = output;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void refresh() {
		output = "";
		ArrayList<Car> cars = CarFactory.getInstance().getCars();
		ArrayList<Customer> customers = CustomerFactory.getInstance().getCustomers();
		ArrayList<Driver> drivers = DriverFactory.getInstance().getDrivers();
		for(Customer customer : customers) {
			output += customer.toString()+"\r\n";
		}
		
		for(Driver d: drivers) {
			output += d.toString()+"\r\n";
		}
		for(Car c : cars) {
			output += c.toString()+"\r\n";
		}
		
		MyUberGUI.getInstance().getTextArea().append("\r\n\r\n" + Time.getLocalTime().toString() + "\r\n" + this.output);
		
		
		
	}
	
	
	public static void main(String argv[]) {
	
	    JFrame f = new JFrame("SimpleFTF ");
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.setContentPane(new MoveCarSubPanel());
	    f.pack();
	    f.setVisible(true);
	    
	    
	  }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// nothing	
	}
		
}	



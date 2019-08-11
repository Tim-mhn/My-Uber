package myUber;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import cars.Car;
import factories.CarFactory;
import factories.CustomerFactory;
import factories.DriverFactory;
import other.Time;
import users.Customer;
import users.Driver;

@SuppressWarnings("serial")
public class SetupSubPanel extends JPanel implements ActionListener, SubPanels {
	
	private String output = "";
	private JButton button = new JButton("Setup");
	private JFormattedTextField[] ftf = new JFormattedTextField[4];
	private String[] des = new String[4];
	//private JTextArea textArea = new JTextArea("", 5, 10);
	

	public SetupSubPanel() {
	
	    des[0] = "Standard cars";
	    ftf[0] = new JFormattedTextField(new Integer(0));
	
	    des[1] = "Berlines";
	    ftf[1] = new JFormattedTextField(new Integer(0));
	
	    des[2] = "Vans";
	    ftf[2] = new JFormattedTextField(new Integer(0));
	
	    des[3] = "Customers"; // manually specify a NumberFormat
	    ftf[3] = new JFormattedTextField(new Integer(0));
	   
	    // add each ftf[] to a BoxLayout
	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    for (int j = 0; j < ftf.length; j += 1) {
	      JPanel borderPanel = new JPanel(new java.awt.BorderLayout());
	      borderPanel.setBorder(new javax.swing.border.TitledBorder(des[j]));
	      borderPanel.add(ftf[j], java.awt.BorderLayout.CENTER);
	      add(borderPanel);
	    }
	    add(this.button);
	    //add(textArea);
	    //JScrollPane scroll = new JScrollPane(textArea);
	    //this.add(scroll);
	    
	    this.button.addActionListener(this);
	    
	    
  }

  
  
	  public String getOutput() {
		return output;
	  }



	public void setOutput(String output) {
		this.output = output;
	}
	
	
	
	public static void main(String argv[]) {
	
	    JFrame f = new JFrame("SimpleFTF ");
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.setContentPane(new SetupSubPanel());
	    f.pack();
	    f.setVisible(true);
	    
	    
	  }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		output = "";
		if(e.getSource().equals(button)) {
			MyUberCLUI.setup((int) ftf[0].getValue(), (int) ftf[1].getValue(), (int) ftf[2].getValue(), (int) ftf[3].getValue());
			CarFactory carF = CarFactory.getInstance();
			DriverFactory driverF = DriverFactory.getInstance();
			CustomerFactory customerF = CustomerFactory.getInstance();
			ArrayList<Car> cars = carF.getCars();
			ArrayList<Driver> drivers = driverF.getDrivers();
			ArrayList<Customer> customers = customerF.getCustomers();
			output += "\r\n-- State System -- \r\n\r\n-- Cars -- \r\n";
			output += "Total number: " + cars.size()+"\r\n";
			output += "Standard: " + carF.getStandards().size()+"\r\n";
			output += "Berline: " + carF.getBerlines().size()+"\r\n";
			output += "Van: " + carF.getVans().size()+"\r\n\r\nList of cars: \r\n";
			for(Car c : cars) {
				output += c.toString()+"\r\n";
			}
			output += "\r\n-- Drivers --\r\nTotal number: " + drivers.size() + "\r\n\r\nList of drivers: \r\n";
			for(Driver d : drivers) {
				output += d.toString() + "\r\n";
			}
			output += "\r\n-- Customers -- \r\nTotal number: " + customers.size() + "\r\n\r\nList of customers: \r\n";
			for(Customer c : customers) {
				output += c.toString() + "\r\n";
			}
			
		}
		MyUberGUI.getInstance().getTextArea().append("\r\n\r\n" + Time.getLocalTime().toString() + "\r\n" + this.output);
	}



	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
}

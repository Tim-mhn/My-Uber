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

import booking.Booking;
import cars.Car;
import factories.CarFactory;
import factories.CustomerFactory;
import factories.DriverFactory;
import other.GPS;
import other.Time;
import rides.Ride;
import users.Customer;
import users.Driver;

@SuppressWarnings("serial")
public class SimRideSubPanel extends JPanel implements ActionListener, SubPanels {

	private String output = "";
	private JButton button = new JButton("Simulate ride");
	private Object[] ftf = new Object[7];
	private String[] des = new String[7];
	//private JTextArea textArea = new JTextArea("", 10, 20);
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SimRideSubPanel() {
	
	    des[0] = "Customer";
	    JComboBox combo0= new JComboBox();
	    ArrayList<Customer> customers = CustomerFactory.getInstance().getCustomers();
	    for(Customer c : customers) {
	    	combo0.addItem(c.getPrenom() + " " + c.getNom() + " " + Integer.toString(c.getId()));
	    }
	    ftf[0] = combo0;
	
	    des[1] = "Longitude";
	    ftf[1] = new JFormattedTextField(new Double(0.0));
	    
	    des[2] = "Latitude";
	    ftf[2] = new JFormattedTextField(new Double(0.0));
	    
	    des[3] = "Ride type";
	    JComboBox combo3 = new JComboBox();
	    combo3.addItem("1 UberBlack");
	    combo3.addItem("2 UberPool");
	    combo3.addItem("3 UberVan");
	    combo3.addItem("4 UberX");
	    ftf[3] = combo3;
	    
	    des[4] = "Number of passengers";
	    JComboBox combo4 = new JComboBox();
	    combo4.addItem("1");
	    combo4.addItem("2");
	    combo4.addItem("3");
	    combo4.addItem("4");
	    combo4.addItem("5");
	    combo4.addItem("6");
	    combo4.addItem("7");
	    ftf[4] = combo4;
	    
	    des[5] = "Driver Mark";
	    JComboBox combo5 = new JComboBox();
	    combo5.addItem("1");
	    combo5.addItem("2");
	    combo5.addItem("3");
	    combo5.addItem("4");
	    combo5.addItem("5");
	    ftf[5] = combo5;
	    
	    des[6] = "Ride Mark";
	    JComboBox combo6 = new JComboBox();
	    combo6.addItem("1");
	    combo6.addItem("2");
	    combo6.addItem("3");
	    combo6.addItem("4");
	    combo6.addItem("5");
	    ftf[6] = combo6;
	    
	    // add each ftf[] to a BoxLayout
	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    JPanel sub1 = new JPanel();
	    sub1.setLayout(new BoxLayout(sub1, BoxLayout.LINE_AXIS));
	    
	    JPanel sub2 = new JPanel();
	    sub2.setLayout(new BoxLayout(sub2, BoxLayout.LINE_AXIS));
	    
	    JPanel sub3 = new JPanel();
	    sub3.setLayout(new BoxLayout(sub3, BoxLayout.LINE_AXIS));
	    
	    JPanel sub4 = new JPanel();
	    sub4.setLayout(new BoxLayout(sub4, BoxLayout.LINE_AXIS));
	    
	    JPanel borderPanel0 = new JPanel(new java.awt.BorderLayout());
	    borderPanel0.setBorder(new javax.swing.border.TitledBorder(des[0]));
	    borderPanel0.add((JComboBox) ftf[0], java.awt.BorderLayout.CENTER);
	    sub1.add(borderPanel0);
	    
	    for(int i = 1; i<3; i++) {
	    	JPanel borderPanel1 = new JPanel(new java.awt.BorderLayout());
		    borderPanel1.setBorder(new javax.swing.border.TitledBorder(des[i]));
		    borderPanel1.add((JFormattedTextField) ftf[i], java.awt.BorderLayout.CENTER);
		    sub2.add(borderPanel1);
	    }
	    
	   for(int i = 3; i<ftf.length; i++) {
		   JPanel borderPanel2 = new JPanel(new java.awt.BorderLayout());
		    borderPanel2.setBorder(new javax.swing.border.TitledBorder(des[i]));
		    borderPanel2.add((JComboBox) ftf[i], java.awt.BorderLayout.CENTER);
		    if(i<=4){
		    	sub1.add(borderPanel2);
		    }
		    else {
		    	sub3.add(borderPanel2);
		    }
		    
	   }
	    	    
	    sub4.add(this.button);
	    this.button.addActionListener(this);
	    add(sub1);
	    add(sub2);
	    add(sub3);
	    add(sub4);
	    
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
	public void refresh() { // refreshed the list of customers
		((JComboBox) ftf[0]).removeAllItems();
		 ArrayList<Customer> customers = CustomerFactory.getInstance().getCustomers();
		 for(Customer c : customers) {
		    ((JComboBox) ftf[0]).addItem(c.getPrenom() + " " + c.getNom() + " " + Integer.toString(c.getId()));
		  }
	}
	
	
	public static void main(String argv[]) {
	
	    JFrame f = new JFrame("SimpleFTF ");
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.setContentPane(new SimRideSubPanel());
	    f.pack();
	    f.setVisible(true);
	    
	    
	  }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		output = "";
		if(e.getSource().equals(this.button)) {
			
			// fetch the right customer in customer factory
			@SuppressWarnings("rawtypes")
			String nameAndSurname = (String) ((JComboBox) ftf[0]).getSelectedItem();
			String[] info = nameAndSurname.split(" ");
			Integer id = Integer.parseInt(info[2]);
			Customer c = CustomerFactory.getInstance().getCustomer(id);
			
			Double longitude = (Double) ((JFormattedTextField) ftf[1]).getValue();
			Double latitude = (Double) ((JFormattedTextField) ftf[2]).getValue();
			@SuppressWarnings("rawtypes")
			Integer nbPassengers = Integer.parseInt((String) ((JComboBox) ftf[4]).getSelectedItem());
			
			Integer rideMark = Integer.parseInt((String) ((JComboBox) ftf[6]).getSelectedItem());
			Integer driverMark = Integer.parseInt((String) ((JComboBox) ftf[5]).getSelectedItem());
			// Fetch index corresponding to ride type choice
			@SuppressWarnings("rawtypes")
			String results = (String) ((JComboBox) ftf[3]).getSelectedItem();
			System.out.print("results: " + results);
			String[] split = results.split(" ");
			System.out.print("split: " + split);
			int choice = Integer.parseInt(split[0]);
			
			try {
				ArrayList<String> prices = c.requestRide(c.getGps(), new GPS(longitude, latitude), nbPassengers, 5000);
				for(Ride r : c.getBooking().getRideTypes()) {
					r.setRideMark(rideMark);
					r.setDriverMark(driverMark);
				}
			} catch (InstantiationException | IllegalAccessException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				c.treatRequest(choice, 5000);
			} catch (InstantiationException | IllegalAccessException | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			double cost = c.getBooking().getRide().getCost();
			c.getBooking().getDriver().getBalance().addMark(driverMark);
			ArrayList<String> newMessages = c.getMessageBox().getLastMessages();
			for(String msg : newMessages) {
				output += msg + "\r\n";
			}
			output += "Ride cost: " + cost + "€\r\n";
			
			
		}
		MyUberGUI.getInstance().getTextArea().append("\r\n\r\n" + Time.getLocalTime().toString() + "\r\n" + this.output);
		
	}	
}


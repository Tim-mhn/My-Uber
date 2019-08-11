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
import users.Customer;
import users.Driver;

@SuppressWarnings("serial")
public class Ask4PriceSubPanel extends JPanel implements ActionListener, SubPanels{

	private String output = "";
	private JButton button = new JButton("Ask for price");
	private Object[] ftf = new Object[3];
	private String[] des = new String[3];
	//private JTextArea textArea = new JTextArea("", 5, 10);
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Ask4PriceSubPanel() {
	
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
	    
	    // add each ftf[] to a BoxLayout
	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    JPanel borderPanel0 = new JPanel(new java.awt.BorderLayout());
	    borderPanel0.setBorder(new javax.swing.border.TitledBorder(des[0]));
	    borderPanel0.add((JComboBox) ftf[0], java.awt.BorderLayout.CENTER);
	    add(borderPanel0);
	    
	    for(int i = 1; i<ftf.length; i++) {
	    	JPanel borderPanel1 = new JPanel(new java.awt.BorderLayout());
		    borderPanel1.setBorder(new javax.swing.border.TitledBorder(des[i]));
		    borderPanel1.add((JFormattedTextField) ftf[i], java.awt.BorderLayout.CENTER);
		    add(borderPanel1);
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void refresh() {
		((JComboBox) ftf[0]).removeAllItems();
		 ArrayList<Customer> customers = CustomerFactory.getInstance().getCustomers();
		 for(Customer c : customers) {
		    ((JComboBox) ftf[0]).addItem(c.getPrenom() + " " + c.getNom() + " " + Integer.toString(c.getId()));
		  }
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
		output = "";
		if(e.getSource().equals(this.button)) {
			
			@SuppressWarnings("rawtypes")
			String nameAndSurname = (String) ((JComboBox) ftf[0]).getSelectedItem();
			String[] info = nameAndSurname.split(" ");
			Integer id = Integer.parseInt(info[2]);
			Customer c = CustomerFactory.getInstance().getCustomer(id);
			
			Double longitude = (Double) ((JFormattedTextField) ftf[1]).getValue();
			Double latitude = (Double) ((JFormattedTextField) ftf[2]).getValue();
			try {
				ArrayList<String> prices = c.requestRide(c.getGps(), new GPS(longitude, latitude), 1, 5000);
				double distance = c.getGps().calculateDistance(new GPS(longitude, latitude));
				output += "Ride distance: " + distance + "km\r\n\r\n";
				for(String price : prices) {
					output += price + "\r\n";
				}
			} catch (InstantiationException | IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.print("fail");
			}
			
			
		}
		MyUberGUI.getInstance().getTextArea().append("\r\n\r\n" + Time.getLocalTime().toString() + "\r\n" + this.output);
		
	}	
}


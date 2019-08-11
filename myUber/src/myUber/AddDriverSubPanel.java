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
import other.Time;
import users.Customer;
import users.Driver;

@SuppressWarnings("serial")
public class AddDriverSubPanel extends JPanel implements ActionListener, SubPanels{

	private String output = "";
	private JButton button = new JButton("Add driver");
	private Object[] ftf = new Object[3];
	private String[] des = new String[3];
	//private JTextArea textArea = new JTextArea("", 5, 10);
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AddDriverSubPanel() {
	
	    des[0] = "Name";
	    ftf[0] = new JFormattedTextField(new String());
	
	    des[1] = "Surname";
	    ftf[1] = new JFormattedTextField(new String());
	    
	    des[2] = "Car ID";
	    JComboBox combo = new JComboBox();
	    ArrayList<Car> cars = CarFactory.getInstance().getCars();
	    for(Car c : cars) {
	    	combo.addItem(c.getID());
	    }
	    ftf[2] = combo;
	
	    
	   
	    // add each ftf[] to a BoxLayout
	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    for (int j = 0; j < ftf.length-1; j += 1) {
	      JPanel borderPanel = new JPanel(new java.awt.BorderLayout());
	      borderPanel.setBorder(new javax.swing.border.TitledBorder(des[j]));
	      borderPanel.add((JFormattedTextField) ftf[j], java.awt.BorderLayout.CENTER);
	      add(borderPanel);
	    }
	    
	    JPanel borderPanel = new JPanel(new java.awt.BorderLayout());
	    borderPanel.setBorder(new javax.swing.border.TitledBorder(des[2]));
	    borderPanel.add((JComboBox) ftf[2], java.awt.BorderLayout.CENTER);
	    add(borderPanel);
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
		((JComboBox) ftf[2]).removeAllItems();
		ArrayList<Car>cars = CarFactory.getInstance().getCars();
		for(Car c : cars) {
	    	((JComboBox) ftf[2]).addItem(c.getID());
	    }
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
			DriverFactory dF = DriverFactory.getInstance();
			@SuppressWarnings("rawtypes")
			String id = (String) ((JComboBox) ftf[2]).getSelectedItem();
			Car car = CarFactory.getInstance().getCar(id);
			dF.newDriver((String) ((JFormattedTextField) ftf[0]).getValue(), (String) ((JFormattedTextField) ftf[1]).getValue(), car);
			
			for(Driver d : dF.getDrivers()) {
				output += d.toString() + "\r\n\r\n";
			}
			
			
			
			
		}
		MyUberGUI.getInstance().getTextArea().append("\r\n\r\n" + Time.getLocalTime().toString() + "\r\n" + this.output);;
		
	}	
}


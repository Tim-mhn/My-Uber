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
public class AddCarDriverSubPanel extends JPanel implements ActionListener, SubPanels{

	private String output = "";
	private JButton button = new JButton("Add Car With Driver");
	private Object[] ftf = new Object[3];
	private String[] des = new String[3];
	

	@SuppressWarnings("unchecked")
	public AddCarDriverSubPanel() {
	
	    des[0] = "Driver Name";
	    ftf[0] = new JFormattedTextField(new String());
	
	    des[1] = "Driver Surname";
	    ftf[1] = new JFormattedTextField(new String());
	    
	    des[2] = "Car-type";
	    JComboBox combo = new JComboBox();
	    combo.addItem("Standard");
	    combo.addItem("Berline");
	    combo.addItem("Van");
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
			DriverFactory driverF = DriverFactory.getInstance();
			CarFactory carF = CarFactory.getInstance();
			String carType = (String) ((JComboBox) ftf[2]).getSelectedItem();
			Car car = carF.newCar(carType);
			driverF.newDriver((String) ((JFormattedTextField) ftf[0]).getValue(), (String) ((JFormattedTextField) ftf[1]).getValue(), car);
			for(Driver d : driverF.getDrivers()) {
				output += d.toString();
			}
			
			for(Car c : carF.getCars()) {
				output += c.toString();
			}
			MyUberGUI.getInstance().getTextArea().append("\r\n\r\n" + Time.getLocalTime().toString() + "\r\n" + this.output);
		}
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}	
}


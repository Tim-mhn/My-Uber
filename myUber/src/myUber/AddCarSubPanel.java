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
public class AddCarSubPanel extends JPanel implements ActionListener, SubPanels{

	private String output = "";
	private JButton button = new JButton("Add car");
	private Object[] ftf = new Object[2];
	private String[] des = new String[2];
	//private JTextArea textArea = new JTextArea("", 5, 10);
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AddCarSubPanel() {
	
		des[0] = "Car type";
	    JComboBox combo = new JComboBox();
	    combo.addItem("Standard");
	    combo.addItem("Berline");
	    combo.addItem("Van");
	    ftf[0] = combo;
	
	    des[1] = "Number of passenger seats";
	    ftf[1] = new JFormattedTextField(new Integer(4));
	    
	
	    
	   
	    // add each ftf[] to a BoxLayout
	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    
	    JPanel borderPanel0 = new JPanel(new java.awt.BorderLayout());
	    borderPanel0.setBorder(new javax.swing.border.TitledBorder(des[0]));
	    borderPanel0.add((JComboBox) ftf[0], java.awt.BorderLayout.CENTER);
	    add(borderPanel0);
	    
	   for (int j = 1; j < ftf.length; j += 1) {
		  JPanel borderPanel = new JPanel(new java.awt.BorderLayout());
	      borderPanel.setBorder(new javax.swing.border.TitledBorder(des[j]));
	      borderPanel.add((JFormattedTextField) ftf[j], java.awt.BorderLayout.CENTER);
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
			// creation of car 
			DriverFactory dF = DriverFactory.getInstance();
			@SuppressWarnings("rawtypes")
			String carType = (String) ((JComboBox) ftf[0]).getSelectedItem();
			int nbPassengers = (Integer) ((JFormattedTextField) ftf[1]).getValue();
			Car car = CarFactory.getInstance().newCar(carType, nbPassengers); 
			
			// display
			for(Car c : CarFactory.getInstance().getCars()) {
				output += c.toString() + "\r\n\r\n";
			}
		}
		MyUberGUI.getInstance().getTextArea().append("\r\n\r\n" + Time.getLocalTime().toString() + "\r\n" + this.output);
		
	}



	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}	
}


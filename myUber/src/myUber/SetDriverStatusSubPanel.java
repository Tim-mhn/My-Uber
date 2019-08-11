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
public class SetDriverStatusSubPanel extends JPanel implements ActionListener, SubPanels{

	private String output = "";
	private JButton button = new JButton("Set Driver Status");
	private Object[] ftf = new Object[2];
	private String[] des = new String[2];
	//private JTextArea textArea = new JTextArea("", 5, 10);
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SetDriverStatusSubPanel() {
	
	    des[0] = "Driver";
	    JComboBox combo0= new JComboBox();
	    ArrayList<Driver> drivers = DriverFactory.getInstance().getDrivers();
	    for(Driver d : drivers) {
	    	combo0.addItem(d.getName() + " " + d.getSurname());
	    }
	    ftf[0] = combo0;
	
	    des[1] = "Status";
	    JComboBox combo1 = new JComboBox();
	    combo1.addItem("on-duty");
	    combo1.addItem("off-duty");
	    ftf[1] = combo1;
	    
	    // add each ftf[] to a BoxLayout
	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    for(int i = 0; i<ftf.length; i++) {
	    	JPanel borderPanel = new JPanel(new java.awt.BorderLayout());
		    borderPanel.setBorder(new javax.swing.border.TitledBorder(des[i]));
		    borderPanel.add((JComboBox) ftf[i], java.awt.BorderLayout.CENTER);
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void refresh() {
		((JComboBox) ftf[0]).removeAllItems();
		ArrayList<Driver> drivers = DriverFactory.getInstance().getDrivers();
		for(Driver d : drivers) {
	    	((JComboBox) ftf[0]).addItem(d.getName() + " " + d.getSurname());
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
			String nameAndSurname = (String) ((JComboBox) ftf[0]).getSelectedItem();
			String[] names = nameAndSurname.split(" ");
			
			Driver d = dF.getDriver(names[0], names[1]);
			String status = (String) ((JComboBox) ftf[1]).getSelectedItem();
			d.setState(status.equalsIgnoreCase("on-duty"));
			
			output += d.toString();
			
		}
		MyUberGUI.getInstance().getTextArea().append("\r\n\r\n" + Time.getLocalTime().toString() + "\r\n" + this.output);
		
	}	
}


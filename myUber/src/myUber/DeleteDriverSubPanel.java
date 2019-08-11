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
public class DeleteDriverSubPanel extends JPanel implements ActionListener, SubPanels{

	private String output = "";
	private JButton button = new JButton("Delete driver");
	private Object[] ftf = new Object[1];
	private String[] des = new String[1];
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DeleteDriverSubPanel() {
	
		des[0] = "Driver";
	    JComboBox combo0= new JComboBox();
	    ArrayList<Driver> drivers = DriverFactory.getInstance().getDrivers();
	    for(Driver d : drivers) {
	    	combo0.addItem(d.getName() + " " + d.getSurname());
	    }
	    ftf[0] = combo0;
	
	    
	   
	    // add each ftf[] to a BoxLayout
	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    JPanel borderPanel0 = new JPanel(new java.awt.BorderLayout());
	    borderPanel0.setBorder(new javax.swing.border.TitledBorder(des[0]));
	    borderPanel0.add((JComboBox) ftf[0], java.awt.BorderLayout.CENTER);
	    add(borderPanel0);
	    add(this.button);
	    
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
		if(e.getSource().equals(button)) {
			DriverFactory dF = DriverFactory.getInstance();
			@SuppressWarnings("rawtypes")
			String nameAndSurname = (String) ((JComboBox) ftf[0]).getSelectedItem();
			String[] names = nameAndSurname.split(" ");
			
			Driver d = dF.getDriver(names[0], names[1]);
			dF.getDrivers().remove(d);
			MyUberGUI.getInstance().getTextArea().append("\r\n\r\n" + Time.getLocalTime().toString() + "\r\nDriver " + d.getName()+ " " + d.getSurname() + " successfully removed !" );
			this.refresh();
		}
		
	}



	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void refresh() {
		((JComboBox) ftf[0]).removeAllItems();
		ArrayList<Driver> drivers = DriverFactory.getInstance().getDrivers();
		for(Driver d : drivers) {
	    	((JComboBox) ftf[0]).addItem(d.getName() + " " + d.getSurname());
	    }
	}	
}

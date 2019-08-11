package myUber;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

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
import sorters.SystemSorter;
import users.Customer;
import users.Driver;

@SuppressWarnings("serial")
public class DisplayDriversSubPanel extends JPanel implements ActionListener, SubPanels{

	private String output = "";
	private JButton button = new JButton("Display drivers");
	private Object[] ftf = new Object[1];
	private String[] des = new String[1];
	//private JTextArea textArea = new JTextArea("", 5, 10);
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DisplayDriversSubPanel() {
	    
	    des[0] = "Sorting method";
	    JComboBox combo = new JComboBox();
	    combo.addItem("Most appreciated");
	    combo.addItem("Most charged");
	    ftf[0] = combo;
	
	    
	   
	    // add each ftf[] to a BoxLayout
	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    
	    JPanel borderPanel = new JPanel(new java.awt.BorderLayout());
	    borderPanel.setBorder(new javax.swing.border.TitledBorder(des[0]));
	    borderPanel.add((JComboBox) ftf[0], java.awt.BorderLayout.CENTER);
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
	
	public void refresh() {
		// do nothing
	}
	
	
	public static void main(String argv[]) {
	
	    JFrame f = new JFrame("SimpleFTF ");
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.setContentPane(new DisplayDriversSubPanel());
	    f.pack();
	    f.setVisible(true);
	    
	    
	  }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		output = "";
		if(e.getSource().equals(button)) {
			@SuppressWarnings("rawtypes")
			String sortPolicy = (String) ((JComboBox) ftf[0]).getSelectedItem();
			ArrayList<users.Observer> sortedDriverList = new ArrayList<users.Observer>();
			if(sortPolicy.equalsIgnoreCase("Most Appreciated")) {
				sortedDriverList = SystemSorter.getSortedDriversByAppreciation();
			}
			else {
				sortedDriverList = SystemSorter.getSortedDriversByOccupation();
			}
			for(int i = 0; i<sortedDriverList.size(); i++) {
				output += "N°" + (i+1) + ": " + sortedDriverList.get(i).toString() + "\r\n\r\n";
			}
			
		}
		MyUberGUI.getInstance().getTextArea().append("\r\n\r\n" + Time.getLocalTime().toString() + "\r\n" + this.output);;
		
	}	
}


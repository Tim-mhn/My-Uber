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
public class AddCustomerSubPanel extends JPanel implements ActionListener, SubPanels{

	private String output = "";
	private JButton button = new JButton("Add customer");
	private JFormattedTextField[] ftf = new JFormattedTextField[2];
	private String[] des = new String[2];
	//private JTextArea textArea = new JTextArea("", 5, 10);
	

	public AddCustomerSubPanel() {
	
	    des[0] = "Name";
	    ftf[0] = new JFormattedTextField(new String());
	
	    des[1] = "Surname";
	    ftf[1] = new JFormattedTextField(new String());
	
	    
	   
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
			CustomerFactory customerF = CustomerFactory.getInstance();
			customerF.newCustomer((String) ftf[0].getValue(), (String) ftf[1].getValue());
			
			for(Customer c : customerF.getCustomers()) {
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

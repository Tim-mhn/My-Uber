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
public class DeleteCustomerSubPanel extends JPanel implements ActionListener, SubPanels{

	private String output = "";
	private JButton button = new JButton("Delete customer");
	private Object[] ftf = new Object[1];
	private String[] des = new String[1];
	//private JTextArea textArea = new JTextArea("", 5, 10);
	

	@SuppressWarnings("unchecked")
	public DeleteCustomerSubPanel() {
	
		des[0] = "Customer";
	    @SuppressWarnings("rawtypes")
		JComboBox combo0= new JComboBox();
	    ArrayList<Customer> customers = CustomerFactory.getInstance().getCustomers();
	    for(Customer c : customers) {
	    	combo0.addItem(c.getPrenom() + " " + c.getNom() + " " + Integer.toString(c.getId()));
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
			String nameAndSurname = (String) ((JComboBox) ftf[0]).getSelectedItem();
			String[] info = nameAndSurname.split(" ");
			Integer id = Integer.parseInt(info[2]);
			Customer c = CustomerFactory.getInstance().getCustomer(id);
			CustomerFactory.getInstance().getCustomers().remove(c);
			this.refresh();
			MyUberGUI.getInstance().getTextArea().append("\r\n\r\n" + Time.getLocalTime().toString() + "\r\nCustomer " + c.getPrenom()+ " " + c.getNom() + " successfully removed !" );
		}
		
	}



	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void refresh() {
		((JComboBox) ftf[0]).removeAllItems();
		 ArrayList<Customer> customers = CustomerFactory.getInstance().getCustomers();
		 for(Customer c : customers) {
		    ((JComboBox) ftf[0]).addItem(c.getPrenom() + " " + c.getNom() + " " + Integer.toString(c.getId()));
		  }
	}	
}

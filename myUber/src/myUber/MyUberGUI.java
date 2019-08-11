package myUber;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import factories.CustomerFactory;


@SuppressWarnings("serial")
public class MyUberGUI extends JFrame implements ActionListener {
	
	private final ArrayList<String> buttonNames = new ArrayList<String>(Arrays.asList("Setup", "Add Customer", "Add Car with driver",
			"Add Driver", "Add Car","Set Driver Status", "Move Car", "Move Customer", "Display State", "Ask for price", "Simulate Ride", "Display drivers", "Display Customers", "Delete Customer", "Delete Driver"));
	
	//private String textDisplayed = "";
	private JPanel panel = new JPanel(new GridLayout(0,1));
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	private JPanel inputPanel = new JPanel(new CardLayout()); // input
	private JPanel outputPanel = new JPanel(new GridLayout(1,1)); // output
	private JTextArea textArea = new JTextArea("", 10, 10); // text Area displayed in output panel
	private SubPanels[] subPanels = new SubPanels[] {new SetupSubPanel(), new AddCustomerSubPanel(), new AddCarDriverSubPanel(), new AddDriverSubPanel(),
			new AddCarSubPanel(), new SetDriverStatusSubPanel(), new MoveCarSubPanel(), new MoveCustomerSubPanel(), new DisplayStateSubPanel(), new Ask4PriceSubPanel(),
			new SimRideSubPanel(), new DisplayDriversSubPanel(), new DisplayCustomersSubPanel(), new DeleteCustomerSubPanel(), new DeleteDriverSubPanel()};

	private static MyUberGUI instance = new MyUberGUI();

	public static MyUberGUI getInstance() {
		return instance;
	}
	
	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}








	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		int index = this.buttons.indexOf(button);
		subPanels[index].refresh(); // refresh data, especially for lists
		CardLayout c = (CardLayout)(inputPanel.getLayout());
		c.show(inputPanel, Integer.toString(index+1));
	}
	
	public MyUberGUI() {
		super("My Uber GUI");
		
		JPanel commandsPanel = new JPanel(new GridLayout(4,4));
		setLayout(new FlowLayout());
		this.setContentPane(panel);
		panel.add(commandsPanel);
		
		TitledBorder commandsBorder = new TitledBorder("Commands");
		commandsBorder.setTitleJustification(TitledBorder.CENTER);
		commandsBorder.setTitlePosition(TitledBorder.TOP);
		commandsPanel.setBorder(commandsBorder);
		
		for(int i = 0; i<this.subPanels.length; i++) {
			inputPanel.add((JPanel) subPanels[i], Integer.toString(i+1));
			JButton button = new JButton(buttonNames.get(i));
			buttons.add(button);
			commandsPanel.add(button);
			button.addActionListener(this);
		}
		inputPanel.setMaximumSize(new Dimension(200,10));
		TitledBorder inputBorder = new TitledBorder("Input");
		inputBorder.setTitleJustification(TitledBorder.CENTER);
		inputBorder.setTitlePosition(TitledBorder.TOP);
		inputPanel.setBorder(inputBorder);
		
		TitledBorder outputBorder = new TitledBorder("Output");
		outputBorder.setTitleJustification(TitledBorder.CENTER);
		outputBorder.setTitlePosition(TitledBorder.TOP);
		outputPanel.setBorder(outputBorder);
		panel.add(inputPanel);
		panel.add(outputPanel);
		outputPanel.add(textArea);
		JScrollPane scroll = new JScrollPane(textArea);
	    outputPanel.add(scroll);
		
		
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		
		myUber.setMode("GUI");
		SwingUtilities.invokeLater(new Runnable() {public void run() {MyUberGUI.getInstance();}});
	}
}

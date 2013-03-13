import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.plaf.FontUIResource;

public class AdminView extends JFrame {
	DB db;
	int id;
	JButton gmsButton;
	JTextField gmsTextField;
	JLabel gmsLabel;
	JButton crButton;
	JTextField crTextField;
	JLabel crLabel;
	JButton addInterest;
	JButton generateDTER;
	JButton listActiveCustomers;
	JButton deleteTransactions;
	JButton setDate;
	JTextField dateTextField;
	JLabel dateLabel;

	JTextArea textArea;
	JScrollPane scroller;


	public AdminView(DB mydb, int myid){
		super("Admin");
		setLayout(null);

		db = mydb;
		id = myid;

		FontUIResource fbold = new FontUIResource(Font.SANS_SERIF,Font.BOLD,14);
		FontUIResource fplain = new FontUIResource(Font.SANS_SERIF,Font.PLAIN,16);
		UIManager.put("Label.font", fbold);
		UIManager.put("Button.font", fbold);
		UIManager.put("TextField.font", fplain);

		gmsButton = new JButton("Generate Monthly Statement");
		gmsButton.setBounds(20, 20, 300, 50);
		gmsTextField = new JTextField();
		gmsTextField.setBounds(350, 20, 300, 50);
		gmsLabel = new JLabel("Tax ID");
		gmsLabel.setBounds(475, 50, 200, 50);
		gmsLabel.setDisplayedMnemonic(KeyEvent.VK_P);

		crButton = new JButton("Customer Report");
		crButton.setBounds(20, 90, 300, 50);
		crTextField = new JTextField();
		crTextField.setBounds(350, 90, 300, 50);
		crLabel = new JLabel("Tax ID");
		crLabel.setBounds(475, 120, 200, 50);
		crLabel.setDisplayedMnemonic(KeyEvent.VK_P);

		addInterest = new JButton("Add Interest");
		addInterest.setBounds(20, 160, 300, 50);
		generateDTER = new JButton("Generate DTER");
		generateDTER.setBounds(350, 160, 300, 50);

		listActiveCustomers = new JButton("List Active Customers");
		listActiveCustomers.setBounds(20, 230, 300, 50);
		deleteTransactions = new JButton("Delete Transactions");
		deleteTransactions.setBounds(350, 230, 300, 50);

		setDate = new JButton("Set Date");
		setDate.setBounds(20, 300, 300, 50);
		dateTextField = new JTextField();
		dateTextField.setBounds(350, 300, 300, 50);
		dateLabel = new JLabel("Enter Date yyyy-mm-dd");
		dateLabel.setBounds(420, 330, 200, 50);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		scroller = new JScrollPane(textArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setBounds(20,370,640,395);

		add(gmsButton); add(gmsTextField); add(gmsLabel); add(crButton);
		add(crTextField); add(crLabel); add(addInterest);
		add(generateDTER); add(listActiveCustomers); add(deleteTransactions); add(setDate);
		add(dateTextField); add(dateLabel); add(scroller);

		MyHandler handler = new MyHandler();
		gmsButton.addActionListener(handler);
		crButton.addActionListener(handler);
		addInterest.addActionListener(handler);
		generateDTER.addActionListener(handler);
		listActiveCustomers.addActionListener(handler);
		deleteTransactions.addActionListener(handler);
		setDate.addActionListener(handler);


	}

	 private class MyHandler implements ActionListener {
    	public void actionPerformed(ActionEvent event) {
    		//DEPOSIT
    		if (event.getSource() == gmsButton){
    			String strTax_ID = gmsTextField.getText();
    			int tax_ID = Integer.parseInt(strTax_ID);
    			//textArea.append("Clicked\n");
    		}
    		else if (event.getSource() == crButton){
    			String strTax_ID = gmsTextField.getText();
    			int tax_ID = Integer.parseInt(strTax_ID);
    		}
    		else if (event.getSource() == addInterest){

    		}
    		else if (event.getSource() == generateDTER){

    		}
    		else if (event.getSource() == listActiveCustomers){

    		}
    		else if (event.getSource() == deleteTransactions){

    		}
    		else if (event.getSource() == setDate){

    		}
    	}
    }

}
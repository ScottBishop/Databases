import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Admin extends JFrame {

	DB db;
	int id;
	JFrame frame;
	Container content;
	JPanel gmsPanel;
	JButton gmsButton;
	JTextField gmsTextField;
	JLabel gmsLabel;
	JPanel crPanel;
	JButton crButton;
	JTextField crTextField;
	JLabel crLabel;
	JPanel onePanel;
	JPanel twoPanel;
	JPanel topPanel;
	JPanel tax_IDPanel1;
	JPanel tax_IDPanel2;
	JPanel midPanel;
	JPanel halfPanel;
	JPanel bottomPanel;

	JButton addInterest;
	JButton generateDTER;
	JButton listActiveCustomers;
	JButton deleteTransactions;

	JTextArea textArea;
	JScrollPane scroller;
	JPanel mainPanel;



	public Admin(DB mydb, int myid) {
		frame = new JFrame("Admin");
		content = frame.getContentPane();

		db = mydb;
		id = myid;

		gmsPanel = new JPanel(new BorderLayout());
		gmsButton = new JButton("Generate Monthly Statement");
		gmsTextField = new JTextField();
		gmsLabel = new JLabel("Tax ID");
		gmsLabel.setDisplayedMnemonic(KeyEvent.VK_P);
		tax_IDPanel1 = new JPanel(new BorderLayout());
		tax_IDPanel1.add(gmsTextField, BorderLayout.NORTH);
		tax_IDPanel1.add(gmsLabel, BorderLayout.SOUTH);
		gmsPanel.add(gmsButton, BorderLayout.WEST);
		gmsPanel.add(tax_IDPanel1, BorderLayout.CENTER);

		crPanel = new JPanel(new BorderLayout());
		crButton = new JButton("Customer Report");
		crTextField = new JTextField();
		crLabel = new JLabel("Tax ID");
		crLabel.setDisplayedMnemonic(KeyEvent.VK_P);
		tax_IDPanel2 = new JPanel(new BorderLayout());
		tax_IDPanel2.add(crTextField, BorderLayout.NORTH);
		tax_IDPanel2.add(crLabel, BorderLayout.SOUTH);
		crPanel.add(crButton, BorderLayout.WEST);
		crPanel.add(tax_IDPanel2, BorderLayout.CENTER);

		topPanel = new JPanel(new BorderLayout());
		topPanel.add(gmsPanel, BorderLayout.NORTH);
		topPanel.add(crPanel, BorderLayout.SOUTH);


		onePanel = new JPanel(new BorderLayout());
		addInterest = new JButton("Add Interest");
		generateDTER = new JButton("Generate DTER");
		onePanel.add(addInterest, BorderLayout.WEST);
		onePanel.add(generateDTER, BorderLayout.EAST);

		twoPanel = new JPanel(new BorderLayout());
		listActiveCustomers = new JButton("List Active Customers");
		deleteTransactions = new JButton("Delete Transactions");
		twoPanel.add(listActiveCustomers, BorderLayout.WEST);
		twoPanel.add(deleteTransactions, BorderLayout.EAST);
		
		midPanel = new JPanel(new BorderLayout());
		midPanel.add(onePanel, BorderLayout.NORTH);
		midPanel.add(twoPanel, BorderLayout. SOUTH);

		halfPanel = new JPanel(new BorderLayout());
		halfPanel.add(topPanel, BorderLayout.NORTH);
		halfPanel.add(midPanel, BorderLayout.SOUTH);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		scroller = new JScrollPane(textArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		

		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(halfPanel, BorderLayout.NORTH);
		mainPanel.add(textArea, BorderLayout.CENTER);

		content.add(mainPanel, BorderLayout.CENTER);

		MyHandler handler = new MyHandler();
		gmsButton.addActionListener(handler);
		crButton.addActionListener(handler);
		addInterest.addActionListener(handler);
		generateDTER.addActionListener(handler);
		listActiveCustomers.addActionListener(handler);
		deleteTransactions.addActionListener(handler);


		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 800);
		frame.setVisible(true);
		frame.setResizable(false);

	}

	private class MyHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == gmsButton){
				textArea.append("clicked \n");

			}
		}
	}


}

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.plaf.FontUIResource;



public class GUI extends JFrame {
	private JButton deposit;
	private JButton withdrawl;
	private JButton buy;
	private JButton sell;
	private JButton showBalance;
	private JButton transactionHistory;
	private JButton viewActorProfile;
	private JButton movieInfo;
	private JButton topMovies;
	private JButton movieReviews;

	private JTextField depositField;
	private JTextField withdrawlField;
	private JTextField buyField1;
	private JTextField buyField2;
	private JTextField sellField1;
	private JTextField sellField2;
	private JTextField startDate;
	private JTextField endDate;
	private JTextField actorProfile;
	private JTextField movieInfoField;
	private JTextField movieReviewsField;
	private JTextArea infoArea;

	private JLabel buyStock;
	private JLabel buyAmt;
	private JLabel sellStock;
	private JLabel sellAmt;
	private JLabel startDateLabel;
	private JLabel endDateLabel;
	private JLabel actorProfileLabel;
	private JLabel movieInfoLabel;
	private JLabel movieReviewsLabel;

	JScrollPane scroller;

	public GUI() { 
	super("Trader Interface"); // superclass constructor sets the title
	setLayout(null);

	FontUIResource fbold = new FontUIResource(Font.SANS_SERIF,Font.BOLD,14);
	FontUIResource fplain = new FontUIResource(Font.SANS_SERIF,Font.PLAIN,16);
	UIManager.put("Label.font", fbold);
	UIManager.put("Button.font", fbold);
	UIManager.put("TextField.font", fplain);

	deposit = new JButton("Deposit");
	deposit.setBounds(20,20,150,40);
	depositField = new JTextField("",10); // 10 wide, and initially empty
	depositField.setBounds(180,20,150,30);

	withdrawl = new JButton("Withdrawl");
	withdrawl.setBounds(350,20,150,40);
	withdrawlField = new JTextField("",10); // 10 wide, and initially empty
	withdrawlField.setBounds(510,20,150,30);

	buy = new JButton("Buy");
	buy.setBounds(20,70,150,40);
	buyField1 = new JTextField("",10); // 10 wide, and initially empty
	buyField1.setBounds(180,70,75,30);
	buyStock = new JLabel("Stock ID",JLabel.LEFT);
	buyStock.setBounds(185,100,150,20);
	buyField2 = new JTextField("",10); // 10 wide, and initially empty
	buyField2.setBounds(260,70,75,30);
	buyAmt = new JLabel("Amount",JLabel.LEFT);
	buyAmt.setBounds(270,100,150,20);

	sell = new JButton("Sell");
	sell.setBounds(350,70,150,40);
	sellField1 = new JTextField("",10); // 10 wide, and initially empty
	sellField1.setBounds(510,70,75,30);
	sellStock = new JLabel("Stock ID",JLabel.LEFT);
	sellStock.setBounds(515,100,150,20);
	sellField2 = new JTextField("",10); // 10 wide, and initially empty
	sellField2.setBounds(590,70,75,30);
	sellAmt = new JLabel("Amount",JLabel.LEFT);
	sellAmt.setBounds(600,100,150,20);
	
	topMovies = new JButton("Top Movies");
	topMovies.setBounds(20,130,150,40);
	startDate = new JTextField("",10); // 10 wide, and initially empty
	startDate.setBounds(180,130,75,30);
	startDateLabel = new JLabel("Start",JLabel.LEFT);
	startDateLabel.setBounds(185,160,150,20);
	endDate = new JTextField("",10); // 10 wide, and initially empty
	endDate.setBounds(260,130,75,30);
	endDateLabel = new JLabel("End",JLabel.LEFT);
	endDateLabel.setBounds(270,160,150,20);

	viewActorProfile = new JButton("View Actor Profile");
	viewActorProfile.setBounds(350,130,150,40);
	actorProfile = new JTextField("",10); // 10 wide, and initially empty
	actorProfile.setBounds(510,130,150,30);
	actorProfileLabel = new JLabel("Stock ID",JLabel.LEFT);
	actorProfileLabel.setBounds(515,160,150,20);

	movieInfo = new JButton("Movie Info");
	movieInfo.setBounds(20,190,150,40);
	movieInfoField = new JTextField("",10); // 10 wide, and initially empty
	movieInfoField.setBounds(180,190,150,30);
	movieInfoLabel = new JLabel("Movie Title",JLabel.LEFT);
	movieInfoLabel.setBounds(185,220,150,20);


	movieReviews = new JButton("Movie Review");
	movieReviews.setBounds(350,190,150,40);
	movieReviewsField = new JTextField("",10); // 10 wide, and initially empty
	movieReviewsField.setBounds(510,190,150,30);
	movieReviewsLabel = new JLabel("Movie Title",JLabel.LEFT);
	movieReviewsLabel.setBounds(515,220,150,20);

	transactionHistory = new JButton("Transaction History");
	transactionHistory.setBounds(100,250,200,40);

	showBalance = new JButton("Show Balance");
	showBalance.setBounds(420,250,200,40);

	infoArea = new JTextArea();
	infoArea.setLineWrap(true);
	infoArea.setEditable(false);
	scroller = new JScrollPane(infoArea);
	scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scroller.setBounds(20,325,640,440);
		
	// add everything to the panel
	add(deposit);add(withdrawl);add(buy);add(sell);
	add(depositField);add(withdrawlField);add(buyField1); add(buyField2); add(sellField1); add(sellField2);
	add(buyStock);add(buyAmt);add(sellStock);add(sellAmt); add(topMovies); add(startDate); add(endDate);
	add(startDateLabel); add(endDateLabel); add(viewActorProfile); add(actorProfile); add(actorProfileLabel);
	add(movieInfo); add(movieInfoField); add(movieInfoLabel); add(movieReviews); add(movieReviewsField); add(movieReviewsLabel);
	add(transactionHistory); add(showBalance); add(scroller);

	// The only thing we want to wait for is a click on the button
	MyHandler handler = new MyHandler();
		deposit.addActionListener(handler);

    } // MyJFrame

    // inner class
    private class MyHandler implements ActionListener {
    	public void actionPerformed(ActionEvent event) {
    		if (event.getSource() == deposit){
    			infoArea.append("BUTTON CLICKED\n");
    		}
	} // actionPerformed
	
    }// innerclass MyHandler
} // outerclass MyJFrame
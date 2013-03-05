import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;



public class MyJFrame extends JFrame {
	private JLabel minUCLabel;
	private JLabel maxUCLabel;
	private JLabel minDigitsLabel;
	private JLabel maxDigitsLabel;
	private JLabel minSpecialLabel;
	private JLabel maxSpecialLabel;
	private JLabel specialCharsAllowedLabel;
	private JLabel lengthLabel;
	private JButton generateButton;

	RandomPasswordGenerator generator = new RandomPasswordGenerator();
	private JTextField minUCTextField;
	private JTextField maxUCTextField;
	private JTextField minDigitsTextField;
	private JTextField maxDigitsTextField;
	private JTextField minSpecialTextField;
	private JTextField maxSpecialTextField;
	private JTextField specialCharsAllowedTextField;
	private JTextField lengthTextField;
	private JTextField resultTextField;
	public MyJFrame() { 
	super("Password Generator"); // superclass constructor sets the title
	setLayout(null);
	FontUIResource fbold = new FontUIResource(Font.SANS_SERIF,Font.BOLD,18);
	FontUIResource fplain = new FontUIResource(Font.SANS_SERIF,Font.PLAIN,20);
	UIManager.put("Label.font", fbold);
	UIManager.put("Button.font", fbold);
	UIManager.put("TextField.font", fplain);
	minUCLabel = new JLabel("Min Upper Case",JLabel.CENTER);
	minUCLabel.setBounds(20,30,150,20);
	minUCTextField = new JTextField("0",10); // 10 wide, and initially empty
	minUCTextField.setBounds(180,20,150,40);

	maxUCLabel = new JLabel("Max Upper Case",JLabel.CENTER);
	maxUCLabel.setBounds(350,30,150,20);
	maxUCTextField = new JTextField("10",10); // 10 wide, and initially empty
	maxUCTextField.setBounds(510,20,150,40);

	minDigitsLabel = new JLabel("Min Digits",JLabel.CENTER);
	minDigitsLabel.setBounds(20,80,150,20);
	minDigitsTextField = new JTextField("0",10); // 10 wide, and initially empty
	minDigitsTextField.setBounds(180,70,150,40);

	maxDigitsLabel = new JLabel("Max Digits",JLabel.CENTER);
	maxDigitsLabel.setBounds(350,80,150,20);
	maxDigitsTextField = new JTextField("10",10); // 10 wide, and initially empty
	maxDigitsTextField.setBounds(510,70,150,40);

	minSpecialLabel = new JLabel("Min Special",JLabel.CENTER);
	minSpecialLabel.setBounds(20,130,150,20);
	minSpecialTextField = new JTextField("0",10); // 10 wide, and initially empty
	minSpecialTextField.setBounds(180,120,150,40);

	maxSpecialLabel = new JLabel("Max Special",JLabel.CENTER);
	maxSpecialLabel.setBounds(350,130,150,20);
	maxSpecialTextField = new JTextField("10",10); // 10 wide, and initially empty
	maxSpecialTextField.setBounds(510,120,150,40);

	specialCharsAllowedLabel = new JLabel("Special Chars",JLabel.CENTER);
	specialCharsAllowedLabel.setBounds(0,180,200,20);
	specialCharsAllowedTextField = new JTextField(10); // 10 wide, and initially empty
	specialCharsAllowedTextField.setBounds(180,170,150,40);

	lengthLabel = new JLabel("Length",JLabel.CENTER);
	lengthLabel.setBounds(350,180,150,20);
	lengthTextField = new JTextField("6",10); // 10 wide, and initially empty
	lengthTextField.setBounds(510,170,150,40);
	generateButton = new JButton("Generate");
	generateButton.setBounds(30,275,125,65);
	resultTextField = new JTextField(50); // 50 wide, and initially empty
	resultTextField.setEditable(false); // not an editable field
	resultTextField.setBounds(180,275,480,65);
	// add everything to the panel
	// note how the code is formatted to resemble the grid layout!
	add(minUCLabel);add(minUCTextField);add(maxUCLabel);add(maxUCTextField);
	add(minDigitsLabel);add(minDigitsTextField);add(maxDigitsLabel); add(maxDigitsTextField);
	add(minSpecialLabel); add(minSpecialTextField);add(maxSpecialLabel);add(maxSpecialTextField);
	add(specialCharsAllowedLabel);add(specialCharsAllowedTextField);add(lengthLabel);add(lengthTextField);
	add(generateButton); add(resultTextField);
	// The only thing we want to wait for is a click on the button
	MyHandler handler = new MyHandler();
	generateButton.addActionListener(handler);
    } // MyJFrame

    // inner class
    private class MyHandler implements ActionListener {
    	public void actionPerformed(ActionEvent event) {
    		if (event.getSource() == generateButton){
    			generator.setSpecialAllowed(specialCharsAllowedTextField.getText());
    			generator.setMinUC(getIntValue(minUCTextField));
    			generator.setMaxUC(getIntValue(maxUCTextField));
    			generator.setMinDigits(getIntValue(minDigitsTextField));
    			generator.setMaxDigits(getIntValue(maxDigitsTextField));
    			generator.setMinSpecial(getIntValue(minSpecialTextField));
    			generator.setMaxSpecial(getIntValue(maxSpecialTextField));

    			String result = generator.generate(getIntValue(lengthTextField));
    			resultTextField.setText(result);

    			minUCTextField.setText(generator.getMinUC()+"");
    			maxUCTextField.setText(generator.getMaxUC()+"");
    			minDigitsTextField.setText(generator.getMinDigits()+"");
    			maxDigitsTextField.setText(generator.getMaxDigits()+"");
    			minSpecialTextField.setText(generator.getMinSpecial()+"");
    			maxSpecialTextField.setText(generator.getMaxSpecial()+"");
    			specialCharsAllowedTextField.setText(generator.getSpecialAllowed());
    			lengthTextField.setText(generator.getLength()+"");
    		}
	} // actionPerformed
	public int getIntValue(JTextField jtf) {
		String currValue = jtf.getText();
		int value = 0;
		try {
			value = Integer.parseInt(currValue);
		} catch (NumberFormatException nfe) {
			jtf.setText("0");
			value = 0;
		}
		return value;
	}
    }// innerclass MyHandler
} // outerclass MyJFrame
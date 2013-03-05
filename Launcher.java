import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.*;
import javax.swing.*;

public class Launcher extends JFrame {

	JTextField userTextField;
	JPasswordField passTextField;
	JFrame frame;
	Container content;
	JButton button;
	JPanel userPanel;
	JLabel userLabel;
	JPanel passPanel;
	JLabel passLabel;
	JPanel panel;

	public Launcher(){

		String title = "Login";
		frame = new JFrame(title);
		content = frame.getContentPane();

		button = new JButton("Login");
		userPanel = new JPanel(new BorderLayout());
		userLabel = new JLabel("Username: ");
		userLabel.setDisplayedMnemonic(KeyEvent.VK_U);
		userTextField = new JTextField();
		userLabel.setLabelFor(userTextField);
		userPanel.add(userLabel, BorderLayout.WEST);
		userPanel.add(userTextField, BorderLayout.CENTER);

		passPanel = new JPanel(new BorderLayout());
		passLabel = new JLabel("Password: ");
		passLabel.setDisplayedMnemonic(KeyEvent.VK_P);
		passTextField = new JPasswordField();
		passLabel.setLabelFor(passTextField);
		passPanel.add(passLabel, BorderLayout.WEST);
		passPanel.add(passTextField, BorderLayout.CENTER);

		panel = new JPanel(new BorderLayout());
		panel.add(userPanel, BorderLayout.NORTH);
		panel.add(passPanel, BorderLayout.SOUTH);
		panel.add(button, BorderLayout.EAST);
		content.add(panel, BorderLayout.NORTH);

		// The only thing we want to wait for is a click on the button
		MyHandler handler = new MyHandler();
		button.addActionListener(handler);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(250, 150);
		frame.setVisible(true);
		frame.setResizable(false);
    } // MyJFrame

    // inner class
    private class MyHandler implements ActionListener {
    	public void actionPerformed(ActionEvent event) {
    		if (event.getSource() == button){
    			String name = userTextField.getText();
    			char[] password = passTextField.getPassword();
    			String passwordString = password.toString();
    			if(name.equals("test")){
    				GUI gui = new GUI();
    				gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    				gui.setSize(700,800);
    				gui.setVisible(true);
    				gui.setResizable(false);
    				frame.dispose();
     			}
    		}
	} // actionPerformed
}
}
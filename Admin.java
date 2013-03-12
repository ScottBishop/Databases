import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Admin extends JFrame {

	DB db;
	int id;
	JFrame frame;
	Container content;
	JPanel mainPanel;



	public Admin(DB mydb, int myid) {
		frame = new JFrame("Admin");
		content = frame.getContentPane();

		db = mydb;
		id = myid;




		mainPanel = new JPanel(new BorderLayout());

		content.add(mainPanel, BorderLayout.CENTER);

		MyHandler handler = new MyHandler();
		//done.addActionListener(handler);


		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 800);
		frame.setVisible(true);
		frame.setResizable(false);

	}

	private class MyHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			// if (event.getSource() == done){

			// }
		}
	}


}

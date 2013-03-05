import javax.swing.JFrame;

public class Launcher {
	public static void main(String[] args) {
		GUI mjf = new GUI();
		mjf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mjf.setSize(700,800);
		mjf.setVisible(true);
		mjf.setResizable(false);
	}
}
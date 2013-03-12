import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class PasswordFieldSample {
  public static void main(String args[]) {
    String title = "Password Example";
    JFrame frame = new JFrame(title);
    Container content = frame.getContentPane();

    JCheckBox isAdmin = new JCheckBox();

    JPanel userPanel = new JPanel(new BorderLayout());
    JLabel userLabel = new JLabel("Username: ");
    userLabel.setDisplayedMnemonic(KeyEvent.VK_U);
    JTextField userTextField = new JTextField();
    userLabel.setLabelFor(userTextField);
    userPanel.add(userLabel, BorderLayout.WEST);
    userPanel.add(userTextField, BorderLayout.CENTER);

    JPanel passPanel = new JPanel(new BorderLayout());
    JLabel passLabel = new JLabel("Password: ");
    passLabel.setDisplayedMnemonic(KeyEvent.VK_P);
    JPasswordField passTextField = new JPasswordField();
    passLabel.setLabelFor(passTextField);
    passPanel.add(passLabel, BorderLayout.WEST);
    passPanel.add(passTextField, BorderLayout.CENTER);

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(userPanel, BorderLayout.NORTH);
    panel.add(passPanel, BorderLayout.SOUTH);
    content.add(panel, BorderLayout.NORTH);

    frame.setSize(250, 150);
    frame.setVisible(true);
  }
}
package UserTest;

import javax.swing.*;

/**
 * Created by Jojo on 5/12/2016.
 */
public class JOptionPaneMultiple {
    private JTextField emailField = new JTextField(15);
    private JPasswordField passwordField = new JPasswordField(15);
    static public String email;
    static public String password;

    public JOptionPaneMultiple() {


        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Email:"));
        myPanel.add(emailField);
        emailField.setText("email@email.com");
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Password:"));
        myPanel.add(passwordField);


        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please log in", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            email = emailField.getText();
            password = passwordField.getText();
        }
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}

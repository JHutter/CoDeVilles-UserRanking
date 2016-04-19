package UserCreation;

import ContainerClasses.UserAccount;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Legoman60 on 4/19/2016.
 */
public class UserAccountSetup {
    private JTextField passField2;
    private JButton finishButton;
    private JPanel rootPanel;
    private JTextField passField1;
    private JTextField nameField;
    private JTextField emailField;
    private JButton cancelButton;
    private UserAccount account;


    public UserAccountSetup() {
        rootPanel.setPreferredSize(new Dimension(500,350));
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().equals("")) {
                    if (!passField1.getText().equals("") && passField1.getText().equals(passField2.getText())) {
                        JOptionPane.showMessageDialog(rootPanel, "Attempting to create account for " + nameField.getText() + "\n email: " + account.getEmail() + "\n password: " + account.getPassword());
                        account = new UserAccount(emailField.getText(), nameField.getText(), passField1.getText());
                        //call database manager to validate unique email
                        JOptionPane.showMessageDialog(rootPanel, "Created account for " + account.getName() + "\n email: " + account.getEmail() + "\n password: " + account.getPassword());
                    } else {
                        JOptionPane.showMessageDialog(rootPanel, "Invalid Password. Both passwords must be the same (and not blank).");
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPanel, "Name can not be blank.");
                }
            }
        });
    }

    public JPanel getRootPanel(){return rootPanel;}
}

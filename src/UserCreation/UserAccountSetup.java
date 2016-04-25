package UserCreation;

import ContainerClasses.UserAccount;
import SharedFunctions.DatabaseManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * This is the form class for user account setup.
 * This form gathers information for anew user account before sending it off to a database
 * The database automatically checks to ensure that an e-mail address is unique
 * CIS 234A Dougherty
 * Date Created: 4/19/2016
 *
 *  @author  Zack
 *  @version  2016.4.24
 *
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
    private DatabaseManager databaseManager;


    /**
     * Default Constructor
     */
    public UserAccountSetup() {
        //Set the size of the panel to agreed value
        rootPanel.setPreferredSize(new Dimension(500,350));

        //instance the database manager
        databaseManager = new DatabaseManager();

        //on click listener for finish button
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //make sure email field isn't blank
                if (!emailField.getText().equals("")) {
                    //make sure name field isn't blank
                    if (!nameField.getText().equals("")) {
                        //make sure password field isn't blank and both password fields match
                        if (!passField1.getText().equals("") && passField1.getText().equals(passField2.getText())) {

                            //all tests passed, all fields have valid values. Create account object.
                            JOptionPane.showMessageDialog(rootPanel, "Attempting to create account for " + nameField.getText() + "\n email: " + emailField.getText() + "\n password: " + passField1.getText());
                            account = new UserAccount(emailField.getText(), nameField.getText(), passField1.getText());

                            //call database manager and attempt to insert account. If email is not unique then database will error.
                            if (databaseManager.insertUserAccount(account)){
                                JOptionPane.showMessageDialog(rootPanel, "Created account for " + account.getName() + "\n email: " + account.getEmail() + "\n password: " + account.getPassword());
                            }
                        } else {
                            //show message on invalid password
                            JOptionPane.showMessageDialog(rootPanel, "Invalid Password. Both passwords must be the same (and not blank).");
                        }
                    } else {
                        //show message on blank name
                        JOptionPane.showMessageDialog(rootPanel, "Name can not be blank.");
                    }
                } else {
                    //show message on blank email
                    JOptionPane.showMessageDialog(rootPanel, "Email can not be blank.");
                }
            }
        });

        //on click listener for cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);//exit on cancel
            }
        });
    }

    /**
     * @return rootPanel the root panel holding all other content
     */
    public JPanel getRootPanel(){return rootPanel;}
}

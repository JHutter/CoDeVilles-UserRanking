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
 *  @version  2016.5.16
 *
 *  2016.4.29
 *      reduced number of pop-ups generated
 *
 *  2016.5.9
 *      error messages now appear in labels instead of in text boxes
 *      minor clean up
 *
 *  2016.5.16
 *      refactored GUI input data validation to use individual methods
 *      added emailField.requestFocus() call at form open
 *      added .requestFocus() calls to input validation methods
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
    private JLabel errorLabel;
    private JLabel errorFlagLabel;
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
                //check that each field has valid data
                if (isValidEmail() && isValidName() && isValidPassword() && IsValidPasswordMatch()){

                    //all tests passed, all fields have valid values. Create account object.
                    account = new UserAccount(emailField.getText(), nameField.getText(), passField1.getText());

                    //call database manager and attempt to insert account. If email is not unique then database will error.
                    if (databaseManager.insertUserAccount(account)){
                        //show confirmation message if insert succeeds
                        JOptionPane.showMessageDialog(rootPanel, "Created account for " + account.getName() +
                                "\nemail: " + account.getEmail() +
                                "\npassword: " + account.getPassword());
                        errorFlagLabel.setText("");
                        errorLabel.setText("");
                    } else {
                        //show error message if insert fails
                        JOptionPane.showMessageDialog(rootPanel, "Unable to create account. Please ensure you are using a unique email\n" +
                                "and check your internet connection");
                        errorFlagLabel.setText("");
                        errorLabel.setText("");
                    }
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

        //set focus on the email field at form start
        emailField.requestFocus();
    }

    /**
     * @return rootPanel the root panel holding all other content
     */
    public JPanel getRootPanel(){return rootPanel;}

    /**
     * Checks to see if the email field is blank. Returns pass/fail
     * @return boolean indicating test pass/fail
     */
    public boolean isValidEmail(){
        //make sure email field isn't blank
        if (!emailField.getText().equals("")) {
            return true;
        } else {
            //show message on blank email
            errorFlagLabel.setText("!Invalid Email!");
            errorLabel.setText("!Email can't be blank!");

            //set focus on the email field
            emailField.requestFocus();
            return false;
        }
    }

    /**
     * Checks to see if the name field is blank. Returns pass/fail
     * @return boolean indicating test pass/fail
     */
    public boolean isValidName(){
        //make sure name field isn't blank
        if (!nameField.getText().equals("")) {
            return true;
        } else {
            //show message on blank name
            errorFlagLabel.setText("!Invalid Name!");
            errorLabel.setText("!Name can't be blank!");

            //set focus on the name field
            nameField.requestFocus();
            return false;
        }
    }

    /**
     * Checks to see if the password field is blank. Returns pass/fail
     * @return boolean indicating test pass/fail
     */
    public boolean isValidPassword(){
        //make sure password field isn't blank
        if (!passField1.getText().equals("")) {
            return true;
        } else {
            //show message on invalid password
            errorFlagLabel.setText("!Invalid Password!");
            errorLabel.setText("!Pass can't be blank!");

            //set focus on the first password field
            passField1.requestFocus();
            return false;
        }
    }

    /**
     * Checks to see if the passwords in both password fields match. Returns pass/fail
     * @return boolean indicating test pass/fail
     */
    public boolean IsValidPasswordMatch(){
        //make sure both password fields match
        if (passField1.getText().equals(passField2.getText())) {
            return true;
        } else {
            //show message on invalid password
            errorFlagLabel.setText("!Invalid Password!");
            errorLabel.setText("!Passwords must match!");

            //set focus on the second password field
            passField2.requestFocus();
            return false;
        }
    }
}

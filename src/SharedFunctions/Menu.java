package SharedFunctions;

import ResultReporting.TestResultReporting;
import AdminSetup.MainAdminSetup;
import UserCreation.TestUserCreation;
import UserTest.TestUserTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * This class models the Main Menu for the project.
 * It is a simple series of buttons
 * When clicked it opens a new window of the respective form
 * The new form is run in a new process
 * Using System.exit() in the new form returns to the main menu
 *
 * Creation Date: 4/27/2016
 *
 * @author Zack
 * @version 2016.4.29
 *
 * 2016.4.29
 *      removed debug pop-ups that appeared after closing a menu-launched form
 */
public class Menu {
    int choice = 0; //The last item selected, not used currently
    private JButton adminSetupButton;
    private JButton userSetupButton;
    private JButton takeTestButton;
    private JButton resultsButton;
    private JPanel rootPanel;
    private JButton exitButton;

    public Menu() {

        //Set the size of the panel to standard value
        rootPanel.setPreferredSize(new Dimension(500, 350));

        //listener for button that starts Admin Setup
        adminSetupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 1;
                try {
                    int status = JavaProcess.exec(MainAdminSetup.class);
                } catch (IOException err) {
                    err.printStackTrace();
                } catch (InterruptedException err) {
                    err.printStackTrace();
                }
            }
        });

        //listener for button that starts User Account Creation
        userSetupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 2;
                try {
                    int status = JavaProcess.exec(TestUserCreation.class);
                } catch (IOException err) {
                    err.printStackTrace();
                } catch (InterruptedException err) {
                    err.printStackTrace();
                }
            }
        });

        //listener for button that starts Test Taking
        takeTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 3;
                try {
                    int status = JavaProcess.exec(TestUserTest.class);
                } catch (IOException err) {
                    err.printStackTrace();
                } catch (InterruptedException err) {
                    err.printStackTrace();
                }
            }
        });

        //listener for button that starts Result Reporting
        resultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 4;
                try {
                    int status = JavaProcess.exec(TestResultReporting.class);
                } catch (IOException err) {
                    err.printStackTrace();
                } catch (InterruptedException err) {
                    err.printStackTrace();
                }
            }
        });

        //listener for exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * @return rootPanel the root panel holding all other content
     */
    public JPanel getRootPanel(){return rootPanel;}
}

package AdminSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The adminSetupForm class is made to control admin setup GUI screen.
 * It add/delete items for test from user and update database.
 * Items should be more than two. After any user takes test, item can not be changed.
 * @author   Jinsook Lee
 * @version  04/18/2016
 */

public class AdminSetupForm {
    private JTextField itemTextField;
    private JPanel rootPanel;
    private JList itemList;
    private JButton addButton;
    private JButton deleteButton;
    private JButton finishButton;
    private JButton cancelButton;
    private JLabel takenLabel;
    private boolean isMoreThanTwo = false;
    private boolean isTestTaken = false;

    public AdminSetupForm(){
        rootPanel.setPreferredSize(new Dimension(500,350));
        takenLabel.setVisible(false);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(rootPanel, "Item is added successfully.");
            }
        });
        if (!isMoreThanTwo)     //If there are less than two items , finish button should be disabled
        {
            finishButton.setEnabled(false);
        }
        if (isTestTaken)        //After any user takes test, item can not be changed
        {
            addButton.setEnabled(false);
            deleteButton.setEnabled(false);
            takenLabel.setVisible(true);
        }
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(rootPanel, "Item is deleted successfully.");
            }
        });
        cancelButton.addActionListener(new ActionListener() {       //Cancel button clears the item input text field
            @Override
            public void actionPerformed(ActionEvent e) {
                itemTextField.setText("");
            }
        });
    }

    public JPanel getRootPanel(){
        return rootPanel;

    }
}

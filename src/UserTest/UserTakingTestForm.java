package UserTest;

import ContainerClasses.TestItem;
import ContainerClasses.TestResult;
import ContainerClasses.TestSession;
import ContainerClasses.UserAccount;
import SharedFunctions.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Description: Form for user taking test story
 *
 * Programmer: JoAnne Hutter
 * Date: 2016-05-03
 *
 * @author JHutter
 * @version 2016-05-10
 *
 * Modifications: updated testtaking logic, finish/next button enabled and disabled,
 * method to write to results
 *
 * 2016.5.10:
 *      Added dialog popup to prompt user login
 *      Updated next button and item buttons
 */
public class UserTakingTestForm {
    private JPanel rootPanel;
    private JButton itemAButton;
    private JButton itemBButton;
    private JButton noItemButton;
    private JButton finishButton;

    private Test test;
    private UserAccount user;
    private TestSession session;
    private DatabaseManager database;

    private JButton selectedItem;
    private JOptionPaneMultiple dialogBox;

    public UserTakingTestForm() {
        //database = new DatabaseManager();
        //String path = JOptionPane.showInputDialog(rootPanel, "Enter a path");
        dialogBox = new JOptionPaneMultiple();

        String email = dialogBox.getEmail();
        int userID = new DatabaseManager().getUserID(email);

        setup(userID);

        rootPanel.setPreferredSize(new Dimension(500,350));
        itemAButton.setText(test.getPairs().get(0).getItem1().getItemText());
        itemBButton.setText(test.getPairs().get(0).getItem2().getItemText());
        disableFinishButton();
        itemAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* TODO refactor this */
                toggleItemButtonColor(itemAButton, itemBButton, noItemButton);
                setSelectedItem(itemAButton);
                enableFinishButton();
            }
        });
        itemBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* TODO refactor this */
                toggleItemButtonColor(itemBButton, itemAButton, noItemButton);
                setSelectedItem(itemBButton);
                enableFinishButton();
            }
        });
        noItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* TODO refactor this */
                toggleItemButtonColor(noItemButton, itemBButton, itemAButton);
                setSelectedItem(noItemButton);
                enableFinishButton();
            }
        });

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* TODO refactor this */
                if (selectedItem != null) { /* don't increment turn if an option isn't selected*/
                    if (test.getCurrentTurn() < test.getTotalTurn()-1) {
                        int itemAID = test.getItemByText(itemAButton.getText()).getItemID();
                        int itemBID = test.getItemByText(itemBButton.getText()).getItemID();

                        if (selectedItem == itemAButton) {
                            test.recordResult(itemAID, itemBID, session.getSessionID(), 1);
                            test.recordResult(itemBID, itemAID, session.getSessionID(), -1);
                        }
                        else if (selectedItem == itemBButton) {
                            test.recordResult(itemAID, itemBID, session.getSessionID(), -1);
                            test.recordResult(itemBID, itemAID, session.getSessionID(), 1);
                        }
                        else {
                            test.recordResult(itemAID, itemBID, session.getSessionID(), 0);
                            test.recordResult(itemBID, itemAID, session.getSessionID(), 0);
                        }

                        test.incrementTurn();
                        selectedItem = null;
                        setNewItemPairs(test.getCurrentTurn());
                        disableFinishButton();
                    }
                    else {
                        itemAButton.setVisible(false);
                        itemBButton.setVisible(false);
                        noItemButton.setVisible(false);
                        finishButton.setText("Finish");
                        finishTest();
                    }
                    resetItemButtonColors(itemAButton, itemBButton, noItemButton);

                }
                return;
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void toggleItemButtonColor(JButton buttonClicked, JButton buttonOther, JButton buttonOtherOther) {
        // JButton().getBackground idea thanks to http://stackoverflow.com/questions/1358398/how-to-get-jbutton-default-background-color
        buttonClicked.setBackground(Color.lightGray);
        buttonOther.setBackground(new JButton().getBackground());
        buttonOtherOther.setBackground(new JButton().getBackground());
    }
    private void setSelectedItem(JButton button) {
        selectedItem = button;
    }

    private void setNewItemPairs(int nextTurn) {
        ItemPair nextPair = test.getNextPair(nextTurn);
        itemAButton.setText(nextPair.getItem1().getItemText());
        itemBButton.setText(nextPair.getItem2().getItemText());
    }

    private void resetItemButtonColors(JButton button1, JButton button2, JButton button3) {
        // JButton().getBackground idea thanks to http://stackoverflow.com/questions/1358398/how-to-get-jbutton-default-background-color
        button1.setBackground(new JButton().getBackground());
        button2.setBackground(new JButton().getBackground());
        button3.setBackground(new JButton().getBackground());
    }

    private void setup(int userID) {
        test = new Test(1);
        user = new UserAccount();
        user.setUserID(userID);
        session = new TestSession(test.getTestID(), user.getUserID(), true);
        database = new DatabaseManager();
        //database.insertSession(user.getUserID(), test.getTestID());
        session.setSessionID(database.getSessionID(user.getUserID(), test.getTestID()));
        //session.setSessionID(3);
        database.setIsActive(session.getSessionID(), user.getUserID(), test.getTestID());
        return;
    }

    private void finishTest() {
        // write results to database iteratively
        finishButton.setText("Test is complete.\nPlease close this window to finish.");
        test.writeResults();
    }

    private void enableFinishButton() {
        finishButton.setEnabled(true);
        return;
    }
    private void disableFinishButton() {
        finishButton.setEnabled(false);
        return;
    }
}



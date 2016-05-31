package UserTest;

import ContainerClasses.TestSession;
import ContainerClasses.UserAccount;
import SharedFunctions.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Description: Form for user taking test story
 *
 * Programmer: JoAnne Hutter
 * Created: 2016-05-03
 *
 * @author JHutter
 * @version 2016-05-24
 *
 * Modifications: updated testtaking logic, finish/next button enabled and disabled,
 * method to write to results
 *
 * 2016.5.10:
 *      Added dialog popup to prompt user login
 *      Updated next button and item buttons
 * 2016.5.24:
 *      Fixed problem with testID, problem with password
 *      Insert session fixed
 * 2016.5.31
 *      Added progress bar
 */
public class UserTakingTestForm {
    private JPanel rootPanel;
    private JButton itemAButton;
    private JButton itemBButton;
    private JButton noItemButton;
    private JButton finishButton;
    private JProgressBar progressBar;

    private Test test;
    private UserAccount user;
    private TestSession session;
    private DatabaseManager database;

    private JButton selectedItem;
    private JOptionPaneMultiple dialogBox;

    public UserTakingTestForm() {
        dialogBox = new JOptionPaneMultiple();
        String email = dialogBox.getEmail();
        String password = dialogBox.getPassword();
        int userID = new DatabaseManager().getUserID(email, password);
        setupProgressBar();

        setup(userID);
        //finishButton.setText(""+session.getSessionID());

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
                        progressBar.setVisible(false);
                        finishTest();
                    }
                    resetItemButtonColors(itemAButton, itemBButton, noItemButton);

                }
                updateProgressBar((int)test.getProgress());

                return;
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    /**
     * toggleItemButtonColor
     * mark which item/button is currently selected by making it light gray
     * all other buttons are set to default color
     * @param buttonClicked the selected button
     * @param buttonOther not the selected button
     * @param buttonOtherOther not the selected button
     */
    private void toggleItemButtonColor(JButton buttonClicked, JButton buttonOther, JButton buttonOtherOther) {
        // JButton().getBackground idea thanks to http://stackoverflow.com/questions/1358398/how-to-get-jbutton-default-background-color
        buttonClicked.setBackground(Color.lightGray);
        buttonOther.setBackground(new JButton().getBackground());
        buttonOtherOther.setBackground(new JButton().getBackground());
    }

    /**
     * setSelectedItem
     * setter to mark which item/button is currently selected
     * @param button
     */
    private void setSelectedItem(JButton button) {
        selectedItem = button;
    }

    /**
     * setNewItemPairs
     * for the next turn, get the item buttons
     * @param nextTurn the next turn in the test
     */
    private void setNewItemPairs(int nextTurn) {
        ItemPair nextPair = test.getNextPair(nextTurn);
        itemAButton.setText(nextPair.getItem1().getItemText());
        itemBButton.setText(nextPair.getItem2().getItemText());
    }

    /**
     * resultItemButtonColors
     * when the turn resets, change the buttons back to their default colors
     * @param button1
     * @param button2
     * @param button3
     */
    private void resetItemButtonColors(JButton button1, JButton button2, JButton button3) {
        // JButton().getBackground idea thanks to http://stackoverflow.com/questions/1358398/how-to-get-jbutton-default-background-color
        button1.setBackground(new JButton().getBackground());
        button2.setBackground(new JButton().getBackground());
        button3.setBackground(new JButton().getBackground());
    }

    /**
     * setup
     * set the test and test session up for a given user
     * @param userID
     */
    private void setup(int userID) {
        test = new Test(1);
        user = new UserAccount();
        user.setUserID(userID);
        session = new TestSession(test.getTestID(), user.getUserID(), true);
        database = new DatabaseManager();
        //database.insertSession(user.getUserID(), test.getTestID());

        //session.setSessionID(database.getSessionID(user.getUserID(), test.getTestID()));
        session.setSessionID(3);
        //database.setIsActive(session.getSessionID(), user.getUserID(), test.getTestID());
        return;
    }

    /**
     * finishTest
     * change button to indicate to user that the test is finished
     * and call test.writeResults to record results in the database
     */
    private void finishTest() {
        // write results to database iteratively
        finishButton.setText("<html>Test is complete.<br>Please close this window to finish.</html>");
        test.writeResults();
    }

    /**
     * enableFinishButton
     * mark the finishButton as enabled to
     * allow test taker to progress with the test
     */
    private void enableFinishButton() {
        finishButton.setEnabled(true);
        return;
    }

    /**
     * disableFinishButton
     * mark the finishButton as disabled to force test taker
     * to make a selection before continuing
     */
    private void disableFinishButton() {
        finishButton.setEnabled(false);
        return;
    }

    /**
     * updateProgressBar
     * update the progress bar based on test turn
     * @param value the int value (currentTurn/totalTurns)
     */
    public void updateProgressBar(int value){
        String progressString = value + "%";
        progressBar.setValue(value);
        progressBar.setString(progressString);
        return;
    }

    /**
     * setProgressBar
     * sets up progress bar (not indeterminate, 0 value, string painted)
     */
    public void setupProgressBar(){
        progressBar.setIndeterminate(false);
        progressBar.setStringPainted(true);
        updateProgressBar(0);
    }


}



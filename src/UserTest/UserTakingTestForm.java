package UserTest;

import ContainerClasses.TestSession;
import ContainerClasses.UserAccount;
import DaoClasses.DAOFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Description: Form for user taking test story
 *
 * Programmer: JHutter
 * Created: 2016-05-03
 *
 * @author JHutter
 * @version 2016-05-31
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
 *      Refactored set process into methods
 *      Added checks with dialog box + exit for invalid values
 *      Refactored to use DAO
 */
public class UserTakingTestForm {
    // fields
    private JPanel rootPanel;
    private JButton itemAButton;
    private JButton itemBButton;
    private JButton noItemButton;
    private JButton finishButton;
    private JProgressBar progressBar;
    private JButton selectedItem;
    private JOptionPaneMultiple dialogBox;
    private UserTest.Test test;
    private UserAccount user;
    private TestSession session;
    private float progress = 0;

    // constructor
    public UserTakingTestForm() {
        dialogBox = new JOptionPaneMultiple(retrieveTestNames());
        session = new TestSession();
        setupProgressBar();
        if (!signinUser()){
            JOptionPane.showMessageDialog(rootPanel, "Unable to sign in. Please try again.");
            System.exit(-1);
        }
        if (!setupTest()){
            JOptionPane.showMessageDialog(rootPanel, "Unable to load test. Please try again later.");
            System.exit(-1);
        }
        if (!setupSession()){
            JOptionPane.showMessageDialog(rootPanel, "Unable to start session. Please try again later.");
            System.exit(-1);
        }

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

        /*this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.out.println("Closed");
                e.getWindow().dispose();
            }
        });*/

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* TODO refactor this */
                if (selectedItem != null) { /* don't increment turn if an option isn't selected*/
                    if (test.getCurrentTurn() < test.getTotalTurn()-1) {
                        int itemAID = test.getItemByText(itemAButton.getText()).getItemID();
                        int itemBID = test.getItemByText(itemBButton.getText()).getItemID();
                        int questionNumber = test.getCurrentTurn();
                        int sessionID = session.getSessionID();

                        if (selectedItem == itemAButton) {
                            test.recordResult(questionNumber, itemBID, sessionID, -1);
                            test.recordResult(questionNumber, itemAID, sessionID, 1);
                        }
                        else if (selectedItem == itemBButton) {
                            test.recordResult(questionNumber, itemBID, sessionID, 1);
                            test.recordResult(questionNumber, itemAID, sessionID, -1);
                        }
                        else {
                            test.recordResult(questionNumber, itemBID, sessionID, 0);
                            test.recordResult(questionNumber, itemAID, sessionID, 0);
                        }

                        test.incrementTurn();
                        selectedItem = null;
                        disableFinishButton();
                        setNewItemPairs(test.getCurrentTurn());
                    }
                    else {
                        // code smell... refactor TODO
                        int itemAID = test.getItemByText(itemAButton.getText()).getItemID();
                        int itemBID = test.getItemByText(itemBButton.getText()).getItemID();
                        int questionNumber = test.getCurrentTurn();
                        int sessionID = session.getSessionID();

                        if (selectedItem == itemAButton) {
                            test.recordResult(questionNumber, itemBID, sessionID, -1);
                            test.recordResult(questionNumber, itemAID, sessionID, 1);
                        }
                        else if (selectedItem == itemBButton) {
                            test.recordResult(questionNumber, itemBID, sessionID, 1);
                            test.recordResult(questionNumber, itemAID, sessionID, -1);
                        }
                        else {
                            test.recordResult(questionNumber, itemBID, sessionID, 0);
                            test.recordResult(questionNumber, itemAID, sessionID, 0);
                        }

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
     * finishTest
     * change button to indicate to user that the test is finished
     * and call test.writeResults to record results in the database
     */
    private void finishTest() {
        finishButton.setText("<html>Test is complete.<br>Please close this window to finish.</html>");
        if (!test.writeResults()){// || test.getResults().size() == 0){
            JOptionPane.showMessageDialog(rootPanel, "Unable to record results. Please try again.");
            System.exit(-1);
        }
        else {
            session.setIsActive(false);
            DAOFactory.getTestSessionDAO().setIsActive(session);
        }
        JOptionPane.showMessageDialog(rootPanel, "Test will now exit.");
        System.exit(0);
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

    /**
     * signinUser
     * grabs user and password from dialog box, checks DB for matching user
     * @return Boolean for test success
     */
    private Boolean signinUser(){
        String email = dialogBox.getEmail();
        String password = dialogBox.getPassword();
        int userID = DAOFactory.getUserAccountDAO().getUserID(email, password);
        if (userID < 1){
            return false;
        }
        user = new UserAccount(userID, email, "", password);
        return true;
    }

    /**
     * retrieveTestNames
     * @return testNames arrayList of Strings
     */
    private ArrayList<ContainerClasses.Test> retrieveTestNames(){
        ArrayList<ContainerClasses.Test> tests = DAOFactory.getTestDAO().returnAllTests();
        return tests;
    }

    /**
     * setupTest
     * grab test name and ID from dialog box, assign them to test
     * @return Boolean for setup success
     */
    private Boolean setupTest(){
        String testName = dialogBox.getTestName();
        int testID = dialogBox.getTestID();
        if (testID < 1){
            return false;
        }
        test = new Test(testID, testName);
        return true;
    }

    /**
     * setupSession
     * use userID and testID to set up the session
     * @return Boolean for action success
     */
    private Boolean setupSession(){
        session = new TestSession(test.getTestID(), user.getUserID(), true);
        DAOFactory.getTestSessionDAO().insertSession(session.getUserID(), session.getTestID());
        session.setSessionID(DAOFactory.getTestSessionDAO().getSessionID(session.getUserID(), session.getTestID()));
        if (session.getSessionID() < 1) {
            return false;
        }
        return true;
    }


}



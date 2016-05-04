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
 * Date: 2016-04-19
 *
 * @author JHutter
 * @version 2016-04-27
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

    public UserTakingTestForm() {
        setup();
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
        /* TODO make the colors prettier */
        buttonClicked.setBackground(Color.lightGray);
        buttonOther.setBackground(new Color(232,232,232));
        buttonOtherOther.setBackground(new Color(232,232,232));
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
        button1.setBackground(new Color(232,232,232));
        button2.setBackground(new Color(232,232,232));
        button3.setBackground(new Color(232,232,232));
    }

    private void setup() {
        test = new Test(1);
        test.setTestID(1);
        user = new UserAccount();
        user.setUserID(1);
        session = new TestSession();
        session.setSessionID(1);
        database = new DatabaseManager();
        //test.setDBItems(database.getTestItems(test.getTestID()));
        //test.setDBItems(1);
        return;

    }

    private void finishTest() {
        // write results to database iteratively
        finishButton.setText("Test is complete.\nPlease close this window to finish.");
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



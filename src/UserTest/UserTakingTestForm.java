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

    private UserAccount user;
    private TestSession session;
    private Test test;
    //private DatabaseManager database;
    private JButton selectedItem;

    public UserTakingTestForm() {
        //setup();
        rootPanel.setPreferredSize(new Dimension(500,350));
        itemAButton.setText(test.getPairs().get(0).getItem1().getItemText());
        itemBButton.setText(test.getPairs().get(0).getItem2().getItemText());
        itemAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* TODO refactor this */
                toggleItemButtonColor(itemAButton, itemBButton, noItemButton);
                setSelectedItem(itemAButton);
            }
        });
        itemBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* TODO refactor this */
                toggleItemButtonColor(itemBButton, itemAButton, noItemButton);
                setSelectedItem(itemBButton);
            }
        });
        noItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* TODO refactor this */
                toggleItemButtonColor(noItemButton, itemBButton, itemAButton);
                setSelectedItem(noItemButton);
            }
        });

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* TODO refactor this */
                if (selectedItem != null) { /* don't increment turn if an option isn't selected*/
                    if (test.getCurrentTurn() < test.getTotalTurn()-1) {
                        test.incrementTurn();
                        selectedItem = null;
                        setNewItemPairs(test.getCurrentTurn());
                    }
                    else {
                        if (test.getCurrentTurn() == test.getTotalTurn()) {
                            finishButton.setText("Finish");
                        }
                    }
                    resetItemButtonColors(itemAButton, itemBButton, noItemButton);

                }
                /* TODO remove this */
                System.out.print("You clicked Next/Finish!\nTurn: " + test.getCurrentTurn() + "\n");
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

    private void setup(){
        // TODO these don't belong here
        //database = new DatabaseManager();
        user = new UserAccount();
        user.setUserID(1);
        test = new Test();
        test.setTestID(1);
        session = new TestSession();
        //session.setSessionID(database.getSessionID(user, test));
        session.setSessionID(1);
        //test.setDBItems(database.getTestItems(test.getTestID()));
        return;

    }
}



package UserTest;

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
    private Test test = new Test();
    private JButton selectedItem;

    public UserTakingTestForm() {
        rootPanel.setPreferredSize(new Dimension(500,350));
        itemAButton.setText(test.getPairs().get(0).getItem1());
        itemBButton.setText(test.getPairs().get(0).getItem2());
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
        itemAButton.setText(nextPair.getItem1());
        itemBButton.setText(nextPair.getItem2());
    }

    private void resetItemButtonColors(JButton button1, JButton button2, JButton button3) {
        button1.setBackground(new Color(232,232,232));
        button2.setBackground(new Color(232,232,232));
        button3.setBackground(new Color(232,232,232));
    }
}



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
 * @version sprint1-part1
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
        itemAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleItemButtonColor(itemAButton, itemBButton, noItemButton);
                setSelectedItem(itemAButton);
            }
        });
        itemBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleItemButtonColor(itemBButton, itemAButton, noItemButton);
                setSelectedItem(itemBButton);
            }
        });
        noItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleItemButtonColor(noItemButton, itemBButton, itemAButton);
                setSelectedItem(noItemButton);
            }
        });

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                test.incrementTurn();
                if (test.getCurrentTurn() == test.getTotalTurn()) {
                    finishButton.setText("Finish");
                }
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
}



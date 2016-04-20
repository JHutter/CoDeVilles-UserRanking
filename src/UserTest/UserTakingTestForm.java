package UserTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by JHutter on 4/19/2016.
 */
public class UserTakingTestForm {
    private JPanel rootPanel;
    private JButton itemAButton;
    private JButton itemBButton;
    private JButton noItemButton;
    private JButton nextButton;
    private JButton finishButton;
    private JTextArea userInstructions;

    public UserTakingTestForm() {
        rootPanel.setPreferredSize(new Dimension(500,350));
        itemAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("You clicked Item A!\n");
            }
        });
        itemBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("You clicked Item B!\n");
            }
        });
        noItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("You clicked \"Can't Decide\"!\n");
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("You clicked Next!\n");
            }
        });
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("You clicked Finish!\n");
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}



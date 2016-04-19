package UserCreation;

import javax.swing.*;

/**
 * This is the main class for driving the window creation.
 * CIS 234A Dougherty
 *
 *  Programmer(s): Zack
 *  Date: 4/19/2016.
 *
 */

public class TestUserCreation {

    public static void createAndShowGui() {
    JFrame frame = new JFrame("User Account Setup");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(new UserAccountSetup().getRootPanel());
    frame.pack();
    frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGui());
    }
}

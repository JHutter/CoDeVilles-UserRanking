package ResultReporting;

import javax.swing.*;

/**
 * This is the main class for driving the window creation for result reporting.
 * CIS 234A Dougherty
 *
 *  Programmer(s): Zack
 *  Date: 4/19/2016.
 *
 */

public class TestResultReporting {
    public static void createAndShowGui() {
        JFrame frame = new JFrame("Result Reporting");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ResultReporting().getRootPanel());
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGui());
    }
}

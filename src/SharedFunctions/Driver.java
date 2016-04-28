package SharedFunctions;

import javax.swing.*;

/**
 * This is the main driver class for the entire project.
 * Running this will launch the Main Menu
 * Creation Date: 4/27/2016
 *
 * @author Zack
 * @version 2016.4.27
 */
public class Driver {
    public static void createAndShowGui() {
        JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Menu().getRootPanel());
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGui());
    }
}



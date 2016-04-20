package AdminSetup;

/**
 * The MainAdminSetup class is made to launch the admin setup GUI screen
 * @author   Jinsook Lee
 * @version  04/18/2016
 */

import javax.swing.*;
public class MainAdminSetup {
    public static void createAndShowGui(){

        JFrame frame = new JFrame("Admin Setup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new AdminSetupForm().getRootPanel());
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGui());

    }
}

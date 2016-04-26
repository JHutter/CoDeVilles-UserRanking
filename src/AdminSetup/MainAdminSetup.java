package AdminSetup;

/**
 * The MainAdminSetup class is made to launch the admin setup GUI screen
 * Create Date 4/18/2016
 *
 * @author   Jinsook Lee
 * @version  4/24/2016
 */

import ContainerClasses.UserAccount;

import javax.swing.*;
import java.util.ArrayList;

public class MainAdminSetup {

    public static void createAndShowGui(){

        JFrame frame = new JFrame("Admin Setup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new AdminSetupForm().getRootPanel());
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGui());
    }

}

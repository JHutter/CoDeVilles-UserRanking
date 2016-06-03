package AdminSetup;

/**
 * The MainAdminSetup class is made to launch initial GUI screen of admin setup part
 * Create Date 4/18/2016
 *
 * @author   Jinsook Lee
 * @version  5/24/2016
 *
 * Modification
 * 5/24/2016
 * - Update to launch TestsetChoiceForm GUI screen
 */
import javax.swing.*;


public class MainAdminSetup {

    public static void createAndShowGui(){
        JFrame frame = new JFrame("Admin Setup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new TestsetChoiceForm().getRootPanel());
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGui());
    }



}

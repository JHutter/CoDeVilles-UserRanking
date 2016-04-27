package UserTest;


import javax.swing.*;

/**
 * Description: Class to manage GUI for user taking test
 *
 * Programmer: JoAnne Hutter
 * Date: 2016-04-19
 *
 * @author JHutter
 * @version sprint1-part1
 */
public class TestUserTest {
    /* TODO use session (having user + test) instead of test itself */
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGui());
    }

    public static void createAndShowGui() {
        JFrame frame = new JFrame("User Taking Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new UserTakingTestForm().getRootPanel());
        frame.pack();
        frame.setVisible(true);
    }

}

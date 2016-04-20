package UserTest;


import javax.swing.*;

public class TestUserTest {
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

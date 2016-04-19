package UserCreation;


import javax.swing.*;

public class TestUserCreation {public static void createAndShowGui() {
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

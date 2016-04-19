package ResultReporting;


import javax.swing.*;

public class TestResultReporting {public static void createAndShowGui() {
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

package UserCreation;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Legoman60 on 4/19/2016.
 */
public class UserAccountSetup {
    private JTextField passField2;
    private JButton finishButton;
    private JPanel rootPanel;
    private JTextField passField1;
    private JTextField nameField;
    private JTextField emailField;
    private JButton cancelButton;

    public UserAccountSetup() {
        rootPanel.setPreferredSize(new Dimension(500,350));
    }

    public JPanel getRootPanel(){return rootPanel;}
}

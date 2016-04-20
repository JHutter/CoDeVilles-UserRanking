package AdminSetup;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Chulwoo on 4/18/2016.
 */
public class AdminSetupForm {
    private JTextField textField1;
    private JPanel panel11;
    private JList list1;
    private JButton addButton;
    private JButton deleteButton;
    private JButton finishButton;
    private JButton cancelButton;

    public AdminSetupForm(){
        panel11.setPreferredSize(new Dimension(500,300));
    }

    public JPanel getRootPanel(){
        return panel11;

    }
}

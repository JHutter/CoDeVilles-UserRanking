package UserTest;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Jojo on 5/12/2016.
 */
public class JOptionPaneMultiple {
    private JTextField emailField = new JTextField(15);
    private JPasswordField passwordField = new JPasswordField(15);
    private JComboBox<String> testChooser;
    private JPanel topPanel;
    private JPanel bottomPanel;
    static public String email;
    static public String password;
    static public String testName;
    static public int testID;
    static public ArrayList<ContainerClasses.Test> tests;

    public JOptionPaneMultiple() {
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Email:"));
        myPanel.add(emailField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Password:"));
        myPanel.add(passwordField);
        emailField.setText("email@email.com");

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please log in", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            email = emailField.getText();
            password = passwordField.getText();
        }
    }

    public JOptionPaneMultiple(ArrayList<ContainerClasses.Test> testNames) {
        JPanel myPanel = new JPanel();
        tests = testNames;
        String[] strings = convertTestArrayToStringArray().toArray(new String[convertTestArrayToStringArray().size()]);
        testChooser = new JComboBox<String>(strings);

        topPanel = new JPanel();
        topPanel.add(new JLabel("Test:"));
        topPanel.add(testChooser);

        bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("Email:"));
        bottomPanel.add(emailField);
        bottomPanel.add(Box.createHorizontalStrut(15)); // a spacer
        bottomPanel.add(new JLabel("Password:"));
        bottomPanel.add(passwordField);
        emailField.setText("email@email.com");

        myPanel.add(topPanel, BorderLayout.NORTH);
        myPanel.add(bottomPanel, BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please log in", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            email = emailField.getText();
            password = passwordField.getText();
            testName = testChooser.getSelectedItem().toString();
            testID = getTestIDByName(testName);
        }
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getTestName(){ return testName;}

    public int getTestID() { return testID;}

    private int getTestIDByName(String testName){
        for (ContainerClasses.Test test : tests) {
            if (test.getTestName().equals(testName)){
                return test.getTestID();
            }
        }
        return 0; // no match
    }

    private ArrayList<String> convertTestArrayToStringArray(){
        ArrayList<String> testNames = new ArrayList<>();
        for (ContainerClasses.Test test : tests) {
            testNames.add(test.getTestName());
        }
        return testNames;
    }
}

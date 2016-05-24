package AdminSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import SharedFunctions.DatabaseManager;

/**
 * The NewsetTitleForm class is made to control NewsetTitleForm GUI screen.
 * Admin can input the name of new test set
 * Create Date 05/24/2016
 *
 * @author   Jinsook Lee
 * @version  05/24/2016
 */

public class NewsetTitleForm {
    private JPanel rootPanel2;
    private JTextField nameTextField;
    private JButton nextButton;
    private DatabaseManager databaseManager;
    private String test;
    private int id;

    //Constructor
    public NewsetTitleForm() {
        databaseManager = new DatabaseManager();
        //test = new Test();
        rootPanel2.setPreferredSize(new Dimension(500, 350));
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTestset();
                javax.swing.SwingUtilities.invokeLater(() -> createAndShowGui1());
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel2;
    }

    /**
     * Open the AdminSetupForm GUI to modify the test set
     */
    public void createAndShowGui1() {
        JFrame frame = new JFrame("Admin Setup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new AdminSetupForm(id).getRootPanel());
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Insert new test set in TESTS table
     */
    public void addTestset() {
        //TestItemDAO tItemDAO = DAOFactory.getTestItemDAO();
        //if (isValidLength())     //item is valid length
        //if (!isDuplicate() && isValidLength()) {       //item is not duplicated
        test = nameTextField.getText();
        id = databaseManager.insertGetTest(test);
        //itemTextField.setText("");
        //itemTextField.requestFocus();
    }
}

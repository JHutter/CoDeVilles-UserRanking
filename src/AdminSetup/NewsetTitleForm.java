package AdminSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import DaoClasses.DAOFactory;
import DaoClasses.TestDAO;

/**
 * The NewsetTitleForm class is made to control NewsetTitleForm GUI screen.
 * Admin can input the name of new test set
 * Create Date 05/24/2016
 *
 * @author   Jinsook Lee
 * @version  05/31/2016
 *
 * Modification
 * 05/25/2016
 * - Use DAO to insert new test
 *
 * 05/31/2016
 * - Add Jframe parameter in constructor to close only this GUI screen
 * - Add isValidLength method to avoid name field is empty
 */

public class NewsetTitleForm {
    private JPanel rootPanel2;
    private JTextField nameTextField;
    private JButton nextButton;
    private String test;
    private JFrame nFrame;
    private int id;

    //Constructor
    public NewsetTitleForm(JFrame pFrame) {
        rootPanel2.setPreferredSize(new Dimension(500, 350));
        nFrame = pFrame;
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isValidLength()) {      // text field is not empty
                    addTestset();
                    javax.swing.SwingUtilities.invokeLater(() -> createAndShowGui1());
                }
            }
        });
    }

    /**
     * Open the AdminSetupForm GUI to modify the test set
     */
    public void createAndShowGui1() {
        JFrame frame = new JFrame("Admin Setup");
        //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
        frame.getContentPane().add(new AdminSetupForm(frame, id, true).getRootPanel());
        frame.pack();
        frame.setVisible(true);
        nFrame.dispose();
    }

    /**
     * Insert new test set in TESTS table
     */
    public void addTestset() {
        TestDAO testDAO = DAOFactory.getTestDAO();
        test = nameTextField.getText();
        id = testDAO.insertGetTest(test);
    }

    /**
     *  Check text length
     *  @return  boolean if text field is not empty return true
     */
    private boolean isValidLength() {
        if(nameTextField.getText().length() == 0) {        //check input is valid
            JOptionPane.showMessageDialog(rootPanel2, "Please input the name of test set.");
            nameTextField.requestFocus();
            return false;
        }
        return true;
    }

    public JPanel getRootPanel() {
        return rootPanel2;
    }
}

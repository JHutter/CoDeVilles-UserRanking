package AdminSetup;

import ContainerClasses.Test;
import DaoClasses.DAOFactory;
import DaoClasses.TestDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The TestsetChoiceForm class is made to control TestsetChoiceForm GUI screen.
 * Admin can choose modify existing test set or define new test set.
 * Create Date 05/24/2016
 *
 * @author   Jinsook Lee
 * @version  05/25/2016
 *
 * 05/25/2016
 * - Use DAO to insert new test
 */
public class TestsetChoiceForm {
    private JComboBox choiceCombo;
    private JRadioButton modiRadioButton;
    private JRadioButton newRadioButton;
    private JButton nextButton;
    private JPanel rootPanel1;
    private int testID;
    private ArrayList<Test> testsArr;
    //private DatabaseManager databaseManager;

    //Constructor
    public TestsetChoiceForm() {

        rootPanel1.setPreferredSize(new Dimension(500,350));
        //databaseManager = new DatabaseManager();
        testsArr = new ArrayList<>();
        getTests();
        fillNameBox();

        choiceCombo.setEnabled(false);
        nextButton.setEnabled(false);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(modiRadioButton.isSelected()) {
                    testID=getTestID();
                    javax.swing.SwingUtilities.invokeLater(() -> createAndShowGui1(getTestID()));
                }else if (newRadioButton.isSelected()){
                    javax.swing.SwingUtilities.invokeLater(() -> createAndShowGui2());
                }
            }
        });

        modiRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choiceCombo.setEnabled(true);
                nextButton.setEnabled(true);
            }
        });

        newRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choiceCombo.setEnabled(false);
                nextButton.setEnabled(true);
            }
        });
    }

    /**
     * Open the AdminSetupForm GUI to modify the test set
     * @param tID testID of the test set
     */
    public static void createAndShowGui1(int tID){
        JFrame frame = new JFrame("Admin Setup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new AdminSetupForm(tID).getRootPanel());
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Open the NewsetTitleForm GUI to input the title of new test set
     */
    public static void createAndShowGui2(){
        JFrame frame = new JFrame("Admin Setup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new NewsetTitleForm().getRootPanel());
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Get test id of selected test set
     * @return testID of selected set in Combo box
     */
    public int getTestID() {
        return testsArr.get(choiceCombo.getSelectedIndex()).getTestID();
    }

    /**
     * Get test set list from DB
     */
    public void getTests(){
        TestDAO testDAO = DAOFactory.getTestDAO();
        if(!testDAO.readAllTests(testsArr)){
            JOptionPane.showMessageDialog(rootPanel1, "Failed to read tests from database.");
        }
    }

    /**
     * populates the name box with test names
     */
    public void fillNameBox(){
        for(Test test : testsArr) {
            choiceCombo.addItem(test.getTestName());
        }
    }

    public JPanel getRootPanel(){
        return rootPanel1;
    }

}

package ResultReporting;

import ContainerClasses.TestItem;
import ContainerClasses.TestResult;
import ContainerClasses.TestSession;
import ContainerClasses.UserAccount;

import DaoClasses.UserAccountDAO;
import DaoClasses.TestResultDAO;
import DaoClasses.TestItemDAO;
import DaoClasses.TestSessionDAO;
import DaoClasses.DAOFactory;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This is the form class for result reporting.
 * This form presents the results of tests to the user
 * CIS 234A Dougherty
 * Date created: 4/19/2016.
 *  @author Zack
 *  @version 2016.5.27
 *
 *  2016.4.29
 *      added error pop-ups if database read fails
 *      results name list now only shows user accounts with answered test questions
 *
 *  2016.5.9
 *      results list now displays items in order of wins
 *      refactored code for cleaner appearance
 *
 *  2016.5.16
 *      results list will no longer show items with all 0's (items that have not been part of a question)
 *  2016.5.20
 *      added button to open results matrix
 *      refactored some database functions to use Dao classes. test sessions and test items Dao not implemented yet
 *  2016.5.27
 *      refactored test sessions and test items database functions to use Dao classes
 *      implemented DAO factory
 */
public class ResultReporting {
    private JComboBox nameBox;
    private JPanel rootPanel;
    private JButton finishButton;
    private JTextArea resultList;
    private JButton matrixButton;

    private ArrayList<UserAccount> userAccounts;
    private ArrayList<TestItem> testItems;
    private ArrayList<TestSession> testSessions;
    private ArrayList<TestResult> testResults;
    private ArrayList<RankedItem> rankedResults;

    /**
     * Default Constructor
     */
    public ResultReporting() {
        //Set the size of the panel to agreed upon value
        rootPanel.setPreferredSize(new Dimension(500, 350));

        //populate the test result list, test item list, test session list, and user account list
        testItems = new ArrayList<>();
        getTestItems();
        testSessions = new ArrayList<>();
        getTestSessions();
        testResults = new ArrayList<>();
        getTestResults();
        userAccounts = new ArrayList<>();
        getUserAccounts();

        //fill the text fields
        fillNameBox();
        fillResultsBox();

        //on click listener for results box
        nameBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillResultsBox();
            }
        });

        //on click listener for finish button
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);//exit on cancel
            }
        });

        //on click listener for button that opens results matrix
        matrixButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.SwingUtilities.invokeLater(() -> createAndShowMatrix());
            }
        });
    }

    /**
     * @return rootPanel the root panel holding all other content
     */
    public JPanel getRootPanel(){return rootPanel;}

    /**
     * fills the test item array list with data
     */
    public void getTestItems(){
        TestItemDAO itemsManager = DAOFactory.getTestItemDAO();
        //populate with values from the database
        if(!itemsManager.readAllTestItems(testItems)){
            //close the window due to read failure
            JOptionPane.showMessageDialog(rootPanel, "Failed to read test items from database. Please check your internet connection and try again.");
            System.exit(-1);
        }
    }

    /**
     * fills the test session array list with data
     */
    public void getTestSessions(){
        TestSessionDAO sessionsManager = DAOFactory.getTestSessionDAO();
        //populate with values from the database
        if(!sessionsManager.readAllTestSessions(testSessions)){
            //close the window due to read failure
            JOptionPane.showMessageDialog(rootPanel, "Failed to read test sessions from database. Please check your internet connection and try again.");
            System.exit(-2);
        }
    }

    /**
     * fills the test result array list with data
     */
    public void getTestResults(){
        TestResultDAO resultsManager = DAOFactory.getTestResultDAO();
        //populate with values from the database
        if(!resultsManager.readAllTestResults(testResults)){
            //close the window due to read failure
            JOptionPane.showMessageDialog(rootPanel, "Failed to read test results from database. Please check your internet connection and try again.");
            System.exit(-3);
        }
    }

    /**
     * fills the user account array list with data
     */
    public void getUserAccounts(){
        UserAccountDAO userAccountsManager = DAOFactory.getUserAccountDAO();
        //populate with values from the database
        if(!userAccountsManager.readUsersHavingResults(userAccounts)){
            //close the window due to read failure
            JOptionPane.showMessageDialog(rootPanel, "Failed to read user accounts from database. Please check your internet connection and try again.");
            System.exit(-4);
        }
    }

    /**
     * populates the name box with the emails in the user account array list
     */
    public void fillNameBox(){
        for(UserAccount userAccount : userAccounts) {
            nameBox.addItem(userAccount.getEmail());
        }
    }

    /**
     * populates the results box with values
     * item names are from the test items array list
     * results are parsed from the test results array list
     */
    public void fillResultsBox(){

        //reset the text box and add the header
        resultList.setText("");
        resultList.append("Item \t Win \t Loss \t Tie \n");

        //generate a list of ranked results for the current user
        generateResultsList();

        //for each item in the unranked item list, print the highest ranked item and remove it from the ranked item list
        for (TestItem testItem: testItems){
            printAndDeleteHighestItem();
        }
    }
    /**
     * takes the results list and item list and combines it into a list of ranked items based on the user selected
     */
    public void generateResultsList(){
        int win = 0, loss = 0, tie = 0;
        int selectedUserID = userAccounts.get(nameBox.getSelectedIndex()).getUserID();
        rankedResults = new ArrayList<RankedItem>();

        //for each item generate results
        for (TestItem testItem : testItems) {
            //for each test session check user id
            for(TestSession testSession: testSessions) {
                //if the session belongs to the user selected, then continue
                if (testSession.getUserID() == selectedUserID){
                    //parse each object in the results list for data
                    for (TestResult testResult : testResults) {
                        //if the result belongs to the session in the current iteration, and the result is for the item in the current iteration, then count the result
                        if (testItem.getItemID() == testResult.getItemID() && testSession.getSessionID() == testResult.getSessionID()) {
                            switch (testResult.getResult()) {
                                case 1:
                                    win++;
                                    break;
                                case 0:
                                    tie++;
                                    break;
                                case -1:
                                    loss++;
                                    break;
                            }
                        }
                    }
                }
            }

            //write the data to the ranked items list after parsing
            rankedResults.add(new RankedItem(testItem, win, loss, tie));
            //reset counters
            win = 0;
            loss = 0;
            tie = 0;
        }
    }

    /**
     * prints the highest ranked item to the results box then removes it from the list
     */
    public void printAndDeleteHighestItem(){
        RankedItem highestItem = new RankedItem();

        //find the highest ranked item
        for (RankedItem rankedItem: rankedResults){
            if(rankedItem.getWin() >= highestItem.getWin()){
                highestItem = rankedItem;
            }
        }
        //print the highest ranked item if the item doesn't have all 0 values
        if(highestItem.getWin() != 0 || highestItem.getLoss() != 0 || highestItem.getTie() != 0) {
            resultList.append(highestItem.getItemText() + "\t" +
                    highestItem.getWin() + "\t" +
                    highestItem.getLoss() + "\t" +
                    highestItem.getTie() + "\n");
        }
        //remove the highest ranked item from the list
        rankedResults.remove(highestItem);
    }

    /**
     * opens the results matrix in a new window
     */
    public static void createAndShowMatrix() {
        JFrame frame = new JFrame("Results Matrix");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new ResultsMatrix().getRootPanel());
        frame.pack();
        frame.setVisible(true);
    }
}

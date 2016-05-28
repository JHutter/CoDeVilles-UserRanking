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
 * This is the form class for result reporting in the form of a matrix.
 * This form presents the results of tests to the user indicating each selection
 * CIS 234A Dougherty
 * Date created: 5/20/2016.
 *  @author Zack
 *  @version 2016.5.27
 *
 *  2016.5.27
 *      refactored test sessions and test items database functions to use Dao classes
 *      implemented DAO factory
 */
public class ResultsMatrix {
    private JComboBox userBox;
    private JLabel userAccountLabel;
    private JLabel sessionNumLabel;
    private JComboBox sessionBox;
    private JTextArea resultsArea;
    private JLabel resultsLabel;
    private JPanel rootPanel;

    private ArrayList<UserAccount> userAccounts;
    private ArrayList<TestItem> testItems;
    private ArrayList<TestSession> testSessions;
    private ArrayList<TestResult> testResults;

    private int selectedUser, selectedSession;

    /**
     * default constructor
     */
    public ResultsMatrix() {
        //Set the size of the panel to necessary value
        rootPanel.setPreferredSize(new Dimension(650, 350));

        //populate the test result list, test item list, test session list, and user account list
        userAccounts = new ArrayList<>();
        getUserAccounts();
        fillUserBox();

        testSessions = new ArrayList<>();
        getTestSessions();
        fillSessionBox();

        testItems = new ArrayList<>();
        getTestItems();

        testResults = new ArrayList<>();
        getTestResults();

        fillResultsMatrix();

        //actionlistener
        userBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedUser = userAccounts.get(userBox.getSelectedIndex()).getUserID();
                fillSessionBox();
                fillResultsMatrix();
            }
        });
        sessionBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sessionBox.getItemCount() != 0) {
                    selectedSession = (int) sessionBox.getSelectedItem();
                    fillResultsMatrix();
                } else {
                    selectedSession = 0;
                }
            }
        });
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
     * populates the user account box with the emails in the user account array list
     */
    public void fillUserBox(){
        for(UserAccount userAccount : userAccounts) {
            userBox.addItem(userAccount.getEmail());
            selectedUser = userAccounts.get(userBox.getSelectedIndex()).getUserID();
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
     * populates the user account box with the emails in the user account array list
     */
    public void fillSessionBox(){
        if(sessionBox.getItemCount() != 0){
            sessionBox.removeAllItems();
        }
        for(TestSession testSession : testSessions) {
            if(testSession.getUserID() == selectedUser && !testSession.getIsActive()) {
                sessionBox.addItem(testSession.getSessionID());
            }
        }
        if(sessionBox.getItemCount() != 0) {
            selectedSession = (int) sessionBox.getSelectedItem();
        }
    }

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
     * populates the results box with values
     * item names are from the test items array list
     * results are parsed from the test results array list
     */
    public void fillResultsMatrix(){
        //reset the text box and add the header
        resultsArea.setText("");
        appendResultsHeader();
        appendResultsRows();
    }

    /**
     * adds the header to the results box
     */
    public void appendResultsHeader(){
        //print a tab character for the first column then the name of each item
        resultsArea.append("\t");
        for(TestItem testItem : testItems){
            if(itemHasResult(testItem)){resultsArea.append(testItem.getItemText() + "\t");}
        }
        resultsArea.append("\n");
    }

    /**
     * fills each row of the results box with data
     */
    public void appendResultsRows(){
        //row loop
        for (TestItem leftItem : testItems){
            if(itemHasResult(leftItem)) {
                resultsArea.append(leftItem.getItemText() + "\t");
                //column loop
                for (TestItem topItem : testItems) {
                    if(itemHasResult(topItem)){resultsArea.append(getStringResult(leftItem, topItem) + "\t");}
                }
                resultsArea.append("\n");
            }
        }
        resultsArea.append("Legend: < = left item selected, /\\ = top item selected, 0 = tie, \" \" = No Data");
    }

    /**
     * Checks if an item is
     */
    public boolean itemHasResult (TestItem item){
        for(TestResult result: testResults){
            if(item.getItemID() == result.getItemID()){return true;}
        }
        return false;
    }

    /**
     * turns two items into a string result
     * @param leftItem the left item in the matrix
     * @param topItem the top item in the matrix
     * @return a string value indicating the result of the comparison in the selected test session.
     */
    public String getStringResult(TestItem leftItem,TestItem topItem){
        //left item loop
        for(TestResult leftResult: testResults){
            //if statement to short circuit loop
            if(leftResult.getItemID() == leftItem.getItemID()) {

                //top item loop
                for (TestResult topResult : testResults) {
                    //if statement to short circuit loop
                    if(topResult.getItemID() == topItem.getItemID()){

                        //if both results are part of the selected session check the question number
                        if(leftResult.getSessionID() == selectedSession && topResult.getSessionID() == selectedSession) {

                            //if both results have the same question number, but not the same item id number check the result
                            if(leftResult.getQuestionNumber() == topResult.getQuestionNumber() && leftResult.getItemID() != topResult.getItemID()) {

                                //print the string result based on the value in each result object
                                //left item was selected
                                if(leftResult.getResult() > topResult.getResult()) {
                                    return "<";
                                }
                                //top item was selected
                                else if (leftResult.getResult() < topResult.getResult()) {
                                    return "/\\";
                                }
                                //neither item was selected
                                else if (leftResult.getResult() == topResult.getResult() ){
                                    return "0";

                                }else{
                                    //return debug value because the other comparisons should not all fail
                                    return "Dbg2";
                                }
                            }
                        }
                    }
                }
            }
        }
        return " "; //return blank if no data found
    }

    /**
     * @return rootPanel the root panel holding all other content
     */
    public JPanel getRootPanel(){return rootPanel;}
}

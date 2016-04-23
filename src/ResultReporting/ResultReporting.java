package ResultReporting;

import ContainerClasses.TestResult;
import ContainerClasses.UserAccount;
import SharedFunctions.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This is the form class for result reporting.
 * This form presents the results of tests to the user
 * CIS 234A Dougherty
 *
 *  Programmer(s): Zack
 *  Date: 4/19/2016.
 *
 */
public class ResultReporting {
    private JComboBox nameBox;
    private JPanel rootPanel;
    private JButton finishButton;
    private JTextArea resultList;
    private ArrayList<UserAccount> userAccounts;
    private ArrayList<TestResult> testResults;
    private DatabaseManager databaseManager;

    /**
     * Default Constructor
     */
    public ResultReporting() {
        //Set the size of the panel to necessary value
        rootPanel.setPreferredSize(new Dimension(560, 325));
        //instance the database manager
        databaseManager = new DatabaseManager();

        //populate the test result list and user account list
        userAccounts = new ArrayList<>();
        getUserAccounts();
        testResults = new ArrayList<>();
        getTestResults();

        //fill the text fields
        fillNameBox();
        fillResultsBox();
    }

    /**
     * @return rootPanel the root panel holding all other content
     */
    public JPanel getRootPanel(){return rootPanel;}

    /**
     * fills the user account array list with data
     */
    public void getUserAccounts(){
        //populate with values from the database
         if(!databaseManager.readAllUserAccounts(userAccounts)){
             //close the window due to read failure
         }
    }

    /**
     * fills the test result array list with data
     */
    public void getTestResults(){
        //populate with test values for now
        testResults.add(new TestResult(1, 1, 1, 1, 1));
        testResults.add(new TestResult(2, 2, 2, 2, 2));
    }

    /**
     * populates the name box with the usernames in the user account array list
     */
    public void fillNameBox(){
        for(UserAccount userAccount : userAccounts) {
            nameBox.addItem(userAccount.getName());
        }
    }

    /**
     * populates the results box with the values in the test results array list
     */
    public void fillResultsBox(){
        resultList.setText("");
        resultList.append("ResultID \t Question# \t ItemID \t SessionID \t Result \n");
        for(TestResult testResult : testResults){
            resultList.append(testResult.getResultID() + "\t" + testResult.getQuestionNumber() + "\t" + testResult.getItemID() + "\t" + testResult.getSessionID() + "\t" + testResult.getResult() + "\n");
        }
    }


}

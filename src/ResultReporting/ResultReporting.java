package ResultReporting;

import ContainerClasses.TestItem;
import ContainerClasses.TestResult;
import ContainerClasses.TestSession;
import ContainerClasses.UserAccount;
import SharedFunctions.DatabaseManager;
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
 *  @version 2016.24.4
 *
 */
public class ResultReporting {
    private JComboBox nameBox;
    private JPanel rootPanel;
    private JButton finishButton;
    private JTextArea resultList;
    private ArrayList<UserAccount> userAccounts;
    private ArrayList<TestItem> testItems;
    private ArrayList<TestSession> testSessions;
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

        nameBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillResultsBox();
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
        //populate with values from the database
        if(!databaseManager.readAllTestItems(testItems)){
            //close the window due to read failure
        }
    }

    /**
     * fills the test session array list with data
     */
    public void getTestSessions(){
        //populate with values from the database
        if(!databaseManager.readAllTestSessions(testSessions)){
            //close the window due to read failure
        }
    }

    /**
     * fills the test result array list with data
     */
    public void getTestResults(){
        //populate with values from the database
        if(!databaseManager.readAllTestResults(testResults)){
            //close the window due to read failure
        }
    }

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
        int win = 0, loss = 0, tie = 0;

        //reset the text box and add the header
        resultList.setText("");
        resultList.append("Item \t Win \t Loss \t Tie \n");

        //for each item print results
        for (TestItem testItem : testItems) {
            //for each test session check user id
            for(TestSession testSession: testSessions) {
                //if the session belongs to the user selected, then continue
                if (testSession.getUserID() == userAccounts.get(nameBox.getSelectedIndex()).getUserID()){
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
            //write the data after parsing
            resultList.append(testItem.getItemText() + "\t" + win + "\t" + loss + "\t" + tie + "\n");
            //reset counters
            win = 0;
            loss = 0;
            tie = 0;
        }
    }
}

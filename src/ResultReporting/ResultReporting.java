package ResultReporting;

import ContainerClasses.TestItem;
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

        //populate the test result list, test item list, and user account list
        userAccounts = new ArrayList<>();
        getUserAccounts();
        testItems = new ArrayList<>();
        getTestItems();
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
     * fills the user account array list with data
     */
    public void getTestItems(){
        //populate with values from the database
        if(!databaseManager.readAllTestItems(testItems)){
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

        for(TestItem testItem : testItems){

            //parse results
            for(TestResult testResult : testResults){
                //if the test item and the test result have the same item id, then count the result
                if(testItem.getItemID()== testResult.getItemID()) {
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
            //write the data after parsing
            resultList.append(testItem.getItemText() + "\t" + win + "\t" + loss + "\t" + tie + "\n");
            //reset counters
            win = 0;
            loss = 0;
            tie = 0;
        }
    }
}
